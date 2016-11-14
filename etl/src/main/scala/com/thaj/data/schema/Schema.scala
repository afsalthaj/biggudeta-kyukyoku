package com.thaj.data.schema

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * Created by afsalthaj on 15/11/2016.
  */
object Schema {
  /** Object that covers the schema of any data-set */
    val storeSchema = StructType(Array(
      StructField("store_id",        IntegerType,false),
      StructField("year",        StringType,false),
      StructField("month",          StringType,false),
      StructField("day",             StringType,false),
      StructField("fuel",            StringType,true),
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
