#!/bin/bash

/usr/local/bin/protoc --java_out=client/src/main/java --proto_path=. --proto_path=/usr/local/include                  \
    --java_plugin_out=client/src/main/java                                                                            \
    --plugin=protoc-gen-java_plugin=/opt/grpc-java/compiler/build/exe/java_plugin/protoc-gen-grpc-java \
    proto/hbase.proto
