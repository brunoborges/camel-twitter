/**
 * 
 */
package org.apache.camel.component.twitter.consumer;

import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.direct.DirectConsumer;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.data.Status;

public class TwitterConsumerDirect extends DirectConsumer implements TwitterConsumer {

	private Twitter4JConsumer twitter4jConsumer;
	
	public TwitterConsumerDirect(TwitterEndpoint endpoint, Processor processor, Twitter4JConsumer twitter4jConsumer) {
		super(endpoint, processor);
		
		this.twitter4jConsumer = twitter4jConsumer;
	}
	
	@Override
    protected void doStart() throws Exception {
		super.doStart();
		
		Iterator<Status> statusIterator = twitter4jConsumer.requestDirectStatus();
		while (statusIterator.hasNext()) {
			Status tStatus = statusIterator.next();

			Exchange e = getEndpoint().createExchange();

			e.getIn().setHeader("screenName", tStatus.getUser().getScreenName());
			e.getIn().setHeader("date", tStatus.getDate());

			e.getIn().setBody(tStatus);

			getProcessor().process(e);
		}
	}
}
