package org.apache.camel.component.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.camel.test.junit4.CamelTestSupport;

public class CamelTwitterTestSupport extends CamelTestSupport {

	protected String consumerKey;
	protected String consumerSecret;
	protected String accessToken;
	protected String accessTokenSecret;

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

		consumerKey = properties.get("consumer.key").toString();
		consumerSecret = properties.get("consumer.secret").toString();
		accessToken = properties.get("access.token").toString();
		accessTokenSecret = properties.get("access.token.secret").toString();
	}
	
	protected String getUriTokens() {
		return "consumerKey=" + consumerKey
				+ "&consumerSecret=" + consumerSecret
				+ "&accessToken=" + accessToken
				+ "&accessTokenSecret=" + accessTokenSecret;
	}
}
