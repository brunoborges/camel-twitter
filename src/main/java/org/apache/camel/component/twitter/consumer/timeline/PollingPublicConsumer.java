package org.apache.camel.component.twitter.consumer.timeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.TwitterConsumerPolling;
import org.apache.camel.component.twitter.data.Status;

import twitter4j.TwitterException;

public class PollingPublicConsumer extends TwitterConsumerPolling {

	public PollingPublicConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	protected Iterator<Status> requestStatus() throws TwitterException {
		List<twitter4j.Status> statusList = null;

		TwitterEndpoint te = (TwitterEndpoint) getEndpoint();
		statusList = te.getTwitter().getPublicTimeline();

		// FIXME should search for tweets since last tweet
		// previous code could do this, but now API of twitter4j has changed
		// must think about another way
		
		List<Status> statusCamelTweet = new ArrayList<Status>(statusList.size());
		for (Iterator<twitter4j.Status> i = statusList.iterator(); i.hasNext();) {
			statusCamelTweet.add(convertStatus(i.next()));
		}

		return statusCamelTweet.iterator();
	}

}
