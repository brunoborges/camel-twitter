package org.apache.camel.component.twitter.consumer;

import java.util.regex.Pattern;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.directmessage.PollingDirectMessageConsumer;
import org.apache.camel.component.twitter.consumer.search.PollingSearchConsumer;
import org.apache.camel.component.twitter.consumer.streaming.PollingFilterConsumer;
import org.apache.camel.component.twitter.consumer.streaming.PollingSampleConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingHomeConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingMentionsConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingPublicConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingRetweetsConsumer;
import org.apache.camel.component.twitter.consumer.timeline.PollingUserConsumer;
import org.apache.camel.component.twitter.data.ConsumerType;
import org.apache.camel.component.twitter.data.StreamingType;
import org.apache.camel.component.twitter.data.TimelineType;
import org.apache.camel.component.twitter.data.TrendsType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * URI STRUCTURE:
 * 
 * timeline/
 * 		public
 * 		home
 * 		friends
 * 		user
 * 		mentions
 * 		retweetsofme
 * user/
 * 		search users (DIRECT ONLY)
 * 		user suggestions (DIRECT ONLY)
 * trends/
 * 		daily
 * 		weekly
 * userlist
 * directmessage
 * streaming/
 * 		filter (POLLING ONLY)
 * 		sample (POLLING ONLY)
 * 
 * @author Brett E. Meyer (3RiverDev.com)
 */
public class TwitterConsumerFactory {
	private static final transient Log LOG = LogFactory.getLog(TwitterConsumerFactory.class);

	public static TwitterConsumerPolling getPollingConsumer(TwitterEndpoint endpoint,
			Processor processor, String uri) throws IllegalArgumentException {
		String[] uriSplit = splitUri(uri);
		
		if (uriSplit.length > 0) {
			switch (ConsumerType.fromUri(uriSplit[0])) {
			case DIRECTMESSAGE:
				return new PollingDirectMessageConsumer(endpoint, processor);
			case SEARCH:
				if (endpoint.getProperties().getKeywords() == null || endpoint.getProperties().getKeywords().trim().isEmpty()) {
					throw new IllegalArgumentException(
							"Type set to SEARCH but no keywords were provided.");
				} else {
					return new PollingSearchConsumer(endpoint, processor);
				}
			case STREAMING:
				switch (StreamingType.fromUri(uriSplit[1])) {
				case SAMPLE:
					return new PollingSampleConsumer(endpoint, processor);
				case FILTER:
					return new PollingFilterConsumer(endpoint, processor);
				}
				break;
			case TIMELINE:
				if (uriSplit.length > 1) {
					switch (TimelineType.fromUri(uriSplit[1])) {
					case HOME:
						return new PollingHomeConsumer(endpoint, processor);
					case MENTIONS:
						return new PollingMentionsConsumer(endpoint, processor);
					case PUBLIC:
						return new PollingPublicConsumer(endpoint, processor);
					case RETWEETSOFME:
						return new PollingRetweetsConsumer(endpoint, processor);
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
				if (uriSplit.length > 1) {
					switch (TrendsType.fromUri(uriSplit[1])) {
					case DAILY:
						// TODO
						break;
					case WEEKLY:
						// TODO
						break;
					}
				}
				break;
			}
		}
		
		LOG.warn("A consumer type was not provided (or an incorrect pairing was used).  Defaulting to Public Timeline!");
		return new PollingPublicConsumer(endpoint, processor);
	}
	
	public static TwitterConsumerDirect getDirectConsumer(TwitterEndpoint endpoint,
			Processor processor, String uri) throws IllegalArgumentException {
		String[] uriSplit = splitUri(uri);
		
		if (uriSplit.length > 0) {
			switch (ConsumerType.fromUri(uriSplit[0])) {
			case DIRECTMESSAGE:
				// TODO
				break;
			case SEARCH:
				if (endpoint.getProperties().getKeywords() == null || endpoint.getProperties().getKeywords().trim().isEmpty()) {
					throw new IllegalArgumentException(
							"Type set to SEARCH but no keywords were provided.");
				} else {
					// TODO
					break;
				}
			case TIMELINE:
				if (uriSplit.length > 1) {
					switch (TimelineType.fromUri(uriSplit[1])) {
					case HOME:
						// TODO
						break;
					case MENTIONS:
						// TODO
						break;
					case PUBLIC:
						// TODO
						break;
					case RETWEETSOFME:
						// TODO
						break;
					case USER:
						if (endpoint.getProperties().getUser() == null || endpoint.getProperties().getUser().trim().isEmpty()) {
							throw new IllegalArgumentException(
									"Fetch type set to USER TIMELINE but no user was set.");
						} else {
							// TODO
							break;
						}
					}
				}
				break;
			case TRENDS:
				if (uriSplit.length > 1) {
					switch (TrendsType.fromUri(uriSplit[1])) {
					case DAILY:
						// TODO
						break;
					case WEEKLY:
						// TODO
						break;
					}
				}
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
//		return new PollingPublicConsumer(endpoint, processor);
		// TODO
		return null;
	}
	
	private static String[] splitUri(String uri) {
		Pattern p1 = Pattern.compile("twitter:(//)*");
		Pattern p2 = Pattern.compile("\\?.*");

		uri = p1.matcher(uri).replaceAll("");
		uri = p2.matcher(uri).replaceAll("");
		
		return uri.split("/");
	}
}
