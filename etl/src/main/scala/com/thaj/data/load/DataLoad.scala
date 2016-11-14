package com.thaj.data.load

import org.apache.spark.sql.types.{StructField, _}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

/** A common set of functionalities that handles the read and write of
  * Spark data set.
  */
object ReadWrite {
  // Type aliases to make the code more readable in terms of function arguments
  type Path = String
  type Format = String

  // Most of the data are csv format
  val csvFormat = "com.databricks.spark.csv"

  /** Load data, given an input path, spark session, schema and format
    * of the data
    */
  def loadData(inputPath: Path, spark: SparkSession, schema: StructType, format: Format = csvFormat) = {
    spark.read.format(csvFormat).schema(schema).load(inputPath)
  }

  /** Write data, as parquet data set, to make sure that we have enough compression
    * and type safety
    */
  def writeData(data: DataFrame, outputPath: Path) =
    data.write.mode(SaveMode.Overwrite).parquet(outputPath)
}

/** Object that covers the schema of any data-set */
object Schema {
  val storeSchema = StructType(Array(
    StructField("store_id",        IntegerType,false),
    StructField("year",        StringType,false),
    StructField("month",          StringType,false),
    StructField("day",            StringType,false),
    StructField("fuel",             StringType,true),
    StructField("nonfuel",         StringType,true),
    StructField("total_profit",    StringType,true)
  ))

  val sitesSchema = StructType(Array(
    StructField("store_id",        IntegerType,false),
    StructField("state",           StringType,false),
    StructField("suburb",          StringType,false),
    StructField("brand",           StringType,false),
    StructField("postcode",        StringType,true),
    StructField("phone",           StringType,true),
    StructField("E10",             StringType,true),
    StructField("ULP",             StringType,true),
    StructField("PULP",            StringType,true),
    StructField("VX98",            StringType,true),
    StructField("LPG",             StringType,true),
    StructField("Diesel",          StringType,true),
    StructField("BioDiesel",       StringType,true),
    StructField("Vortex",          StringType,true),
    StructField("Eftpos",          StringType,true),
    StructField("Workshop",        StringType,true),
    StructField("OpenAllHours",    StringType,true),
    StructField("Carwash",         StringType,true),
    StructField("Disabled",        StringType,true),
    StructField("Toilet",          StringType,true),
    StructField("ATM",             StringType,true),
    StructField("BBQ",             StringType,true),
    StructField("GAS",             StringType,true),
    StructField("Truckstop",       StringType,true),
    StructField("E-FLEX",          StringType,true),
    StructField("VX95",            StringType,true),
    StructField("Longitude",       StringType,true),
    StructField("Latitude",        StringType,true)
  ))
}

object DataLoad{
  import Schema._
  def main(args: Array[String]) {
    val spark = SparkSession.builder()
      .config("spark.sql.parquet.binaryAsString", "true")
      .config("spark.sql.warehouse.dir", "/tmp/spark-warehouse/").getOrCreate()

    val storeData = ReadWrite.loadData("/Users/afsalthaj/SampleData/fake_profit/*", spark, storeSchema)
    storeData.select("store_id", "year", "month", "day").show()
    ReadWrite.writeData(storeData, "/Users/afsalthaj/SampleData/profit/")

    val sitesData = ReadWrite.loadData("/Users/afsalthaj/SampleData/sitesinfo/*", spark, sitesSchema )
    sitesData.select("store_id", "state", "suburb", "brand").show()
    ReadWrite.writeData(sitesData, "/Users/afsalthaj/SampleData/sites/")

    val joinedDataSet = storeData.join(sitesData, Seq("store_id"))

    joinedDataSet.select(
      joinedDataSet("store_id"),
      joinedDataSet("total_profit"),
      joinedDataSet("latitude"),
      joinedDataSet("longitude")
    ).show

    spark.stop()
  }
}
