## Input DStreams and Receivers

Input DStreams are DStreams representing the stream of input data received from streaming sources. 
In the quick example, lines was an input DStream as it represented the stream of data received from the netcat server. 
Every input DStream (except file stream, discussed later in this section) is associated with a Receiver
(Scala doc, Java doc) object which receives the data from a source and stores it in Spark’s memory for processing.


Spark Streaming provides two categories of built-in streaming sources.

Basic sources: Sources directly available in the StreamingContext API. Examples: file systems, and socket connections.
Advanced sources: Sources like Kafka, Flume, Kinesis, etc. are available through extra utility classes. 
These require linking against extra dependencies as discussed in the linking section.