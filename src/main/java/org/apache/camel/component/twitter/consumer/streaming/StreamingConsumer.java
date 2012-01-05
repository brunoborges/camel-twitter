package org.apache.camel.component.twitter.consumer.streaming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.Twitter4JConsumer;
import org.apache.camel.component.twitter.data.Status;
import org.apache.camel.component.twitter.util.TwitterConverter;

import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;

public class StreamingConsumer implements Twitter4JConsumer, StatusListener {
	
	TwitterEndpoint te;
	
	private List<Status> receivedStatuses = new ArrayList<Status>();
	
	private boolean clear = false;

	public StreamingConsumer(TwitterEndpoint te) {
		this.te = te;
	}

	public Iterator<Status> requestPollingStatus(long lastStatusUpdateId) throws TwitterException {
		clear = true;
		return Collections.unmodifiableList(receivedStatuses).iterator();
	}

	public Iterator<Status> requestDirectStatus() throws TwitterException {
		// not used
		return null;
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatus(twitter4j.Status status) {
		if (clear) {
			receivedStatuses.clear();
			clear = false;
		}
		receivedStatuses.add(TwitterConverter.convertStatus(status));
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub
		
	}

}
