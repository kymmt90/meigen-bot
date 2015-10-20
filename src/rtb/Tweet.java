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
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {
    private Text text;
    private MetaData meta;

    @JsonCreator
    public Tweet(@JsonProperty("screenName") String screenName,
                 @JsonProperty("text") String text,
                 @JsonProperty("date") String date,
                 @JsonProperty("favorites_count") int favoritesCount) {
        if (screenName == null || text == null || date == null) throw new NullPointerException();
        if (favoritesCount < 0) throw new IllegalArgumentException();
        this.text = new Text(text);
        this.meta = new MetaData(screenName, date, favoritesCount);
    }

    public Tweet(String screenName, String text, Date date, int favoritesCount) {
        if (screenName == null || text == null || date == null) throw new NullPointerException();
        if (favoritesCount < 0) throw new IllegalArgumentException();
        this.text = new Text(text);
        this.meta = new MetaData(screenName, date, favoritesCount);
    }

    public String getScreenName() {
        return meta.getScreenName();
    }

    public String getText() {
        return text.toString();
    }

    public String getDate() {
        return meta.getDate();
    }

    @JsonProperty("favorites_count")
    public int getFavoritesCount() {
        return meta.getFavoritesCount();
    }

    @JsonIgnore
    public boolean isReply() {
        return text.isReply();
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;
        Tweet other = (Tweet)object;
        return Objects.equals(text, other.text);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        meta.addScreenNameTo(builder);
        builder.append(" ");
        meta.addFavoritesCountTo(builder);
        builder.append(" ");
        meta.addDateTo(builder);

        final int metaLength = builder.toString().length();
        return text.length() + metaLength > 140
             ? text.toString()
             : text.toString() + builder.toString();
    }
}
