package com.thaj.data.io

import org.apache.spark.sql.{SaveMode, SparkSession, _}
import org.apache.spark.sql.types.StructType

/**
  * Created by afsalthaj on 15/11/2016.
  */
/** A common set of functionalities that handles the read and write of
  * Spark data set.
  */
object IO {
  // Type aliases to make the code more readable in terms of function arguments
  type Path = String
  type Format = String

  // Most of the data are csv format
  val csvFormat = "com.databricks.spark.csv"

  /** Write data, as parquet data set, to make sure that we have enough compression
    * and type safety
    */
  def writeData(data: DataFrame, outputPath: Path) = data.write.mode(SaveMode.Overwrite).parquet(outputPath)
}
