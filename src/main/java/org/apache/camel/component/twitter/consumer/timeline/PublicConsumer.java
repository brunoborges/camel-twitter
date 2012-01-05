package org.apache.camel.component.twitter.consumer.timeline;

import java.util.Iterator;
import java.util.List;

import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.Twitter4JConsumer;
import org.apache.camel.component.twitter.data.Status;
import org.apache.camel.component.twitter.util.TwitterConverter;

import twitter4j.TwitterException;

public class PublicConsumer implements Twitter4JConsumer {
	
	TwitterEndpoint te;

	public PublicConsumer(TwitterEndpoint te) {
		this.te = te;
	}

	public Iterator<Status> requestPollingStatus(long lastStatusUpdateId) throws TwitterException {
		return getPublicTimeline();
	}
	
	public Iterator<Status> requestDirectStatus() throws TwitterException {
		return getPublicTimeline();
	}
	
	private Iterator<Status> getPublicTimeline() throws TwitterException {
		List<twitter4j.Status> statusList = te.getTwitter().getPublicTimeline();
		return TwitterConverter.convertStatuses(statusList).iterator();
	}
}
