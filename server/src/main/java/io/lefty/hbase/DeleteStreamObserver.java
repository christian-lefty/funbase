package io.lefty.hbase;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import io.lefty.hbase.proto.HBaseProto.ColumnQualifier;
import io.lefty.hbase.proto.HBaseProto.Delete;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Answers the GRPC delete endpoint.
 */
final class DeleteStreamObserver implements StreamObserver<Delete> {
  private static final Logger LOG = LoggerFactory.getLogger(DeleteStreamObserver.class);

  private final StreamObserver<Empty> response;
  private final Connection hbase;

  DeleteStreamObserver(Connection hbase, StreamObserver<Empty> response) {
    this.hbase = hbase;
    this.response = response;
  }

  @Override
  public void onNext(Delete del) {

  }

  @Override
  public void onError(Throwable ex) {
    LOG.error("on stream error", ex);
    response.onError(ex);
  }

  @Override
  public void onCompleted() {
    response.onNext(Empty.getDefaultInstance());
    response.onCompleted();
  }
}
