/**
 * 
 */
package org.apache.camel.component.twitter.consumer;

import java.util.Iterator;

import org.apache.camel.component.twitter.data.Status;

import twitter4j.TwitterException;

public interface Twitter4JConsumer {
	
	public Iterator<Status> requestPollingStatus(long lastStatusUpdateId) throws TwitterException;
	
	public Iterator<Status> requestDirectStatus() throws TwitterException;
}
