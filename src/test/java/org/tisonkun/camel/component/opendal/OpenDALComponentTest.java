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

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class OpenDALComponentTest extends CamelTestSupport {

    private final EventBusHelper eventBusHelper = EventBusHelper.getInstance();

    @Test
    @Disabled
    public void testOpenDAL() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(5);

        // Trigger events to subscribers
        simulateEventTrigger();

        mock.await();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("opendal:foo?serviceName=memory")
                        .to("opendal:bar?serviceName=memory")
                        .to("mock:result");
            }
        };
    }

    private void simulateEventTrigger() {
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                final Date now = new Date();
                // publish events to the event bus
                eventBusHelper.publish(now);
            }
        };

        new Timer().scheduleAtFixedRate(task, 1000L, 1000L);
    }
}
