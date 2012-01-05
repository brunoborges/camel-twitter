package org.apache.camel.component.twitter.consumer.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.Twitter4JConsumer;
import org.apache.camel.component.twitter.data.Status;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.TwitterException;

public class SearchConsumer implements Twitter4JConsumer {

	TwitterEndpoint te;
	
	public SearchConsumer(TwitterEndpoint te) {
		this.te = te;
	}

	public Iterator<Status> requestPollingStatus(long lastStatusUpdateId) throws TwitterException {
		String keywords = te.getProperties().getKeywords();
		Query query = new Query(keywords);
		query.setSinceId(lastStatusUpdateId);
		return search(query);
	}
	
	public Iterator<Status> requestDirectStatus() throws TwitterException {
		String keywords = te.getProperties().getKeywords();
		return search(new Query(keywords));
	}
	
	private Iterator<Status> search(Query query) throws TwitterException {
		QueryResult qr = te.getTwitter().search(query);
		List<Tweet> tweets = qr.getTweets();

		List<Status> statusCamel = new ArrayList<Status>(tweets.size());
		Iterator<Tweet> i = tweets.iterator();
		while (i.hasNext()) {
			statusCamel.add(new Status(i.next()));
		}

		return statusCamel.iterator();
	}
}
