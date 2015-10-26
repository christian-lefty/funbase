package io.lefty.hbase;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import io.lefty.hbase.proto.HBaseProto.Column;
import io.lefty.hbase.proto.HBaseProto.Put;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/**
 * Answers the GRPC put stream request. This class is not threadsafe.
 */
final class PutStreamObserver implements StreamObserver<Put> {
  private static final Logger LOG = LoggerFactory.getLogger(PutStreamObserver.class);
  private static final int PUT_BATCH_SIZE = 64;
  private static final long WRITE_BUFFER_SIZE = 8388608L;

  private final Connection hbase;
  private final ExecutorService hbaseOperations;
  private final StreamObserver<Empty> response;
  private final Queue<Put> puts = new LinkedList<>();
  private String tableName;

  PutStreamObserver(Connection hbase, ExecutorService hbaseOperations, StreamObserver<Empty> response) {
    this.hbase = hbase;
    this.hbaseOperations = hbaseOperations;
    this.response = response;
  }

  @Override
  public void onNext(Put put) {
    // all gets in a stream must be for the same table.
    if (!checkTableName(put)) {
      return;
    }

    puts.add(put);
  }

  private boolean checkTableName(Put put) {
    if (put.getTable().isEmpty()) {
      response.onError(new IllegalArgumentException("empty table: " + put.getTable()));
      return false;
    }

    // This is the first put, so we just consider its table to be the table for the whole stream.
    if (tableName == null) {
      tableName = put.getTable();
      return true;
    }

    if (!tableName.equals(put.getTable())) {
      response.onError(new IllegalArgumentException(
          "table: " + put.getTable() + " does not match that of the previous puts: " + tableName));
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
    if (puts.isEmpty()) {
      LOG.warn("ignoring empty request");
      response.onNext(Empty.getDefaultInstance());
      response.onCompleted();
      return;
    }

    LOG.info("submitting put task for {} elements.", puts.size());
    hbaseOperations.submit(this::callPut);
  }

  private Void callPut() {
    TableName tn = TableName.valueOf(tableName);
    BufferedMutatorParams p = new BufferedMutatorParams(tn);
    p.writeBufferSize(WRITE_BUFFER_SIZE);

    try (BufferedMutator table = hbase.getBufferedMutator(p)) {
      while (!puts.isEmpty()) {
        putBatch(table);
      }
      response.onNext(Empty.getDefaultInstance());
      response.onCompleted();
      return null;
    } catch (IOException ex) {
      LOG.error("error getting from hbase.", ex);
      response.onError(ex);
      return null;
    } catch (RuntimeException ex) {
      response.onError(ex);
      throw ex;
    }
  }

  private void putBatch(BufferedMutator table) throws IOException {
    List<org.apache.hadoop.hbase.client.Put> batch = new ArrayList<>();
    while (batch.size() < PUT_BATCH_SIZE && !puts.isEmpty()) {
      batch.add(toHBasePut(puts.poll()));
    }

    table.mutate(batch);
  }

  private static org.apache.hadoop.hbase.client.Put toHBasePut(Put lput) {
    org.apache.hadoop.hbase.client.Put put = new org.apache.hadoop.hbase.client.Put(lput.getId().toByteArray());
    for (Column c : lput.getColumns().getColumnList()) {
      put.addColumn(c.getColumn().getCf().toByteArray(), c.getColumn().getQualifier().toByteArray(),
          c.getData().toByteArray());
    }
    return put;
  }
}
