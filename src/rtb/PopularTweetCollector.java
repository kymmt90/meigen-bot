/*
 * Copyright 2014 Kohei Yamamoto
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
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class PopularTweetCollector {
    private Twitter twitter;
    
    public PopularTweetCollector() {
        twitter  = TwitterFactory.getSingleton();
    }
    
    /**
     * Get the date of latest status of the user.
     * @param userScreenName the screen name of user
     * @return the date of latest status of user who is specified in parameter
     * @throws TwitterException
     */
    public Date getLatestTweetDate(String userScreenName) throws TwitterException {
        if (userScreenName == null) throw new NullPointerException();
        return twitter.getUserTimeline(userScreenName, new Paging(1, 1)).get(0).getCreatedAt();
    }
    
    /**
     * Write out the list of popular tweets in JSON form. Each tweet in the list has the number of favs which is greater than favThreshold.
     * @param userScreenName the screen name of user
     * @param favThreshold the threshold of the number of favs
     * @param fileName JSON file name
     * @return the list of statuses
     * @throws TwitterException
     * @throws JsonParseException
     * @throws IOException  
     */
    public List<Status> collectPopularTweets(String userScreenName, final int favThreshold, String fileName)
            throws TwitterException, JsonParseException, IOException {
        if (userScreenName == null || fileName == null) throw new NullPointerException();
        if (favThreshold < 0) throw new IllegalArgumentException();
        
        List<Status> tweets = new ArrayList<>();
        for (int page = 1; ; ++page) {
            List<Status> statuses = twitter.getUserTimeline(userScreenName, new Paging(page, 200));
            if (statuses.size() == 0) return tweets;
            tweets.addAll(statuses.stream().filter(s -> s.getFavoriteCount() >= favThreshold)
                                           .collect(Collectors.toList()));
        }
    }
        
    /**
     * Write out the list of tweets to JSON form. 
     * @param statuses the list of statuses
     * @param fileName JSON file name
     * @throws JsonParseException
     * @throws IOException
     */
    public void writeAsJson(List<? extends Status> statuses, String fileName) throws JsonParseException, IOException {
        if (statuses == null || fileName == null) throw new NullPointerException();
        if (statuses.isEmpty()) return;

        // Convert Status to TweetPojo
        List<TweetPojo> pojos = new ArrayList<>(statuses.size());
        for (Status s : statuses) {
            // Get tweet text
            String text = s.getText().replaceAll("[\n\t]", " ");
            
            // Get formatted date text
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(s.getCreatedAt());
            
            pojos.add(new TweetPojo(text, date, s.getFavoriteCount()));
        }
        
        // Write out TweetPojo as JSON
        FileWriter   writer = new FileWriter(new File(fileName));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, pojos);
    }
    
    /**
     * Get the amount seconds until next rate limiting period for /statuses/user_timeline.
     * @return the amount of seconds until next rate limiting period
     * @throws TwitterException
     */
    public int getSecondsUntilRateLimitReset() throws TwitterException {
        RateLimitStatus status = twitter.getRateLimitStatus().get("/statuses/user_timeline");
        return status.getSecondsUntilReset();
    }
}
