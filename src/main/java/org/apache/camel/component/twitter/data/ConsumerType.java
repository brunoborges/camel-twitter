package org.apache.camel.component.twitter.data;

/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public enum ConsumerType {
	
	 TIMELINE, SEARCH, USER, TRENDS, USERLIST, DIRECTMESSAGE, STREAMING, UNKNOWN;
	 
	 public static ConsumerType fromUri(String uri) {
		 for (ConsumerType consumerType : ConsumerType.values()) {
			 if (consumerType.name().equalsIgnoreCase(uri)) {
				 return consumerType;
			 }
		 }
		 return ConsumerType.UNKNOWN;
	 }
}
