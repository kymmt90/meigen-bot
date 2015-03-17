/*
 * Copyright 2015 Kohei Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rtb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RandomTweetBot {
    private Twitter twitter;
    private List<String> tweets;
    private Random random;
    private String prevTweet;
    private boolean allowsReply;

    public RandomTweetBot(final boolean reply) {
        twitter    = TwitterFactory.getSingleton();
        tweets     = new ArrayList<>();
        random     = new Random(System.currentTimeMillis());
        prevTweet  = null;
        this.allowsReply = reply;
    }

    public String getTweet(final int i) {
        return tweets.get(i);
    }

    public int getNumTweets() {
        return tweets.size();
    }

    public String getPrevTweet() {
        return prevTweet;
    }

    public void setPrevTweet(String tweet) {
        prevTweet = tweet;
    }

    /**
     * Read in JSON tweets file.
     * @param fileName tweets file in JSON form 
     * @return the number of tweets in the file
     * @throws JsonProcessingException
     * @throws IOException
     */
    public int readJsonFile(String fileName) throws JsonProcessingException, IOException {
        ObjectMapper    mapper = new ObjectMapper();
        List<Tweet> pojos  = mapper.readValue(new File(fileName), new TypeReference<List<Tweet>>() {});
        tweets = pojos.stream().map(p -> p.toString()).collect(Collectors.toList());
        return tweets.size();
    }

    /**
     * Get a tweet randomly from tweets list in this class. For avoiding duplicated error, use the tweet that is different from previous one.
     * @return tweet text
     */
    public String nextTweet() {
        String tweet = null;
        do {
            tweet = getTweet(random.nextInt(getNumTweets()));
        } while (equalsPrevTweet(tweet) || !allows(tweet));
        return tweet;
    }
    
    /* package */ boolean equalsPrevTweet(String tweet) {
        return getPrevTweet() != null && getPrevTweet().equals(tweet);
    }
    
    /* package */ boolean allows(String tweet) {
        return allowsReply || !isReply(tweet);
    }
    
    /* package */ boolean isReply(String tweet) {
        return tweet.charAt(0) == '@';
    }

    /**
     * Update twitter status with a random tweet from the list in this class. 
     * @return the updated tweet
     * @throws TwitterException
     */
    public String updateNextTweet() throws TwitterException {
        String tweet = nextTweet();
        setPrevTweet(tweet);
        return updateStatus(tweet);
    }
    
    /* package */ String updateStatus(String status) throws TwitterException {
        if (status == null) throw new IllegalArgumentException();
        return twitter.updateStatus(status).getText();
    }
}
