package org.apache.camel.component.twitter;

import java.util.List;

import junit.framework.TestCase;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter4JTest extends TestCase {

	private Twitter t;

	@Override
	protected void setUp() throws Exception {
		Configuration config = new ConfigurationBuilder().setUser("cameltweet").setPassword("ctapache!").build();
		t = new TwitterFactory().getInstance(AuthorizationFactory.getInstance(config));
	}

	@Override
	protected void tearDown() throws Exception {
		t = null;
	}

	public void testFriendsTimeline() throws TwitterException {
		List<Status> l = t.getFriendsTimeline(new Paging());

		assertNotNull(l);

		assertFalse(l.isEmpty());
	}

	public void testPublicTimeline() throws TwitterException {
		List<Status> l = t.getPublicTimeline();

		assertNotNull(l);

		assertFalse(l.isEmpty());
	}

	public void testUserTimeline() throws TwitterException {
		List<Status> l = t.getUserTimeline("brunoborges");

		assertNotNull(l);

		assertFalse(l.isEmpty());
	}

}
