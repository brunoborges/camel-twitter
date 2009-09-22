package org.apache.camel.component.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.camel.test.junit4.CamelTestSupport;

public class CamelTwitterTestSupport extends CamelTestSupport {

	private String user;
	private String pass;
	private String follow;

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

		setUser(properties.get("username").toString());
		setPass(properties.get("password").toString());
		setFollow(properties.get("follow").toString());
	}

	protected final String getUser() {
		return user;
	}

	protected final String getPass() {
		return pass;
	}

	protected final String getFollow() {
		return follow;
	}

	private void setUser(String object) {
		this.user = object;
	}

	private void setPass(String object) {
		this.pass = object;
	}

	private void setFollow(String object) {
		this.follow = object;
	}

}
