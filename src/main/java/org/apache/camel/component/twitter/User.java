package org.apache.camel.component.twitter;

import java.net.URL;

public interface User {

	public String getName();

	public String getScreenname();

	public String getLocation();

	public String getDescription();

	public int getFollowersCount();

	public URL getProfileImageUrl();

	public URL getProfileUrl();

}
