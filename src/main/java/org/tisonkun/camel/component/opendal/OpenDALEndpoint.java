/*
 * Copyright 2023 tison <wander4096@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tisonkun.camel.component.opendal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.camel.Category;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.apache.camel.support.DefaultEndpoint;

/**
 * Store and retrieve objects from various storage services via OpenDAL.
 */
@Getter
@Setter
@NoArgsConstructor
@UriEndpoint(
        firstVersion = "0.1.0-SNAPSHOT",
        scheme = "opendal",
        title = "OpenDAL",
        syntax = "opendal://rootPath",
        category = {
            Category.CACHE,
            Category.CLOUD,
            Category.DATABASE,
            Category.FILE,
        })
public class OpenDALEndpoint extends DefaultEndpoint {
    public final Map<String, String> options = new HashMap<>();

    /**
     * The root path to construct the OpenDAL operator.
     */
    @UriPath
    @Metadata(required = true)
    private String rootPath;

    /**
     * The name of the backed storage service.
     */
    @UriParam
    @Metadata(required = true)
    private String serviceName;

    public OpenDALEndpoint(String uri, OpenDALComponent component) {
        super(uri, component);
    }

    public Producer createProducer() throws Exception {
        return new OpenDALProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        Consumer consumer = new OpenDALConsumer(this, processor);
        configureConsumer(consumer);
        return consumer;
    }

    public ExecutorService createExecutor() {
        return getCamelContext().getExecutorServiceManager().newSingleThreadExecutor(this, "OpenDALConsumer");
    }
}
