import sbt._
import sbt.Keys._

object build extends Build {
  type Sett = Def.Setting[_]

  lazy val all = Project(
    id = "all"
    , base = file(".")
    , settings = Seq(
        scalaVersion := "2.11.7",
        publishArtifact := false
      )
    , aggregate = Seq(etl)
  )

  lazy val etl = Project(
    id = "etl"
    , base = file("etl")
    , settings = Seq[Sett](
      scalaVersion := "2.11.7",
      libraryDependencies ++= Seq
          (
            "org.apache.spark" %% "spark-core" % "2.0.1" % "provided",
            "org.apache.spark" %% "spark-sql" % "2.0.1",
            "org.apache.spark" %% "spark-hive" % "2.0.1",
            "org.apache.spark" %% "spark-streaming" % "2.0.1",
            "org.apache.spark" %% "spark-streaming-flume" % "2.0.1",
            "org.apache.spark" %% "spark-mllib" % "2.0.1",
            "org.apache.spark" %% "spark-catalyst" % "2.0.1",
            "org.apache.spark" %% "spark-unsafe" % "2.0.1"

            )
        , parallelExecution in Test := false
      )
  )

}