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
import org.apache.camel.impl.ScheduledPollConsumer;

public class TwitterConsumerPolling extends ScheduledPollConsumer implements TwitterConsumer {

	private Twitter4JConsumer twitter4jConsumer;
	
	private long lastStatusUpdateID = 1;

	private int delay;

	public TwitterConsumerPolling(TwitterEndpoint endpoint, Processor processor, Twitter4JConsumer twitter4jConsumer) {
		super(endpoint, processor);
		
		this.twitter4jConsumer = twitter4jConsumer;

		delay = endpoint.getProperties().getDelay();

		setInitialDelay(0);
		setDelay(delay);
		setTimeUnit(TimeUnit.SECONDS);
	}

	protected int poll() throws Exception {
		Iterator<Status> statusIterator = twitter4jConsumer.requestPollingStatus(lastStatusUpdateID);

		int total = 0;
		while (statusIterator.hasNext()) {
			Status tStatus = statusIterator.next();

//			if (tStatus.getId() <= lastStatusUpdateID) {
//				return 0;
//			}

			Exchange e = getEndpoint().createExchange();

			e.getIn().setHeader("screenName", tStatus.getUser().getScreenName());
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

	public long getLastStatusUpdateID() {
		return lastStatusUpdateID;
	}
}
