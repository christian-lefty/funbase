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

@javax.annotation.Generated("by gRPC proto compiler")
public class HBaseGrpc {

  private HBaseGrpc() {}

  public static final String SERVICE_NAME = "hbase.proto.HBase";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<io.lefty.hbase.proto.HBaseProto.Put,
      com.google.protobuf.Empty> METHOD_PUTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING,
          generateFullMethodName(
              "hbase.proto.HBase", "Puts"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Put.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<io.lefty.hbase.proto.HBaseProto.Delete,
      com.google.protobuf.Empty> METHOD_DELETES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hbase.proto.HBase", "Deletes"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Delete.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<io.lefty.hbase.proto.HBaseProto.Get,
      io.lefty.hbase.proto.HBaseProto.Result> METHOD_GETS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "hbase.proto.HBase", "Gets"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Get.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.lefty.hbase.proto.HBaseProto.Result.getDefaultInstance()));

  public static HBaseStub newStub(io.grpc.Channel channel) {
    return new HBaseStub(channel);
  }

  public static HBaseBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HBaseBlockingStub(channel);
  }

  public static HBaseFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HBaseFutureStub(channel);
  }

  public static interface HBase {

    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Put> puts(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver);

    public void deletes(io.lefty.hbase.proto.HBaseProto.Delete request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver);

    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Get> gets(
        io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Result> responseObserver);
  }

  public static interface HBaseBlockingClient {

    public com.google.protobuf.Empty deletes(io.lefty.hbase.proto.HBaseProto.Delete request);
  }

  public static interface HBaseFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deletes(
        io.lefty.hbase.proto.HBaseProto.Delete request);
  }

  public static class HBaseStub extends io.grpc.stub.AbstractStub<HBaseStub>
      implements HBase {
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

    @java.lang.Override
    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Put> puts(
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_PUTS, getCallOptions()), responseObserver);
    }

    @java.lang.Override
    public void deletes(io.lefty.hbase.proto.HBaseProto.Delete request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETES, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Get> gets(
        io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Result> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_GETS, getCallOptions()), responseObserver);
    }
  }

  public static class HBaseBlockingStub extends io.grpc.stub.AbstractStub<HBaseBlockingStub>
      implements HBaseBlockingClient {
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

    @java.lang.Override
    public com.google.protobuf.Empty deletes(io.lefty.hbase.proto.HBaseProto.Delete request) {
      return blockingUnaryCall(
          getChannel().newCall(METHOD_DELETES, getCallOptions()), request);
    }
  }

  public static class HBaseFutureStub extends io.grpc.stub.AbstractStub<HBaseFutureStub>
      implements HBaseFutureClient {
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

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deletes(
        io.lefty.hbase.proto.HBaseProto.Delete request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETES, getCallOptions()), request);
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final HBase serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
      .addMethod(
        METHOD_PUTS,
        asyncClientStreamingCall(
          new io.grpc.stub.ServerCalls.ClientStreamingMethod<
              io.lefty.hbase.proto.HBaseProto.Put,
              com.google.protobuf.Empty>() {
            @java.lang.Override
            public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Put> invoke(
                io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
              return serviceImpl.puts(responseObserver);
            }
          }))
      .addMethod(
        METHOD_DELETES,
        asyncUnaryCall(
          new io.grpc.stub.ServerCalls.UnaryMethod<
              io.lefty.hbase.proto.HBaseProto.Delete,
              com.google.protobuf.Empty>() {
            @java.lang.Override
            public void invoke(
                io.lefty.hbase.proto.HBaseProto.Delete request,
                io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
              serviceImpl.deletes(request, responseObserver);
            }
          }))
      .addMethod(
        METHOD_GETS,
        asyncBidiStreamingCall(
          new io.grpc.stub.ServerCalls.BidiStreamingMethod<
              io.lefty.hbase.proto.HBaseProto.Get,
              io.lefty.hbase.proto.HBaseProto.Result>() {
            @java.lang.Override
            public io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Get> invoke(
                io.grpc.stub.StreamObserver<io.lefty.hbase.proto.HBaseProto.Result> responseObserver) {
              return serviceImpl.gets(responseObserver);
            }
          })).build();
  }
}
