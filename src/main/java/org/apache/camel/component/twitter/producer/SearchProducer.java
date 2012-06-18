/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.twitter.producer;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.component.twitter.TwitterEndpoint;
import org.apache.camel.component.twitter.TwitterConstants;
import org.apache.camel.impl.DefaultProducer;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;

public class SearchProducer extends DefaultProducer {

    private long lastId;
    private TwitterEndpoint twitterEndpoint;

    public SearchProducer(TwitterEndpoint te) {
        super(te);
        this.twitterEndpoint = te;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String keywords = exchange.getIn().getHeader(TwitterConstants.TWITTER_SEARCH_QUERY, String.class);

        Query query = new Query(keywords);
        if (lastId != 0) {
            query.setSinceId(lastId);
        }

        Twitter twitter = twitterEndpoint.getTwitter();
        QueryResult results = twitter.search(query);
        List<Tweet> list = results.getTweets();

        for (Tweet t : list) {
            long newId = t.getId();
            if (newId > lastId) {
                lastId = newId;
            }
        }

        exchange.getIn().setBody(list);
    }

}
