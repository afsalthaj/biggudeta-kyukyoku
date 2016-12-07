package com.thaj.data.load

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import com.thaj.data.schema.Schema._
import com.thaj.data.io.IO._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._

object DataLoad{

  def main(args: Array[String]) {
    val spark = SparkSession.builder()
      .config("spark.sql.parquet.binaryAsString", "true")
      .config("spark.sql.warehouse.dir", "/tmp/spark-warehouse/").getOrCreate()

    import spark.implicits._

    // A sample DataFrame
    val storeData: DataFrame = spark.read.format(csvFormat).schema(storeSchema)
      .load("/Users/afsalthaj/SampleData/fake_profit/*")

    writeData(storeData, "/Users/afsalthaj/SampleData/profit/")

   // Another DataFrame
   val sitesData: DataFrame =
    spark.read.format(csvFormat).schema(sitesSchema)
    .load("/Users/afsalthaj/SampleData/sitesinfo/*")

    // Started with a DataFrame and mapped it to SuburbState to get DataSet[SuburbState]
    val subSetColumnsFromStoreData: Dataset[SuburbState] =
      sitesData.map(t => SuburbState(t.getAs[String]("suburb"), t.getAs[String]("state")))

    // Started with an RDD since schema is not passed in, and you mapped it to AreaData to get RDD[AreaData]
    val areaDataSt: RDD[AreaData] =
      spark.sparkContext.textFile("/Users/afsalthaj/SampleData/AreaData/area.psv")
      .map(_.split("\\|"))
      .map(t => {
        AreaData(t(0), t(1), t(2), t(3).toDouble)
      })

    // An easy conversion to `DataFrame` directly from an RDD
    val areaData =  areaDataSt.toDF()

    // Being able to join a `DataFrame` to a `DataSet` to get a `DataFrame` in return
    val suburbArea: DataFrame = areaData
      .join(
        subSetColumnsFromStoreData,
        lower(areaData("label")).contains(lower(subSetColumnsFromStoreData("suburb"))),
        "left"
      )

    suburbArea.show(10)

    writeData(sitesData.toDF(), "/Users/afsalthaj/SampleData/sites/")

    // Join two dataframe to get a DataFrame in return
    val joinedDataSet: DataFrame = storeData.join(sitesData, Seq("store_id"), "left")

    joinedDataSet.printSchema()

    spark.stop()
  }
}