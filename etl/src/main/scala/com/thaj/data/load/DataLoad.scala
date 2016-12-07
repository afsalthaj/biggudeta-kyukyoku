package com.thaj.data.load

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import com.thaj.data.schema.Schema._
import com.thaj.data.io.IO._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._

// A sample program that shows the interoperability of APIs
/**
  * Spark SQL is a Spark module for structured data processing.
  * Unlike the basic Spark RDD API, the interfaces provided by Spark SQL provide Spark with more information
  * about the structure of both the data and the computation being performed. Internally, Spark SQL uses this
  * extra information to perform extra optimizations. There are several ways to interact with Spark SQL including
  * SQL and the Dataset API. When computing a result the same execution engine is used, independent of which API/language
  * you are using to express the computation. This unification means that developers can easily switch back and forth
  * between different APIs based on which provides the most natural way to express a given transformation.
  */

/**
  * A Dataset is a distributed collection of data. Dataset is a new interface added in Spark 1.6
  * that provides the benefits of RDDs (strong typing, ability to use powerful lambda functions) with
  * the benefits of Spark SQL’s optimized execution engine. A Dataset can be constructed from JVM objects and
  * then manipulated using functional transformations (map, flatMap, filter, etc.). The Dataset API is available in
  * Scala and Java. Python does not have the support for the Dataset API. But due to Python’s dynamic nature,
  * many of the benefits of the Dataset API are already available (i.e. you can access the field of a
  * row by name naturally row.columnName). The case for R is similar.
  *
  * A DataFrame is a Dataset organized into named columns. It is conceptually equivalent to a table in a
  * relational database or a data frame in R/Python, but with richer optimizations under the hood. DataFrames
  * can be constructed from a wide array of sources such as: structured data files, tables in Hive, external databases,
  * or existing RDDs. The DataFrame API is available in Scala, Java, Python, and R. In Scala and Java, a DataFrame is
  * represented by a Dataset of Rows. In the Scala API, DataFrame is simply a type alias of Dataset[Row].
  * While, in Java API, users need to use Dataset<Row> to represent a DataFrame.
  */
object DataLoad{
  def main(args: Array[String]) {
    val spark = SparkSession.builder()
      .config("spark.sql.parquet.binaryAsString", "true")
      .config("spark.sql.warehouse.dir", "/tmp/spark-warehouse/").getOrCreate()

    import spark.implicits._

    // A sample DataFrame
    val storeData: DataFrame = spark.read.format(csvFormat).schema(storeSchema)
      .load("/Users/afsalthaj/SampleData/fake_profit/*")

    // Write as Parquet
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

    // Show schema
    suburbArea.show(10)
    // Write Data
    writeData(sitesData.toDF(), "/Users/afsalthaj/SampleData/sites/")

    // Join two DataFrame to get a DataFrame in return
    val joinedDataSet: DataFrame = storeData.join(sitesData, Seq("store_id"), "left")
    joinedDataSet.printSchema()
    spark.stop()
  }
}