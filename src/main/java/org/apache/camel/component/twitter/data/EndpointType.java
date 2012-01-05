package org.apache.camel.component.twitter.data;

/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public enum EndpointType {
	
	 POLLING, DIRECT;
	 
	 public static EndpointType fromUri(String uri) {
		 for (EndpointType endpointType : EndpointType.values()) {
			 if (endpointType.name().equalsIgnoreCase(uri)) {
				 return endpointType;
			 }
		 }
		 return EndpointType.DIRECT;
	 }
}
