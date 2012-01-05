package org.apache.camel.component.twitter.consumer.timeline;

import java.util.Iterator;
import java.util.List;

import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.Twitter4JConsumer;
import org.apache.camel.component.twitter.data.Status;
import org.apache.camel.component.twitter.util.TwitterConverter;

import twitter4j.Paging;
import twitter4j.TwitterException;

public class HomeConsumer implements Twitter4JConsumer {
	
	TwitterEndpoint te;

	public HomeConsumer(TwitterEndpoint te) {
		this.te = te;
	}

	public Iterator<Status> requestPollingStatus(long lastStatusUpdateId) throws TwitterException {
		List<twitter4j.Status> statusList = te.getTwitter().getHomeTimeline(
				new Paging(lastStatusUpdateId));
		return TwitterConverter.convertStatuses(statusList).iterator();
	}
	
	public Iterator<Status> requestDirectStatus() throws TwitterException {
		List<twitter4j.Status> statusList = te.getTwitter().getHomeTimeline();
		return TwitterConverter.convertStatuses(statusList).iterator();
	}
}
