#### Sample Spark Job

Client Mode:
$SPARK_HOME/spark-submit --class com.thaj.data.load.DataLoad   < path to packaged etl project >/etl_2.11-0.1-SNAPSHOT.jar

Cluster Mode:
$SPARK_HOME/spark-submit --class com.thaj.data.load.DataLoad --master yarn --deploy-mode cluster < path to packaged etl project>/etl_2.11-0.1-SNAPSHOT.jar

#### Read output
Example: java -jar < pathto >/parquet-tools-1.6.1-SNAPSHOT.jar cat part-r-00000-a9130032-fc52-4a6c-91b2-c3a39d2bc23a.snappy.parquet
