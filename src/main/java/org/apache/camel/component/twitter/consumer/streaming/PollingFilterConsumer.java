package org.apache.camel.component.twitter.consumer.streaming;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class PollingFilterConsumer extends PollingStreamingConsumer {
	
	public PollingFilterConsumer(TwitterEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		
		TwitterStream twitterStream = new TwitterStreamFactory(
				endpoint.getProperties().getConfiguration()).getInstance();
		twitterStream.addListener(this);
		
		String allLocationsString = endpoint.getProperties().getLocations();
		String[] locationStrings = allLocationsString.split(";");
		double[][] locations = new double[locationStrings.length][2];
		for (int i = 0; i < locationStrings.length; i++) {
			String[] coords = locationStrings[i].split(",");
			locations[i][0] = Double.valueOf(coords[0]);
			locations[i][1] = Double.valueOf(coords[1]);
		}
		
		FilterQuery fq = new FilterQuery();
		fq.locations(locations);
		
	    twitterStream.filter(fq);
	}
}
