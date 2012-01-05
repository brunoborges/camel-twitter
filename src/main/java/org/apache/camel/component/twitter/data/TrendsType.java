package org.apache.camel.component.twitter.data;


/**
 * @author Brett E. Meyer (3RiverDev.com)
 */
public enum TrendsType {
	DAILY, WEEKLY, UNKNOWN;
	
	public static TrendsType fromUri(String uri) {
		 for (TrendsType trendType : TrendsType.values()) {
			 if (trendType.name().equalsIgnoreCase(uri)) {
				 return trendType;
			 }
		 }
		 return TrendsType.UNKNOWN;
	 }
}
