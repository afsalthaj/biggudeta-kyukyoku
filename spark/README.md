#### Sample Spark Job

deploy-mode: Distinguishes where the driver program runs:

`--deploy-mode: client` => the submitter launches the driver outside of the cluster

```
$SPARK_HOME/spark-submit --class com.thaj.data.load.DataLoad --master yarn < path to packaged etl project >/etl_2.11-0.1-SNAPSHOT.jar
```

`--deploy-mode: cluster` => the framework launches the driver inside of the cluster

```
$SPARK_HOME/spark-submit --class com.thaj.data.load.DataLoad --master yarn --deploy-mode cluster < path to packaged etl project>/etl_2.11-0.1-SNAPSHOT.jar
```

Switch to `--master local` if you want to run the entire job in local mode.

#### Read output
```
Example: java -jar < pathto >/parquet-tools-1.6.1-SNAPSHOT.jar cat part-r-00000-a9130032-fc52-4a6c-91b2-c3a39d2bc23a.snappy.parquet
```
