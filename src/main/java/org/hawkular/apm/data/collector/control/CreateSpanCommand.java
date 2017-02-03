/*
 * Copyright 2017 Juraci Paixão Kröhling
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.apm.data.collector.control;

import java.util.logging.Logger;

import org.hawkular.apm.proto.DataPublisherGrpc;
import org.hawkular.apm.proto.SpanRequest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * @author Juraci Paixão Kröhling
 */
final class CreateSpanCommand extends HystrixCommand<Void> {
    private static final Logger logger = Logger.getLogger(CreateSpanHandler.class.getName());
    private final SpanRequest request;
    private final ManagedChannel channel;
    private final DataPublisherGrpc.DataPublisherBlockingStub blockingStub;

    CreateSpanCommand(SpanRequest name) {
        super(HystrixCommandGroupKey.Factory.asKey("CreateSpanGroup"));
        Configuration configuration = ConfigurationResources.getInstance().getConfiguration();
        this.channel = ManagedChannelBuilder
                .forAddress(configuration.getDataPublisherHostname(), configuration.getDataPublisherPort())
                .usePlaintext(true) // not using SSL, at least not now
                .build();
        this.blockingStub = DataPublisherGrpc.newBlockingStub(channel);
        this.request = name;
    }

    @Override
    protected Void run() {
        try {
            blockingStub.publish(request);
            logger.finest("Span sent to publisher.");
        } catch (StatusRuntimeException e) {
            logger.warning(String.format("RPC failed: %s", e.getStatus()));
        }
        return null;
    }

    @Override
    protected Void getFallback() {
        // we would probably add to some internal queue, to send it again later
        // it could still fail, if we reach too many items on the queue
        logger.finest("Could not send Span to publisher. Falling back.");
        return null;
    }
}