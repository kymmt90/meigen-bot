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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {
    private Text text;
    private TweetDate date;
    private FavoritesCount favoritesCount;
    
    @JsonCreator
    public Tweet(@JsonProperty("text") String text,
                 @JsonProperty("date") String date,
                 @JsonProperty("favorites_count") int favoritesCount) {
        this.text = new Text(text);
        this.date = new TweetDate(date);
        this.favoritesCount = new FavoritesCount(favoritesCount);
    }
    
    public Tweet(String text, Date date, int favoritesCount) {
        this.text = new Text(text);
        this.date = new TweetDate(date);
        this.favoritesCount = new FavoritesCount(favoritesCount);
    }

    public String getText() {
        return text.toString();
    }

    public String getDate() {
        return date.toString();
    }

    @JsonProperty("favorites_count")
    public int getFavoritesCount() {
        return favoritesCount.toValue();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        text.addTo(builder);
        builder.append(" ");
        favoritesCount.addTo(builder);
        builder.append(" ");
        date.addTo(builder);       
        return builder.toString();
    }
}
