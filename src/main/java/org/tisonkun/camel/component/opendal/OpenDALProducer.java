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

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.apache.opendal.BlockingOperator;

@Slf4j
public class OpenDALProducer extends DefaultProducer {
    private final OpenDALEndpoint endpoint;
    private final BlockingOperator operator;

    public OpenDALProducer(OpenDALEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
        this.operator = new BlockingOperator(endpoint.getServiceName(), endpoint.options);
    }

    public void process(Exchange exchange) throws Exception {
        operator.write(endpoint.getRootPath(), exchange.getIn().getMessageId());
        System.out.println(exchange.getIn().getBody());
    }
}
