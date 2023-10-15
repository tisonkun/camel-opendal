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

import java.util.concurrent.ExecutorService;
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
 * OpenDAL component which does bla bla.
 *
 * TODO: Update one line description above what the component does, and update Category.
 */
@UriEndpoint(
        firstVersion = "0.1.0-SNAPSHOT",
        scheme = "opendal",
        title = "OpenDAL",
        syntax = "opendal:name",
        category = {Category.DATABASE})
public class OpenDALEndpoint extends DefaultEndpoint {
    @UriPath
    @Metadata(required = true)
    private String name;

    @UriParam(defaultValue = "10")
    private int option = 10;

    public OpenDALEndpoint() {}

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

    /**
     * Some description of this option, and what it does
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Some description of this option, and what it does
     */
    public void setOption(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }

    public ExecutorService createExecutor() {
        // TODO: Delete me when you implemented your custom component
        return getCamelContext().getExecutorServiceManager().newSingleThreadExecutor(this, "OpenDALConsumer");
    }
}
