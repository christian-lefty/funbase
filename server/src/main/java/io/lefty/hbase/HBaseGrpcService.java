package io.lefty.hbase;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import io.lefty.hbase.proto.HBaseGrpc;
import io.lefty.hbase.proto.HBaseProto.Get;
import io.lefty.hbase.proto.HBaseProto.Put;
import io.lefty.hbase.proto.HBaseProto.Result;

import org.apache.hadoop.hbase.client.Connection;

import java.util.concurrent.ExecutorService;

/**
 * Implementation of the GRPC service.
 */
final class HBaseGrpcService implements HBaseGrpc.HBase {

  private final Connection hbase;
  private final ExecutorService hbaseOperations;

  HBaseGrpcService(Connection hbase, ExecutorService hbaseOperations) {
    this.hbase = hbase;
    this.hbaseOperations = hbaseOperations;
  }

  @Override
  public StreamObserver<Put> puts(StreamObserver<Empty> responseObserver) {
    return new PutStreamObserver(hbase, hbaseOperations, responseObserver);
  }

  @Override
  public StreamObserver<Get> gets(StreamObserver<Result> responseObserver) {
    return new GetStreamObserver(hbase, hbaseOperations, responseObserver);
  }
}
