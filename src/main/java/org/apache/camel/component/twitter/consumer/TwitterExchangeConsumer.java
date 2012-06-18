package org.apache.camel.component.twitter.consumer;

import java.io.Serializable;
import java.util.List;

import org.apache.camel.Exchange;

import twitter4j.TwitterException;

public interface TwitterExchangeConsumer {

	public abstract List<? extends Serializable> exchangeConsume(Exchange in)
			throws TwitterException;

}
