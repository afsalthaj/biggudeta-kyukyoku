package com.thaj.data.load

import org.apache.spark.sql.{Dataset, SparkSession}
import com.thaj.data.schema.Schema._
import com.thaj.data.io.IO._

object DataLoad{

  def main(args: Array[String]) {
    val spark = SparkSession.builder()
      .config("spark.sql.parquet.binaryAsString", "true")
      .config("spark.sql.warehouse.dir", "/tmp/spark-warehouse/").getOrCreate()

    val storeData: Dataset[StoreData] = loadData[StoreData]("/Users/afsalthaj/SampleData/fake_profit/*", spark)
    storeData.select("store_id", "year", "month", "day").show()
    writeData(storeData.toDF, "/Users/afsalthaj/SampleData/profit/")

    val sitesData = loadData("/Users/afsalthaj/SampleData/sitesinfo/*", spark, sitesSchema )
    sitesData.select("store_id", "state", "suburb", "brand").show()
    writeData(sitesData, "/Users/afsalthaj/SampleData/sites/")

    val joinedDataSet = storeData.toDF.join(sitesData, Seq("store_id"), "left")


    joinedDataSet.select(
      joinedDataSet("store_id"),
      joinedDataSet("total_profit"),
      joinedDataSet("latitude"),
      joinedDataSet("longitude")
    ).show

    spark.stop()
  }
}
