package org.apache.camel.component.twitter.consumer.streaming;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class PollingSampleConsumer extends PollingStreamingConsumer {
	
	public PollingSampleConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		
		TwitterStream twitterStream = new TwitterStreamFactory(
				endpoint.getProperties().getConfiguration()).getInstance();
		twitterStream.addListener(this);
	    twitterStream.sample();
	}
}
