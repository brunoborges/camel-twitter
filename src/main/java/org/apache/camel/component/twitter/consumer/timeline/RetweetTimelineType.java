package org.apache.camel.component.twitter.consumer.timeline;

/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public enum RetweetTimelineType {
	OTHERS, YOU, YOURTWEETS, UNKNOWN;
	
	public static RetweetTimelineType fromUri(String uri) {
		 for (RetweetTimelineType timelineType : RetweetTimelineType.values()) {
			 if (timelineType.name().equalsIgnoreCase(uri)) {
				 return timelineType;
			 }
		 }
		 return RetweetTimelineType.UNKNOWN;
	 }
}
