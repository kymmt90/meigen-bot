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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class TweetDate {
    private static DateTimeFormatter formatter
        = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private LocalDateTime date;

    TweetDate(String date) {
        if (date == null) throw new NullPointerException();
        this.date = LocalDateTime.parse(date, formatter);
    }

    TweetDate(Date date) {
        if (date == null) throw new NullPointerException();
        Instant instant = date.toInstant();
        this.date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    void addTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append("[")
               .append(this.toString())
               .append("]");
    }

    @Override
    public String toString() {
        return date.format(formatter);
    }
}
