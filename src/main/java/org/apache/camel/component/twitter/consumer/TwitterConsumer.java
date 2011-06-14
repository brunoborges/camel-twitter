/**
 * 
 */
package org.apache.camel.component.twitter.consumer;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.data.Status;
import org.apache.camel.component.twitter.data.StatusImpl;
import org.apache.camel.component.twitter.data.User;
import org.apache.camel.component.twitter.data.UserImpl;
import org.apache.camel.impl.ScheduledPollConsumer;

import twitter4j.TwitterException;

public abstract class TwitterConsumer extends ScheduledPollConsumer {

	private long lastStatusUpdateID = 1;

	private int delay;

	public TwitterConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);

		delay = endpoint.getDelay();

		setInitialDelay(0);
		setDelay(delay);
		setTimeUnit(TimeUnit.SECONDS);
	}

	protected int poll() throws Exception {
		Iterator<Status> statusIterator = requestStatus();

		Status tStatus = null;
		int total = 0;
		while (statusIterator.hasNext()) {
			tStatus = statusIterator.next();

			if (tStatus.getId() <= lastStatusUpdateID) {
				return 0;
			}

			Exchange e = getEndpoint().createExchange();

			e.getIn()
					.setHeader("screenname", tStatus.getUser().getScreenname());
			e.getIn().setHeader("date", tStatus.getDate());

			e.getIn().setBody(tStatus);

			getProcessor().process(e);

			total++;
			// make sure it ignores updates that were already polled
			long newerStatusID = tStatus.getId();
			if (newerStatusID > lastStatusUpdateID) {
				lastStatusUpdateID = newerStatusID;
			}
		}

		return total;
	}

	protected final Status convertStatus(twitter4j.Status s) {
		User user = new UserImpl(s.getUser());
		Status status = new StatusImpl(s, user);
		return status;
	}

	protected final long getLastStatusUpdateID() {
		return lastStatusUpdateID;
	}

	protected abstract Iterator<Status> requestStatus()
			throws TwitterException;
}
