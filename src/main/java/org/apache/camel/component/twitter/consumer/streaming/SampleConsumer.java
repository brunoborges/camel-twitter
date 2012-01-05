package org.apache.camel.component.twitter.consumer.streaming;

import org.apache.camel.component.twitter.TwitterEndpoint;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class SampleConsumer extends StreamingConsumer {
	
	public SampleConsumer(TwitterEndpoint te) {
		super(te);
		
		TwitterStream twitterStream = new TwitterStreamFactory(
				te.getProperties().getConfiguration()).getInstance();
		twitterStream.addListener(this);
	    twitterStream.sample();
	}
}
