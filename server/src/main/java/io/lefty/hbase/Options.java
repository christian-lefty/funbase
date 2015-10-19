package io.lefty.hbase;

import org.kohsuke.args4j.Option;

/**
 * Options class, implemented using args4j.
 * http://args4j.kohsuke.org/.
 */
final class Options {
  /**
   * Hbase quorum, in zookeeper format: server1:2181,server2:2181.
   */
  @Option(name = "-hbase_quorum")
  public String hbaseQuorum = "localhost:2181";

  /**
   * Port for the GRPC server to listen on.
   */
  @Option(name = "-port")
  public int port = 9000;

  /**
   * Number of threads to perform IO operations to hbase. This is not the same as GRPC's network threads.
   */
  @Option(name = "-nthreads")
  public int nthreads = 4;
}
