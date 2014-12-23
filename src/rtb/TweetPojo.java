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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetPojo {
    private String text;
    private String date;
    private int favoritesCount;
    
    @JsonCreator
    public TweetPojo(@JsonProperty("text") String text,
                     @JsonProperty("date") String date,
                     @JsonProperty("favorites_count") int favoritesCount) {
        this.text = text;
        this.date = date;
        this.favoritesCount = favoritesCount;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    @JsonProperty("favorites_count")
    public int getFavoritesCount() {
        return favoritesCount;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();    
        builder.append(text)
            .append(" [").append(favoritesCount).append(" fav]")
            .append(" [").append(date).append("]");     
        return builder.toString();
    }
}
