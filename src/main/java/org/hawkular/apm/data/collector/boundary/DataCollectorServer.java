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
package org.hawkular.apm.data.collector.boundary;

import org.hawkular.apm.data.collector.control.CreateSpanHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * @author Juraci Paixão Kröhling
 */
public class DataCollectorServer {
    public static void start(String bindTo, int port) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.post("/v2/span").handler(new CreateSpanHandler()::handle);
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(port, bindTo);
    }
}
