package com.thaj.data.streaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

// Referred
// https://github.com/apache/spark/blob/v2.0.1/examples/src/main/scala/org/apache/spark/examples/streaming/NetworkWordCount.scala

// https://spark.apache.org/docs/2.0.1/streaming-programming-guide.html#basic-concepts
/**
 * Created by afsalthaj on 25/03/17.
 */

/**
 * Spark Streaming is an extension of the core Spark API that
 * enables scalable, high-throughput, fault-tolerant stream processing of live data streams.
 * Data can be ingested from many sources like Kafka, Flume, Kinesis, or TCP sockets, and can be processed
 * using complex algorithms expressed with high-level functions like map, reduce, join and window. Finally,
 * processed data can be pushed out to filesystems, databases, and live dashboards. In fact, you can apply Spark’s machine
 * learning and graph processing algorithms on data streams.
 *
 * Spark Streaming receives live input data streams and divides the data
 * into batches, which are then processed by the Spark engine to generate the final stream of results in batches.
 *
 * Spark Streaming provides a high-level abstraction called discretized stream or DStream,
 * which represents a continuous stream of data. DStreams can be created either from input data streams
 * from sources such as Kafka, Flume, and Kinesis, or by applying high-level operations on other DStreams.
 * Internally, a DStream is represented as a sequence of RDDs.
 */


object Streaming {

  // Let’s say we want to count the number of words in text data received from a
  // data server listening on a TCP socket. All you need to do is as follows.
  import org.apache.spark._
  import org.apache.spark.streaming._


  // Create a local StreamingContext with two working thread and batch interval of 1 second.
  // The master requires 2 cores to prevent from a starvation scenario.
  val conf = new SparkConf().setMaster("local[2]").setAppName("Network word count")

  // created a spark context
  val ssc = new StreamingContext(conf, Seconds(1))

  /**
   * TCP (Transmission Control Protocol) is a standard that defines how to establish and
   * maintain a network conversation via which application programs can exchange data.
   * TCP works with the Internet Protocol (IP), which defines how computers send packets of data to each other.
   * Together, TCP and IP are the basic rules defining the Internet.
   * TCP is a connection-oriented protocol, which means a connection is
   * established and maintained until the application programs at each end have
   * finished exchanging messages. It determines how to break application data into packets that networks can deliver,
   * sends packets to and accepts packets from the network layer, manages flow control,
   * and—because it is meant to provide error-free data transmission—handles retransmission of
   * dropped or garbled packets as well as acknowledgement of all packets that arrive.
   */
  // Using the context, we create a DStream that represents streaming data from a TCP source,
  // specified as hostname (e.g. localhost) and port (e.g. 9999).

  // Create a DStream that will connect to hostname:port, like localhost:9999
  val lines: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)

  // This lines DStream represents the stream of data that will be received from the data server.
  // Each record in this DStream is a line of text. Next, we want to split the lines by space characters into words.

  // Split each line into words
  val words: DStream[String] = lines.flatMap(_.split(" "))

  // Count each word in each batch
  val pairs = words.map(word => (word, 1))
  val wordCounts = pairs.reduceByKey(_ + _)

  // Print the first ten elements of each RDD generated in this DStream to the console
  wordCounts.print()

  // Note that when these lines are executed,
  // Spark Streaming only sets up the computation
  // it will perform when it is started, and no real processing has started yet.
  // To start the processing after all the transformations have been setup, we finally call
  ssc.start()             // Start the computation
  ssc.awaitTermination()  // Wait for the computation to terminate

  // If you have already downloaded and built Spark, you can run this example as follows.
  // You will first need to run Netcat (a small utility found in most Unix-like systems) as a data server by using
  // nc -lk 9999
}
