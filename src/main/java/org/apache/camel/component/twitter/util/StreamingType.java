package org.apache.camel.component.twitter.util;


/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public enum StreamingType {
	SAMPLE, FILTER, UNKNOWN;
	
	public static StreamingType fromUri(String uri) {
		 for (StreamingType streamType : StreamingType.values()) {
			 if (streamType.name().equalsIgnoreCase(uri)) {
				 return streamType;
			 }
		 }
		 return StreamingType.UNKNOWN;
	 }
}
