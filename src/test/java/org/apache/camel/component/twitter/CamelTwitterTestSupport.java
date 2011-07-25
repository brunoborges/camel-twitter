package org.apache.camel.component.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.camel.test.junit4.CamelTestSupport;

public class CamelTwitterTestSupport extends CamelTestSupport {

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;

	public CamelTwitterTestSupport() {
		URL url = getClass().getResource("/test-options.properties");

		InputStream inStream;
		try {
			inStream = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalAccessError(
					"test-options.properties could not be found");
		}

		Properties properties = new Properties();
		try {
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalAccessError(
					"test-options.properties could not be found");
		}

		setAccessToken(properties.get("access.token").toString());
		setAccessTokenSecret(properties.get("access.token.secret").toString());
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
	
	public String getUriTokens() {
		return "consumerKey=" + consumerKey
				+ "&consumerSecret=" + consumerSecret
				+ "&accessToken=" + accessToken
				+ "&accessTokenSecret=" + accessTokenSecret;
	}
}
