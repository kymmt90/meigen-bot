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

class MetaData {
    private ScreenName screenName; 
    private TweetDate date;
    private FavoritesCount favoritesCount;
    private String url;
    
    MetaData(String screenName, String date, int favoritesCount, String url) {
        if (screenName == null || date == null || url == null) throw new NullPointerException();
        if (favoritesCount < 0) throw new IllegalArgumentException();

        this.screenName = new ScreenName(screenName);
        this.date = new TweetDate(date);
        this.favoritesCount = new FavoritesCount(favoritesCount);
        this.url = url;
    }
    
    MetaData(String screenName, Date date, int favoritesCount, String url) {
        if (screenName == null || date == null) throw new NullPointerException();
        if (favoritesCount < 0) throw new IllegalArgumentException();

        this.screenName = new ScreenName(screenName);
        this.date = new TweetDate(date);
        this.favoritesCount = new FavoritesCount(favoritesCount);
        this.url = url;
    }
    
    String getScreenName() {
        return screenName.toString();
    }
    
    String getDate() {
        return date.toString();
    }
    
    int getFavoritesCount() {
        return favoritesCount.toValue();
    }
    
    String getUrl() {
        return url;
    }

    void addScreenNameTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        screenName.addTo(builder);
    }
    
    void addDateTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        date.addTo(builder);
    }
    
    void addFavoritesCountTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        favoritesCount.addTo(builder);
    }

    void addURLTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append(url);
    }
}
