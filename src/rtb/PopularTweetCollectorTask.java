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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import twitter4j.Status;
import twitter4j.TwitterException;

public class PopularTweetCollectorTask extends TimerTask {
    private String fileName;
    private List<String> screenNames;
    private List<Integer> favThresholds;
    private PopularTweetCollector collector;

    public PopularTweetCollectorTask(String fileName, List<String> screenNames,
                                     List<Integer> favThresholds, PopularTweetCollector collector) {
        this.fileName = fileName;
        this.screenNames = screenNames;
        this.favThresholds = favThresholds;
        this.collector = collector;
    }

    @Override
    public void run() {
        try {
            List<Status> tweets = new ArrayList<>();
            for (int i = 0; i < screenNames.size(); ++i) {
                tweets.addAll(collector.collectPopularTweets(screenNames.get(i), favThresholds.get(i), fileName));
            }
            collector.writeAsJson(tweets, fileName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        } catch (TwitterException te) {
            System.out.println(te.getMessage());
        }
    }
}
