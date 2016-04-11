package io.lefty.hbase;

import com.google.protobuf.ByteString;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.lefty.hbase.proto.HBaseProto.Column;
import io.lefty.hbase.proto.HBaseProto.ColumnQualifier;
import io.lefty.hbase.proto.HBaseProto.Columns;
import io.lefty.hbase.proto.HBaseProto.Get;
import io.lefty.hbase.proto.HBaseProto.Result;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/**
 * Answers the GRPC get stream request. This class is not threadsafe.
 */
final class GetStreamObserver implements StreamObserver<Get> {
  private static final Logger LOG = LoggerFactory.getLogger(GetStreamObserver.class);
  private static final int GET_BATCH_SIZE = 64;

  private final StreamObserver<Result> response;
  private final Connection hbase;
  private final ExecutorService hbaseOperations;
  private final Queue<Get> gets = new LinkedList<>();
  private String tableName;

  GetStreamObserver(Connection hbase, ExecutorService hbaseOperations, StreamObserver<Result> response) {
    this.hbase = hbase;
    this.hbaseOperations = hbaseOperations;
    this.response = response;
  }

  @Override
  public void onNext(Get get) {
    // all gets in a stream must be for the same table.
    if (!checkTableName(get)) {
      return;
    }

    gets.add(get);
  }

  private boolean checkTableName(Get get) {
    if (get.getTable().isEmpty()) {
      response.onError(new IllegalArgumentException("empty table: " + get.getTable()));
      return false;
    }

    // This is the first get, so we just consider its table to be the table for the whole stream.
    if (tableName == null) {
      tableName = get.getTable();
      return true;
    }

    if (!tableName.equals(get.getTable())) {
      response.onError(new IllegalArgumentException(
          "table: " + get.getTable() + " does not match that of the previous gets: " + tableName));
      return false;
    }

    return true;
  }

  @Override
  public void onError(Throwable ex) {
    LOG.error("on stream error", ex);
    response.onError(ex);
  }

  @Override
  public void onCompleted() {
    if (gets.isEmpty()) {
      LOG.warn("ignoring empty request");
      response.onCompleted();
      return;
    }

    LOG.info("submitting get task for {} elements.", gets.size());
    hbaseOperations.submit(this::callGet);
  }

  private Void callGet() {
    TableName tn = TableName.valueOf(tableName);

    try (Table table = hbase.getTable(tn)) {
      while (!gets.isEmpty()) {
        getBatch(table);
      }
      response.onCompleted();
      return null;
    } catch (IOException ex) {
      LOG.error("error getting from hbase.", ex);
      response.onError(ex);
      return null;
    } catch (RuntimeException ex) {
      LOG.error("error getting from hbase.", ex);
      response.onError(new io.grpc.StatusRuntimeException(Status.fromThrowable(ex)));
      return null;
    }
  }

  private void getBatch(Table table) throws IOException {
    List<Get> batch = new ArrayList<>();
    while (batch.size() < GET_BATCH_SIZE && !gets.isEmpty()) {
      batch.add(gets.poll());
    }

    for (org.apache.hadoop.hbase.client.Result result : table.get(toHBaseGets(batch))) {
      response.onNext(fromHBaseResult(result));
    }
  }

  private static List<org.apache.hadoop.hbase.client.Get> toHBaseGets(List<Get> gets) {
    List<org.apache.hadoop.hbase.client.Get> result = new ArrayList<>();
    for (Get get : gets) {
      org.apache.hadoop.hbase.client.Get g = new org.apache.hadoop.hbase.client.Get(get.getId().toByteArray());
      for (ColumnQualifier c : get.getColumnList()) {
        g.addColumn(c.getCf().toByteArray(), c.getQualifier().toByteArray());
      }
      result.add(g);
    }
    return result;
  }

  private static Result fromHBaseResult(org.apache.hadoop.hbase.client.Result result) {
    Result.Builder r = Result.newBuilder();
    if (result == null || result.isEmpty()) {
      return r.setFound(false).build();
    }
    r.setId(ByteString.copyFrom(result.getRow()));

    r.setFound(true);
    Columns.Builder columns = r.getColumnsBuilder();
    for (NavigableMap.Entry<byte[], NavigableMap<byte[], byte[]>> e : result.getNoVersionMap().entrySet()) {
      ByteString columnFamily = ByteString.copyFrom(e.getKey());
      for (NavigableMap.Entry<byte[], byte[]> col : e.getValue().entrySet()) {
        ByteString qualifier = ByteString.copyFrom(col.getKey());
        ByteString value = ByteString.copyFrom(col.getValue());
        Column.Builder column = Column.newBuilder();
        column.setColumn(ColumnQualifier.newBuilder()
            .setCf(columnFamily)
            .setQualifier(qualifier))
            .build();
        column.setData(value);
        columns.addColumn(column);
      }
    }
    return r.build();
  }
}
