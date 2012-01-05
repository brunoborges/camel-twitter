package org.apache.camel.component.twitter.data;

public class GeoLocation {
	
	private double latitude;
	
	private double longitude;
	
	public GeoLocation(twitter4j.GeoLocation geoLocation) {
		this.latitude = geoLocation.getLatitude();
		this.longitude = geoLocation.getLongitude();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
