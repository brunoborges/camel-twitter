package org.apache.camel.component.twitter.consumer.timeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.TwitterConsumer;
import org.apache.camel.component.twitter.data.Status;

import twitter4j.Paging;
import twitter4j.TwitterException;

public class TwitterFriendsConsumer extends TwitterConsumer {

	public TwitterFriendsConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	protected Iterator<Status> requestStatus() throws TwitterException {
		TwitterEndpoint te = (TwitterEndpoint) getEndpoint();
		List<twitter4j.Status> statusList = te.getTwitter().getFriendsTimeline(new Paging(getLastStatusUpdateID()));

		List<Status> statusCamelTweet = new ArrayList<Status>(statusList.size());
		for (Iterator<twitter4j.Status> i = statusList.iterator(); i.hasNext();) {
			statusCamelTweet.add(convertStatus(i.next()));
		}
		return statusCamelTweet.iterator();
	}

}
