/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.twitter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.TwitterException;

public class TwitterProducer extends DefaultProducer implements Processor {

	private static final transient Log LOG = LogFactory
			.getLog(TwitterProducer.class);

	private TwitterEndpoint endpoint;

	public TwitterProducer(TwitterEndpoint endpoint) {
		super(endpoint);
		this.endpoint = endpoint;
	}

	public void process(Exchange exchange) throws Exception {
		// update twitter's status
		updateStatus(exchange);
	}

	private void updateStatus(Exchange exchange) throws TwitterException {
		String status = exchange.getIn().getBody(String.class);

		if (status.length() > 160) {
			LOG.warn("Message is longer than 160 characteres "
					+ "and it will be truncated!");
			status.substring(0, 160);
		}

		if (endpoint.getTwitter().getAuthorization().isEnabled()) {
			endpoint.getTwitter().updateStatus(status);
		} else {
			LOG.error("Trying to update a non-authenticated Twitter user!");
		}
	}

}
