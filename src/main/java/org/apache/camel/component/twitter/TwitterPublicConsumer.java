package org.apache.camel.component.twitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Processor;

import twitter4j.TwitterException;

public class TwitterPublicConsumer extends TwitterConsumer {

	public TwitterPublicConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	protected Iterator<Status> requestStatus() throws TwitterException {
		List<twitter4j.Status> statusList = null;

		TwitterEndpoint te = (TwitterEndpoint) getEndpoint();
		if (getLastStatusUpdateID() == 0) {
			statusList = te.getTwitter().getPublicTimeline();
		} else {
			statusList = te.getTwitter().getPublicTimeline(
					getLastStatusUpdateID());
		}

		List<Status> statusCamelTweet = new ArrayList<Status>(statusList.size());
		for (Iterator<twitter4j.Status> i = statusList.iterator(); i.hasNext();) {
			statusCamelTweet.add(convertStatus(i.next()));
		}

		return statusCamelTweet.iterator();
	}

}
