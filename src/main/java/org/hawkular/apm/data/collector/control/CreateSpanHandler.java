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

import org.hawkular.apm.proto.SpanRequest;

import io.vertx.ext.web.RoutingContext;

/**
 * @author Juraci Paixão Kröhling
 */
public class CreateSpanHandler {
    private static final Logger logger = Logger.getLogger(CreateSpanHandler.class.getName());

    public void handle(RoutingContext routingContext) {
        routingContext.request().bodyHandler(b -> {
            // here we would do some validation on the data, like some basic sanity and boundary checks
            // for now, we just pass it to the next component
            SpanRequest request = SpanRequest.newBuilder().setName(b.toJsonObject().getString("name")).build();
            logger.finest(String.format("The following span will be sent to the publisher: %s", request.getName()));
            new CreateSpanCommand(request).execute();
            routingContext.response().setStatusCode(204).end();
            logger.finest(String.format("Span command executed for span '%s'", request.getName()));
        });
    }
}
