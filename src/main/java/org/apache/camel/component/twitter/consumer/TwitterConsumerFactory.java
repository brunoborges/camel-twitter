package org.apache.camel.component.twitter.consumer;

import java.util.regex.Pattern;

import org.apache.camel.Processor;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.consumer.search.TwitterSearchConsumer;
import org.apache.camel.component.twitter.consumer.timeline.RetweetTimelineType;
import org.apache.camel.component.twitter.consumer.timeline.TimelineType;
import org.apache.camel.component.twitter.consumer.timeline.TwitterHomeConsumer;
import org.apache.camel.component.twitter.consumer.timeline.TwitterPublicConsumer;
import org.apache.camel.component.twitter.consumer.timeline.TwitterUserConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public class TwitterConsumerFactory {
	private static final transient Log LOG = LogFactory.getLog(TwitterConsumerFactory.class);

	public static TwitterConsumer getConsumer(TwitterEndpoint endpoint,
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
		 * 		firehose
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
				if (endpoint.getSearch() == null || endpoint.getSearch().trim().isEmpty()) {
					throw new IllegalArgumentException(
							"Fetch type set to SEARCH but no search query was set.");
				} else {
					return new TwitterSearchConsumer(endpoint, processor);
				}
			case STREAMING:
				// TODO
				break;
			case TIMELINE:
				if (typeSplit.length > 1) {
					switch (TimelineType.fromUri(typeSplit[1])) {
					case HOME:
						return new TwitterHomeConsumer(endpoint, processor);
					case MENTIONS:
						// TODO
						break;
					case PUBLIC:
						return new TwitterPublicConsumer(endpoint, processor);
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
						if (endpoint.getUser() == null || endpoint.getUser().trim().isEmpty()) {
							throw new IllegalArgumentException(
									"Fetch type set to USER TIMELINE but no user was set.");
						} else {
							return new TwitterUserConsumer(endpoint, processor);
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
		
		LOG.warn("A fetch type was not provided (or an incorrect pairing was used).  Defaulting to PUBLIC!");
		return new TwitterPublicConsumer(endpoint, processor);
	}
}
