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
    TableName tn = TableName.valueOf(del.getTable());
    try (Table table = hbase.getTable(tn)) {
      org.apache.hadoop.hbase.client.Delete delete =
          new org.apache.hadoop.hbase.client.Delete(del.getId().toByteArray());
      for (ColumnQualifier col : del.getColumnList()) {
        delete.addColumn(col.getCf().toByteArray(), col.getQualifier().toByteArray());
      }
      table.delete(delete);
    } catch (IOException ex) {
      LOG.error("error deleting from hbase.", ex);
      response.onError(ex);
    }
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
