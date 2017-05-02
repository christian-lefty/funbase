package io.lefty.hbase.proto;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The proxy service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: proto/hbase.proto")
public final class HBaseGrpc {

  private HBaseGrpc() {}

  public static final String SERVICE_NAME = "hbase.proto.HBase";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.lefty.hbase.proto.HBaseProto.Put,
      com.google.protobuf.Empty> METHOD_PUTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING,
          generateFullMethodName(
              "hbase.proto.HBase", "Puts"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Put.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.lefty.hbase.proto.HBaseProto.Delete,
      com.google.protobuf.Empty> METHOD_DELETES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hbase.proto.HBase", "Deletes"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Delete.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.lefty.hbase.proto.HBaseProto.Get,
      io.lefty.hbase.proto.HBaseProto.Result> METHOD_GETS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "hbase.proto.HBase", "Gets"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Get.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Result.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HBaseStub newStub(io.grpc.Channel channel) {
    return new HBaseStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HBaseBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HBaseBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static HBaseFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HBaseFutureStub(channel);
  }

  /**
   * <pre>
   * The proxy service.
   * </pre>
   */
  public static abstract class HBaseImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Persists all the put items.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Put> puts(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_PUTS, responseObserver);
    }

    /**
     * <pre>
     * Deletes the item.
     * </pre>
     */
    public void deletes(io.lefty.hbase.proto.HBaseProto.Delete request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETES, responseObserver);
    }

    /**
     * <pre>
     * Returns a result for each get, in the same order as they
     * were sent.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Get> gets(
        io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Result> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_GETS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PUTS,
            asyncClientStreamingCall(
              new MethodHandlers<
                io.lefty.hbase.proto.HBaseProto.Put,
                com.google.protobuf.Empty>(
                  this, METHODID_PUTS)))
          .addMethod(
            METHOD_DELETES,
            asyncUnaryCall(
              new MethodHandlers<
                io.lefty.hbase.proto.HBaseProto.Delete,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETES)))
          .addMethod(
            METHOD_GETS,
            asyncBidiStreamingCall(
              new MethodHandlers<
                io.lefty.hbase.proto.HBaseProto.Get,
                io.lefty.hbase.proto.HBaseProto.Result>(
                  this, METHODID_GETS)))
          .build();
    }
  }

  /**
   * <pre>
   * The proxy service.
   * </pre>
   */
  public static final class HBaseStub extends io.grpc.stub.AbstractStub<HBaseStub> {
    private HBaseStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HBaseStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HBaseStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HBaseStub(channel, callOptions);
    }

    /**
     * <pre>
     * Persists all the put items.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Put> puts(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_PUTS, getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Deletes the item.
     * </pre>
     */
    public void deletes(io.lefty.hbase.proto.HBaseProto.Delete request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETES, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns a result for each get, in the same order as they
     * were sent.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Get> gets(
        io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Result> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_GETS, getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * The proxy service.
   * </pre>
   */
  public static final class HBaseBlockingStub extends io.grpc.stub.AbstractStub<HBaseBlockingStub> {
    private HBaseBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HBaseBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HBaseBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HBaseBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Deletes the item.
     * </pre>
     */
    public com.google.protobuf.Empty deletes(io.lefty.hbase.proto.HBaseProto.Delete request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETES, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The proxy service.
   * </pre>
   */
  public static final class HBaseFutureStub extends io.grpc.stub.AbstractStub<HBaseFutureStub> {
    private HBaseFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HBaseFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HBaseFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HBaseFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Deletes the item.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deletes(
        io.lefty.hbase.proto.HBaseProto.Delete request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETES, getCallOptions()), request);
    }
  }

  private static final int METHODID_DELETES = 0;
  private static final int METHODID_PUTS = 1;
  private static final int METHODID_GETS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HBaseImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HBaseImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DELETES:
          serviceImpl.deletes((io.lefty.hbase.proto.HBaseProto.Delete) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUTS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.puts(
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
        case METHODID_GETS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.gets(
              (io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Result>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class HBaseDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.lefty.hbase.proto.HBaseProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HBaseGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HBaseDescriptorSupplier())
              .addMethod(METHOD_PUTS)
              .addMethod(METHOD_DELETES)
              .addMethod(METHOD_GETS)
              .build();
        }
      }
    }
    return result;
  }
}
