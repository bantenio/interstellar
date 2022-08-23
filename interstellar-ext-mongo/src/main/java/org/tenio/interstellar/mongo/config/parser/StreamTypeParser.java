package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.connection.AsynchronousSocketChannelStreamFactoryFactory;
import com.mongodb.connection.StreamFactoryFactory;
import com.mongodb.connection.netty.NettyStreamFactoryFactory;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;

import java.util.Optional;

public class StreamTypeParser {
    private final MongoClientProperties config;

    public StreamTypeParser(MongoClientProperties config) {
        this.config = config;
    }

    public Optional<StreamFactoryFactory> streamFactory() {
        return Optional.ofNullable(config.getStreamType())
                .map(StreamType::parse)
                .map(StreamType::streamFactory);
    }

    private enum StreamType {
        NIO2 {
            @Override
            StreamFactoryFactory streamFactory() {
                return AsynchronousSocketChannelStreamFactoryFactory.builder().build();
            }
        },

        NETTY {
            @Override
            StreamFactoryFactory streamFactory() {
                return NettyStreamFactoryFactory.builder().build();
            }
        };

        abstract StreamFactoryFactory streamFactory();

        static StreamType parse(String streamType) {
            try {
                return valueOf(streamType.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Not supported StreamType. Supported values are [nio2|netty]");
            }
        }
    }
}
