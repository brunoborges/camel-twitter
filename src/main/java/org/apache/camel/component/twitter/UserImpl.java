package org.apache.camel.component.twitter;

import java.net.URL;

class UserImpl implements User {

	protected UserImpl(twitter4j.User user) {
		this.name = user.getName();
		this.screenname = user.getScreenName();
		this.profileImageUrl = user.getProfileImageURL();
		this.profileUrl = user.getURL();
		this.location = user.getLocation();
		this.description = user.getDescription();
		this.followersCount = user.getFollowersCount();
	}

	private String description;
	private String screenname;
	private URL profileUrl;
	private URL profileImageUrl;
	private String name;
	private String location;
	private int followersCount;

	public String getDescription() {
		return description;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public URL getProfileImageUrl() {
		return profileImageUrl;
	}

	public URL getProfileUrl() {
		return profileUrl;
	}

	public String getScreenname() {
		return screenname;
	}

}
