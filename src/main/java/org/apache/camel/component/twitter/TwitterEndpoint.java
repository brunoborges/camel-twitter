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

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.twitter.consumer.TwitterConsumer;
import org.apache.camel.component.twitter.consumer.search.TwitterSearchConsumer;
import org.apache.camel.component.twitter.consumer.timeline.TwitterFriendsConsumer;
import org.apache.camel.component.twitter.consumer.timeline.TwitterPublicConsumer;
import org.apache.camel.component.twitter.consumer.timeline.TwitterUserConsumer;
import org.apache.camel.component.twitter.producer.TwitterProducer;
import org.apache.camel.impl.DefaultPollingEndpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Represents a direct endpoint that synchronously invokes the consumers of the
 * endpoint when a producer sends a message to it.
 * 
 * @version
 */
public class TwitterEndpoint extends DefaultPollingEndpoint {

	private Twitter twitter;

	private static final Log LOG = LogFactory.getLog(TwitterEndpoint.class);

	public TwitterEndpoint(String uri, TwitterComponent component) {
		super(uri, component);
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		TwitterConsumer tc = null;

		if (type == TimelineType.ALL) {
			tc = new TwitterPublicConsumer(this, processor);
		} else if (type == TimelineType.USER) {
			tc = new TwitterUserConsumer(this, processor);
		} else if (type == TimelineType.FRIENDS) {
			tc = new TwitterFriendsConsumer(this, processor);
		} else if (type == TimelineType.SEARCH) {
			tc = new TwitterSearchConsumer(this, processor);
		}

		configureConsumer(tc);

		return tc;
	}

	public Producer createProducer() throws Exception {
		return new TwitterProducer(this);
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public boolean isSingleton() {
		return true;
	}

	// Twitter properties
	private String user;

	private String pass;

	private int delay = 60;

	private String follow;

	private TimelineType type;

	private String search;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * 
	 * @return update rate in seconds
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * sets update rate in seconds
	 * 
	 * @param updateEvery
	 */
	public void setDelay(int updateEvery) {
		this.delay = updateEvery;
	}

	public void initiate() {
		Set<String> userPass = new LinkedHashSet<String>(2);
		if (getUser() != null && getUser().isEmpty() == false) {
			userPass.add(getUser());
		}

		if (getPass() != null && getPass().isEmpty() == false) {
			userPass.add(getPass());
		}

		if (userPass.size() == 1) {
			throw new IllegalArgumentException(
					"Both user and password must by set or left blank.");
		}

		if (userPass.isEmpty() && getType() == null) {
			LOG.warn("No user was set to authenticate. "
					+ "Public timeline will be used.");
			setType(TimelineType.ALL);
		}

		if (getFollow() != null && getType() == TimelineType.ALL) {
			LOG.warn("Public timeline is set. Endpoint will ignore option "
					+ "to fetch status from user '" + getFollow() + "'.");
		}

		if (getType() != TimelineType.ALL && getType() != TimelineType.SEARCH && userPass.isEmpty()) {
			throw new IllegalArgumentException("Cannot use timeline type '"
					+ getType() + "' without authentication!");
		}

		if (getType() == null && getFollow() != null) {
			LOG.warn("Follow type is not set for user '" + getFollow()
					+ "'. Default to User's timeline.");
			setType(TimelineType.USER);
		}

		if (getType() == null && getFollow() == null) {
			LOG.warn("Following type is not set."
					+ " Default to Friends' timeline.");
			setType(TimelineType.FRIENDS);
		}

		if (getType() == TimelineType.USER && getFollow() == null) {
			throw new IllegalArgumentException(
					"Fetch type set to USER but no user to fetch timeline from was set.");
		}

		if (getFollow() == null) {
			LOG.info("User to fetch status from is not set. "
					+ "Default to fetch from authenticated "
					+ "user's frontpage timeline.");
			setFollow(getUser());
		}

		if (LOG.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Twitter endpoint was set up with:");
			sb.append("\n- user: " + getUser());
			sb.append("\n- pass: " + getPass());
			sb.append("\n- type: " + getType());
			sb.append("\n- following: " + getFollow());
			sb.append("\n- delay: " + getDelay());
			LOG.info(sb);
		}

		if (getType() == TimelineType.SEARCH
				&& (getSearch() == null || getSearch().trim().isEmpty())) {
			throw new IllegalArgumentException(
					"Fetch type set to SEARCH but no search query was set.");
		}

		if (userPass.isEmpty() == false) {
			LOG.info("Instantiating Twitter with credentials");
			Configuration config = new ConfigurationBuilder().setUser(getUser()).setPassword(getPass()).build();
			twitter = new TwitterFactory().getInstance(AuthorizationFactory.getInstance(config));
		} else {
			LOG.info("Instantiating Twitter with NO credentials");
			twitter = new TwitterFactory().getInstance();
		}
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}

	public TimelineType getType() {
		return type;
	}

	public void setType(TimelineType type) {
		this.type = type;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
