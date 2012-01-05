/**
 * 
 */
package org.apache.camel.component.twitter.consumer;

import java.util.Iterator;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.data.Status;
import org.apache.camel.impl.ScheduledPollConsumer;

import twitter4j.TwitterException;

public abstract class TwitterConsumerDirect extends ScheduledPollConsumer implements TwitterConsumer {

	private long lastStatusUpdateID = 1;

	public TwitterConsumerDirect(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	protected final Status convertStatus(twitter4j.Status s) {
		return new Status(s);
	}

	public long getLastStatusUpdateID() {
		return lastStatusUpdateID;
	}

	protected abstract Iterator<Status> requestStatus()
			throws TwitterException;
}
