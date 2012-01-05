package org.apache.camel.component.twitter.consumer;

import java.util.regex.Pattern;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.search.PollingSearchConsumer;
import org.apache.camel.component.twitter.consumer.streaming.PollingFilterConsumer;
import org.apache.camel.component.twitter.consumer.streaming.PollingSampleConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingHomeConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingPublicConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingUserConsumer;
import org.apache.camel.component.twitter.consumer.timeline.RetweetTimelineType;
import org.apache.camel.component.twitter.util.ConsumerType;
import org.apache.camel.component.twitter.util.StreamingType;
import org.apache.camel.component.twitter.util.TimelineType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public class TwitterConsumerFactory {
	private static final transient Log LOG = LogFactory.getLog(TwitterConsumerFactory.class);

	public static TwitterConsumerPolling getPollingConsumer(TwitterEndpoint endpoint,
			Processor processor, String uri) throws IllegalArgumentException {
		
		/** URI STRUCTURE:
		 * 
		 * timeline/
		 * 		public
		 * 		home
		 * 		friends
		 * 		user
		 * 		mentions
		 * 		retweets/
		 * 			others
		 * 			you
		 * 			yourtweets
		 * user/
		 * 		search users
		 * 		user suggestions
		 * trends
		 * userlist
		 * directmessage
		 * geo/
		 * 		search
		 * 		reversegeocode
		 * streaming/
		 * 		filter
		 * 		sample
		 */
		
		Pattern p1 = Pattern.compile("twitter:(//)*");
		Pattern p2 = Pattern.compile("\\?.*");

		uri = p1.matcher(uri).replaceAll("");
		uri = p2.matcher(uri).replaceAll("");
		
		String[] typeSplit = uri.split("/");
		
		if (typeSplit.length > 0) {
			switch (ConsumerType.fromUri(typeSplit[0])) {
			case DIRECTMESSAGE:
				// TODO
				break;
			case GEO:
				// TODO
				break;
			case SEARCH:
				if (endpoint.getProperties().getKeywords() == null || endpoint.getProperties().getKeywords().trim().isEmpty()) {
					throw new IllegalArgumentException(
							"Type set to SEARCH but no keywords were provided.");
				} else {
					return new PollingSearchConsumer(endpoint, processor);
				}
			case STREAMING:
				switch (StreamingType.fromUri(typeSplit[1])) {
				case SAMPLE:
					return new PollingSampleConsumer(endpoint, processor);
				case FILTER:
					return new PollingFilterConsumer(endpoint, processor);
				}
				break;
			case TIMELINE:
				if (typeSplit.length > 1) {
					switch (TimelineType.fromUri(typeSplit[1])) {
					case HOME:
						return new PollingHomeConsumer(endpoint, processor);
					case MENTIONS:
						// TODO
						break;
					case PUBLIC:
						return new PollingPublicConsumer(endpoint, processor);
					case RETWEETS:
						if (typeSplit.length > 2) {
							switch (RetweetTimelineType.fromUri(typeSplit[2])) {
							case OTHERS:
								// TODO
								break;
							case YOU:
								// TODO
								break;
							case YOURTWEETS:
								// TODO
								break;
							}
						}
						break;
					case USER:
						if (endpoint.getProperties().getUser() == null || endpoint.getProperties().getUser().trim().isEmpty()) {
							throw new IllegalArgumentException(
									"Fetch type set to USER TIMELINE but no user was set.");
						} else {
							return new PollingUserConsumer(endpoint, processor);
						}
					}
				}
				break;
			case TRENDS:
				// TODO
				break;
			case USER:
				// TODO
				break;
			case USERLIST:
				// TODO
				break;
			}
		}
		
		LOG.warn("A consumer type was not provided (or an incorrect pairing was used).  Defaulting to Public Timeline!");
		return new PollingPublicConsumer(endpoint, processor);
	}
	
	public static TwitterConsumerDirect getDirectConsumer(TwitterEndpoint endpoint,
			Processor processor, String uri) throws IllegalArgumentException {
		// TODO
		return null;
	}
}
