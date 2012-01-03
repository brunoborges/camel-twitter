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

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.component.twitter.consumer.TwitterConsumerFactory;
import org.apache.camel.component.twitter.util.TwitterProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Represents a polling endpoint that synchronously invokes the consumers.
 */
public class TwitterEndpointDirect extends DirectEndpoint implements TwitterEndpoint {

	private static final Log LOG = LogFactory.getLog(TwitterEndpointDirect.class);

	private Twitter twitter;
	
	private TwitterProperties properties;

	public TwitterEndpointDirect(String uri, TwitterComponent component, TwitterProperties properties) {
		super(uri, component);
		this.properties = properties;
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		return TwitterConsumerFactory.getDirectConsumer(
				this, processor, getEndpointUri());
	}

//	public Producer createProducer() throws Exception {
//		return new TwitterProducer(this);
//	}

	public void initiate() {
		properties.checkComplete();
		
		try {
			twitter = new TwitterFactory(properties.getConfiguration()).getInstance();
		} catch (Exception e) {
			LOG.error("Could not instantiate Twitter!  Exception: " + e.getMessage());
		} finally {
			LOG.info("Successfully instantiated Twitter!");
		}
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public boolean isSingleton() {
		return true;
	}

	public TwitterProperties getProperties() {
		return properties;
	}
}
