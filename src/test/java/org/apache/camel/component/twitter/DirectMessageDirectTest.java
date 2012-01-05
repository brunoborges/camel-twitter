/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.twitter;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * consumes tweets
 */
public class DirectMessageDirectTest extends CamelTwitterTestSupport {
    private static final transient Log LOG = LogFactory.getLog(
    		DirectMessageDirectTest.class);
    

    @Test
    public void testReadDirectMessage() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.assertIsSatisfied();
        List<Exchange> tweets = mock.getExchanges();
        if (LOG.isInfoEnabled()) {
            for (Exchange e : tweets) {
                LOG.info("Tweet: " + e.getIn().getBody(String.class));
            }
        }
    }

    // get around junit warning
    @Test
    public void testNothing() throws Exception {
        // dummy test method
    }

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("twitter://directmessage?type=direct&" + getUriTokens())
                   .transform(body().convertToString())
                   .to("mock:result");
            }
        };
    }
}
