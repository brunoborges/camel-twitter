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

import junit.framework.Assert;

import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * consumes tweets
 */
public class SearchByExchangeDirectTest extends CamelTwitterTestSupport {
	private static final transient Logger LOG = LoggerFactory
			.getLogger(SearchByExchangeDirectTest.class);

	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	@Produce(uri = "direct:double")
	protected ProducerTemplate templateDouble;

	@Test
	public void testSearchTimelineWithDynamicQuery() throws Exception {
		template.sendBodyAndHeader(null, TwitterConstants.TWITTER_SEARCH_QUERY,
				"java");

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

	@Test
	public void testDoubleSearchKeepingOld() throws Exception {
		templateDouble.sendBodyAndHeader(null,
				TwitterConstants.TWITTER_SEARCH_QUERY, "java");

		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);
		mock.assertIsSatisfied();
		List<Exchange> tweets = mock.getExchanges();
		if (LOG.isInfoEnabled()) {
			for (Exchange e : tweets) {
				LOG.info("Tweet: " + e.getIn().getBody(String.class));
			}
		}

		// calls the same query again, expecting to receive the same amount of
		// tweets, because we told this route to not filter old(consumed) tweets
		int total = mock.getReceivedCounter();

		templateDouble.sendBodyAndHeader(null,
				TwitterConstants.TWITTER_SEARCH_QUERY, "java");

		Assert.assertEquals(total, mock.getReceivedCounter());
	}

	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:start").to("twitter://search?" + getUriTokens())
						.split().body().to("mock:result");

				from("direct:double")
						.to("twitter://search?filterOld=false&"
								+ getUriTokens()).split().body()
						.to("mock:result");
			}
		};
	}
}
