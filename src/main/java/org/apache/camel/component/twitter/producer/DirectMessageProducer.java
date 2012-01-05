/**
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
package org.apache.camel.component.twitter.producer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Produces text as a direct message.
 * 
 * @author Brett E. Meyer (3RiverDev.com)
 */
public class DirectMessageProducer extends DefaultProducer implements Processor {

	private static final transient Log LOG = LogFactory
			.getLog(DirectMessageProducer.class);

	private TwitterEndpoint te;

	public DirectMessageProducer(TwitterEndpoint te) {
		super(te);
		this.te = te;
	}

	public void process(Exchange exchange) throws Exception {
		// send direct message
		String toUsername = te.getProperties().getUser();
		String text = exchange.getIn().getBody(String.class);

		if (toUsername.isEmpty()) {
			LOG.error("Can't send direct message -- no 'user' provided!");
		} else {
			te.getTwitter().sendDirectMessage(toUsername, text);
		}
	}
}
