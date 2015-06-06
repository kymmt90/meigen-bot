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

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import twitter4j.Status;

@RunWith(Enclosed.class)
public class PopularTweetCollectorTest {
    public static class Normal {
        PopularTweetCollector sut;

        @Before
        public void setUp() throws Exception {
            sut = new PopularTweetCollector();
        }

        @Test
        public void writeAsJson_statuses_emptyList_invokes_nothing() throws Exception {
            sut.writeAsJson(Collections.emptyList(), "filename");
        }
    }

    public static class Error {
        @Test(expected = NullPointerException.class)
        public void writeAsJson_statuses_null_throws_NullPointerException() throws Exception {
            new PopularTweetCollector().writeAsJson(null, "filename");
        }
        
        @Test(expected = NullPointerException.class)
        public void writeAsJson_filename_null_throws_NullPointerException() throws Exception {
            new PopularTweetCollector().writeAsJson(new ArrayList<Status>(), null);
        }
        
        @Test(expected = IllegalArgumentException.class)
        public void if_collectPopularTweets_favthreshold_is_less_than_0_then_throws_IllegalArgumentException() throws Exception {
            new PopularTweetCollector().collectPopularTweets("", -1, "");
        }
    }
}
