export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
export HADOOP_CONF_DIR=/etc/hadoop/conf
export SPARK_HOME=/root/spark/spark-2.0.1-bin-hadoop2.7

export PATH=$PATH:$SPARK_HOME/bin:$JAVA_HOME/bin:$HOME/bin
