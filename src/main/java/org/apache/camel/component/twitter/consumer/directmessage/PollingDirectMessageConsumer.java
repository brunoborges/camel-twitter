package org.apache.camel.component.twitter.consumer.directmessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.TwitterConsumerPolling;
import org.apache.camel.component.twitter.data.Status;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.TwitterException;

public class PollingDirectMessageConsumer extends TwitterConsumerPolling {

	public PollingDirectMessageConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
	}

	@Override
	protected Iterator<Status> requestStatus() throws TwitterException {
		TwitterEndpoint te = (TwitterEndpoint) getEndpoint();
		List<DirectMessage> messagesList = te.getTwitter().getDirectMessages(
				new Paging(getLastStatusUpdateID()));

		List<Status> statusCamelTweet = new ArrayList<Status>(messagesList.size());
		for (Iterator<DirectMessage> i = messagesList.iterator(); i.hasNext();) {
			statusCamelTweet.add(new Status(i.next()));
		}
		return statusCamelTweet.iterator();
	}

}
