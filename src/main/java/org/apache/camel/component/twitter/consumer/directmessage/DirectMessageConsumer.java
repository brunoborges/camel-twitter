package org.apache.camel.component.twitter.consumer.directmessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.Twitter4JConsumer;
import org.apache.camel.component.twitter.data.Status;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.TwitterException;

public class DirectMessageConsumer implements Twitter4JConsumer {
	
	TwitterEndpoint te;

	public DirectMessageConsumer(TwitterEndpoint te) {
		this.te = te;
	}

	public Iterator<Status> requestPollingStatus(long lastStatusUpdateId) throws TwitterException {
		return getIterator(te.getTwitter().getDirectMessages(
				new Paging(lastStatusUpdateId)));
	}
	
	public Iterator<Status> requestDirectStatus() throws TwitterException {
		return getIterator(te.getTwitter().getDirectMessages());
	}
	
	private Iterator<Status> getIterator(List<DirectMessage> messagesList) {
		List<Status> statusCamelTweet = new ArrayList<Status>(messagesList.size());
		for (Iterator<DirectMessage> i = messagesList.iterator(); i.hasNext();) {
			statusCamelTweet.add(new Status(i.next()));
		}
		return statusCamelTweet.iterator();
	}
}
