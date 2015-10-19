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

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

class Text {
    private static String URLRegex
        = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    public static int charactersReservedPerMedia;
    public static int shortURLLength;
    public static int shortURLLengthHttps;

    static {
        TwitterAPIConfiguration config = null;
        try {
            config = TwitterFactory.getSingleton().getAPIConfiguration();
        } catch (TwitterException te) {
            te.printStackTrace();
        }
        charactersReservedPerMedia = config.getCharactersReservedPerMedia();
        shortURLLength             = config.getShortURLLength();
        shortURLLengthHttps        = config.getShortURLLengthHttps();
    }

    private String text;
    private int length;

    Text(String text) {
        if (text == null) throw new NullPointerException();
        this.text = text;

        final int numURL = countURL(text);
        this.length = numURL * shortURLLength + removeURL(text).length();
    }

    int countURL(String text) {
        Matcher matcher = Pattern.compile(URLRegex).matcher(text);
        int numURL = 0;
        while (matcher.find()) ++numURL;
        return numURL;
    }

    String removeURL(String text) {
        Matcher matcher = Pattern.compile(URLRegex).matcher(text);
        return matcher.replaceAll("");
    }

    void addTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append(text);
    }

    boolean isReply() {
        return text.charAt(0) == '@';
    }

    int length() {
        return length;
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
        Text other = (Text)object;
        return Objects.equals(text.toString(), other.toString());
    }

    @Override
    public String toString() {
        return text;
    }
}
