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
package org.apache.camel.component.twitter;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * @version $Revision: 786399 $
 */
public class UriConfigurationTest extends Assert {
    protected CamelContext context = new DefaultCamelContext();

    @Test
    public void testFollowConfiguration() throws Exception {
        Endpoint endpoint = context.getEndpoint("twitter:tweets?user=lhein&pass=myPass&type=FRIENDS&follow=davsclaus");
        assertTrue("Endpoint not a TwitterEndpoint: " + endpoint, endpoint instanceof TwitterEndpoint);
        TwitterEndpoint twitterEndpoint = (TwitterEndpoint) endpoint;

        Assert.assertEquals(TimelineType.FRIENDS, twitterEndpoint.getType());
        Assert.assertEquals("lhein", twitterEndpoint.getUser());
        Assert.assertEquals("myPass", twitterEndpoint.getPass());
        Assert.assertEquals("davsclaus", twitterEndpoint.getFollow());
    }

    @Test
    public void testWithoutFollowConfiguration() throws Exception {
        Endpoint endpoint = context.getEndpoint("twitter:tweets?user=lhein&pass=myPass&type=ALL");
        assertTrue("Endpoint not a TwitterEndpoint: " + endpoint, endpoint instanceof TwitterEndpoint);
        TwitterEndpoint twitterEndpoint = (TwitterEndpoint) endpoint;

        Assert.assertEquals(TimelineType.ALL, twitterEndpoint.getType());
        Assert.assertEquals("lhein", twitterEndpoint.getUser());
        Assert.assertEquals("myPass", twitterEndpoint.getPass());
    }

    @Test
    public void testAnonymousWithoutFollowConfiguration() throws Exception {
        Endpoint endpoint = context.getEndpoint("twitter:tweets?type=ALL");
        assertTrue("Endpoint not a TwitterEndpoint: " + endpoint, endpoint instanceof TwitterEndpoint);
        TwitterEndpoint twitterEndpoint = (TwitterEndpoint) endpoint;

        Assert.assertEquals(TimelineType.ALL, twitterEndpoint.getType());
        Assert.assertNull(twitterEndpoint.getUser());
        Assert.assertNull(twitterEndpoint.getPass());
        Assert.assertNull(twitterEndpoint.getFollow());
    }
}
