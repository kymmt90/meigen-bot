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
    
    MetaData(String screenName, String date, int favoritesCount) {
        this.screenName = new ScreenName(screenName);
        this.date = new TweetDate(date);
        this.favoritesCount = new FavoritesCount(favoritesCount);
    }
    
    MetaData(String screenName, Date date, int favoritesCount) {
        this.screenName = new ScreenName(screenName);
        this.date = new TweetDate(date);
        this.favoritesCount = new FavoritesCount(favoritesCount);
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
    
    void addScreenNameTo(StringBuilder builder) {
        screenName.addTo(builder);
    }
    
    void addDateTo(StringBuilder builder) {
        date.addTo(builder);
    }
    
    void addFavoritesCountTo(StringBuilder builder) {
        favoritesCount.addTo(builder);
    }
}
