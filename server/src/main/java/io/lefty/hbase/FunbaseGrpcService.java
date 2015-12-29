package io.lefty.hbase;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import io.lefty.hbase.proto.HBaseGrpc;
import io.lefty.hbase.proto.HBaseProto.ColumnQualifier;
import io.lefty.hbase.proto.HBaseProto.Delete;
import io.lefty.hbase.proto.HBaseProto.Get;
import io.lefty.hbase.proto.HBaseProto.Put;
import io.lefty.hbase.proto.HBaseProto.Result;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Implementation of the GRPC service.
 */
final class FunbaseGrpcService implements HBaseGrpc.HBase {
  private static final Logger LOG = LoggerFactory.getLogger(FunbaseGrpcService.class);

  private final Connection hbase;
  private final ExecutorService hbaseOperations;

  FunbaseGrpcService(Connection hbase, ExecutorService hbaseOperations) {
    this.hbase = hbase;
    this.hbaseOperations = hbaseOperations;
  }

  @Override
  public StreamObserver<Put> puts(StreamObserver<Empty> responseObserver) {
    return new PutStreamObserver(hbase, hbaseOperations, responseObserver);
  }

  @Override
  public void deletes(Delete del, StreamObserver<Empty> response) {
    TableName tn = TableName.valueOf(del.getTable());
    try (Table table = hbase.getTable(tn)) {
      org.apache.hadoop.hbase.client.Delete delete =
          new org.apache.hadoop.hbase.client.Delete(del.getId().toByteArray());
      for (ColumnQualifier col : del.getColumnList()) {
        delete.addColumn(col.getCf().toByteArray(), col.getQualifier().toByteArray());
      }
      LOG.info("deleting for id: {}.", del.getId().toStringUtf8());
      table.delete(delete);
      response.onNext(Empty.getDefaultInstance());
      response.onCompleted();
    } catch (IOException ex) {
      LOG.error("error deleting from hbase.", ex);
      response.onError(ex);
    }
  }

  @Override
  public StreamObserver<Get> gets(StreamObserver<Result> responseObserver) {
    return new GetStreamObserver(hbase, hbaseOperations, responseObserver);
  }
}
