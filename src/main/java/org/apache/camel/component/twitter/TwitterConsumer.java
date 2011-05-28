/**
 * 
 */
package org.apache.camel.component.twitter;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

import twitter4j.TwitterException;

abstract class TwitterConsumer extends ScheduledPollConsumer {

	private long lastStatusUpdateID = 1;

	private int delay;

	public TwitterConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);

		delay = endpoint.getDelay();

		setInitialDelay(0);
		setDelay(delay);
		setTimeUnit(TimeUnit.SECONDS);
	}

	protected void poll() throws Exception {
		Iterator<Status> statusIterator = requestStatus();

		Status tStatus = null;
		while (statusIterator.hasNext()) {
			tStatus = statusIterator.next();

			if (tStatus.getId() <= lastStatusUpdateID) {
				return;
			}

			Exchange e = getEndpoint().createExchange();

			e.getIn()
					.setHeader("screenname", tStatus.getUser().getScreenname());
			e.getIn().setHeader("date", tStatus.getDate());

			e.getIn().setBody(tStatus);

			getProcessor().process(e);

			// make sure it ignores updates that were already polled
			long newerStatusID = tStatus.getId();
			if (newerStatusID > lastStatusUpdateID) {
				lastStatusUpdateID = newerStatusID;
			}
		}

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