package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.connection.AsynchronousSocketChannelStreamFactoryFactory;
import com.mongodb.connection.StreamFactoryFactory;
import com.mongodb.connection.netty.NettyStreamFactoryFactory;
import org.tenio.interstellar.mongo.config.MongoClientProperties;

import java.util.Optional;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class StreamTypeParser {
    private final MongoClientProperties config;

    /**
     *
     * TODO
     *
     * @param config TODO
     */
    public StreamTypeParser(MongoClientProperties config) {
        this.config = config;
    }

    /**
     *
     * TODO
     *
     * @return TODO
     */
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
