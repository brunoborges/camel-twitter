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
import org.apache.camel.Producer;
import org.apache.camel.component.twitter.consumer.TwitterConsumer;
import org.apache.camel.component.twitter.consumer.TwitterConsumerFactory;
import org.apache.camel.component.twitter.producer.TwitterProducer;
import org.apache.camel.impl.DefaultPollingEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Represents a direct endpoint that synchronously invokes the consumers of the
 * endpoint when a producer sends a message to it.
 */
public class TwitterEndpoint extends DefaultPollingEndpoint {

	private static final Log LOG = LogFactory.getLog(TwitterEndpoint.class);

	private Twitter twitter;

	// Twitter properties
	
	private String consumerKey = "";
	
	private String consumerSecret = "";
	
	private String accessToken = "";
	
	private String accessTokenSecret = "";

	private int delay = 60;

	private String user;

	private String search;

	public TwitterEndpoint(String uri, TwitterComponent component) {
		super(uri, component);
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		TwitterConsumer tc = TwitterConsumerFactory.getConsumer(
				this, processor, getEndpointUri());
		configureConsumer(tc);
		return tc;
	}

	public Producer createProducer() throws Exception {
		return new TwitterProducer(this);
	}

	public void initiate() {
		if (consumerKey.isEmpty() || consumerSecret.isEmpty()
					|| accessToken.isEmpty() || accessTokenSecret.isEmpty()) {
			throw new IllegalArgumentException(
					"consumerKey, consumerSecret, accessToken, and accessTokenSecret must be set!");
		}
		
		ConfigurationBuilder confbuilder  = new ConfigurationBuilder();
		confbuilder.setOAuthConsumerKey(consumerKey);
		confbuilder.setOAuthConsumerSecret(consumerSecret);
		confbuilder.setOAuthAccessToken(accessToken);
		confbuilder.setOAuthAccessTokenSecret(accessTokenSecret);
		
		twitter = new TwitterFactory(confbuilder.build()).getInstance();
		LOG.info("Successfully instantiated Twitter!");
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public boolean isSingleton() {
		return true;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
