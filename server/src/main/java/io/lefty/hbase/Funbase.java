package io.lefty.hbase;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.lefty.hbase.proto.HBaseGrpc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main: starts up the hbase GRPC server.
 */
public final class Funbase {
  private static final Logger LOG = LoggerFactory.getLogger(Funbase.class);

  public static void main(String[] args) throws Exception {
    Options opts = parseOptionsOrDie(args, new Options());
    Connection hbase = hbase(opts);
    ExecutorService service = Executors.newFixedThreadPool(opts.nthreads);

    FunbaseGrpcService grpcService = new FunbaseGrpcService(hbase, service);
    Server server = ServerBuilder.forPort(opts.port)
        .addService(HBaseGrpc.bindService(grpcService))
        .build();
    LOG.info("hbase grpc is taking the stage on 0.0.0.0:" + opts.port);
    server.start();
    // run forever.
    server.awaitTermination();
  }

  private static Connection hbase(Options opts) {
    if (opts.hbaseProjectId == null) {
      LOG.info("connecting to regular hbase quorum: {}", opts.hbaseQuorum);
      Configuration config = HBaseConfiguration.create();
      config.set("hbase.zookeeper.quorum", opts.hbaseQuorum);
      config.set("hbase.client.operation.timeout", Integer.toString(opts.operationsTimeout));
      Connection hbase;
      try {
        hbase = ConnectionFactory.createConnection(config);
      } catch (IOException ex) {
        throw new RuntimeException("cannot connect to hbase", ex);
      }
      return hbase;
    } else {
      LOG.info("connecting to google BigTable: {}, {}, {}", opts.hbaseProjectId,
          opts.hbaseZone, opts.hbaseClusterId);
      // Connect to a google cloud BigTable.
      return BigtableConfiguration.connect(opts.hbaseProjectId, opts.hbaseZone,
          opts.hbaseClusterId);
    }
  }

  private static Options parseOptionsOrDie(String[] args, Options options) {
    CmdLineParser parser = new CmdLineParser(options);
    parser.setUsageWidth(100);

    try {
      parser.parseArgument(args);
    } catch (CmdLineException ex) {
      System.err.println(ex.getMessage());
      System.err.println();
      parser.printUsage(System.err);
      System.err.println();
      System.exit(1);
    }
    return options;
  }
}
