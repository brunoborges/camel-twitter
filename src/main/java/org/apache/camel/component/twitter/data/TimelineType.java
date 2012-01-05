package org.apache.camel.component.twitter.data;


/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public enum TimelineType {
	PUBLIC, HOME, USER, MENTIONS, RETWEETSOFME, UNKNOWN;
	
	public static TimelineType fromUri(String uri) {
		 for (TimelineType timelineType : TimelineType.values()) {
			 if (timelineType.name().equalsIgnoreCase(uri)) {
				 return timelineType;
			 }
		 }
		 return TimelineType.UNKNOWN;
	 }
}
