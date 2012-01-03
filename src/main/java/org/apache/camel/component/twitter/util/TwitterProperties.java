package org.apache.camel.component.twitter.util;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterProperties {
	
	private String consumerKey;
	
	private String consumerSecret;
	
	private String accessToken;
	
	private String accessTokenSecret;

	private String user;

	private String keywords;
	
	private int delay = 60;
	
	private String type;
	
	public void checkComplete() {
		if (consumerKey.isEmpty() || consumerSecret.isEmpty()
				|| accessToken.isEmpty() || accessTokenSecret.isEmpty()) {
			throw new IllegalArgumentException(
					"consumerKey, consumerSecret, accessToken, and accessTokenSecret must be set!");
		}
	}
	
	public Configuration getConfiguration() {
		ConfigurationBuilder confBuilder  = new ConfigurationBuilder();
		confBuilder.setOAuthConsumerKey(consumerKey);
		confBuilder.setOAuthConsumerSecret(consumerSecret);
		confBuilder.setOAuthAccessToken(accessToken);
		confBuilder.setOAuthAccessTokenSecret(accessTokenSecret);
		return confBuilder.build();
	}
	
	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
