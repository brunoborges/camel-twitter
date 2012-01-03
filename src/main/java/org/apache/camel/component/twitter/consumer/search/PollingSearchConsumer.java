package org.apache.camel.component.twitter.consumer.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.TwitterConsumerPolling;
import org.apache.camel.component.twitter.data.Status;
import org.apache.camel.component.twitter.data.User;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.TwitterException;

public class PollingSearchConsumer extends TwitterConsumerPolling {

	public PollingSearchConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	protected Iterator<Status> requestStatus() throws TwitterException {
		TwitterEndpoint te = (TwitterEndpoint) getEndpoint();
		String keywords = te.getProperties().getKeywords();
		Query query = new Query(keywords);
		query.setSinceId(getLastStatusUpdateID());
		QueryResult qr = te.getTwitter().search(query);
		List<Tweet> tweets = qr.getTweets();

		List<Status> statusCamel = new ArrayList<Status>(tweets.size());
		Iterator<Tweet> i = tweets.iterator();
		while (i.hasNext()) {
			Tweet tweet = i.next();
			final String username = tweet.getFromUser();
			statusCamel.add(new Status(new User(username), tweet.getId(),
					tweet.getText(), tweet.getCreatedAt(), false));
		}

		return statusCamel.iterator();
	}

}
