package com.thaj.data

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by afsalthaj on 16/11/2016.
  */


case class Person(rating: String, income: Double, age: Int)


object App {
  val sc = new SparkContext(new SparkConf().setAppName("People linear regression").setMaster("local"))

  def prepareFeatures(people: Seq[Person]): Seq[org.apache.spark.mllib.linalg.Vector] = {
    val maxIncome = people map(_ income) max
    val maxAge = people map(_ age) max

    people map (p =>
      Vectors dense(
        if (p.rating == "A") 0.7 else if (p.rating == "B") 0.5 else 0.3,
        p.income / maxIncome,
        p.age.toDouble / maxAge))
  }
}

