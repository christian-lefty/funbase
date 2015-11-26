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
   * The 3 options below are to connect to a google cloud BigTable cluster.
   * If hbaseProjectId is null, we connect to a traditional hbase_quorum.
   *
   * Note that when using google cloud BigTable, alpn must be set correctly too.
   *
   * See: https://cloud.google.com/bigtable/docs/connecting-hbase
   */
  @Option(name = "-hbase_project_id")
  public String hbaseProjectId;
  @Option(name = "-hbase_zone")
  public String hbaseZone;
  @Option(name = "-hbase_cluster_id")
  public String hbaseClusterId;

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

  @Option(name = "-operations_timeout")
  public int operationsTimeout = 10000;
}
