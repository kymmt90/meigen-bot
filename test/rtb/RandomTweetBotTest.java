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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(Enclosed.class)
public class RandomTweetBotTest {
    public static class InitializeWithJsonSingle {
        RandomTweetBot sut;
        int retValreadJsonFile;

        @Before
        public void setUp() throws Exception {
            sut = new RandomTweetBot(true);
            retValreadJsonFile = sut.readJsonFile("test/rtb/single.json");
        }

        @Test
        public void readJsonFile_returns_1() throws Exception {
            assertThat(retValreadJsonFile, is(1));
        }

        @Test
        public void nextTweet_returns_text_including_1() throws Exception {
            String actual = sut.nextTweet().toString();
            assertThat(actual, is("1 [user] [0 like] [1990-01-18 01:23]"));
        }

        @Test
        public void getTweet_returns_text_including_1() throws Exception {
            String actual = sut.getTweet(0).toString();
            assertThat(actual, is("1 [user] [0 like] [1990-01-18 01:23]"));
        }

        @Test(expected = IndexOutOfBoundsException.class)
        public void getTweet_1_throws_IndexOutOfBoundsException() throws Exception {
            sut.getTweet(1);
        }

        @Test(expected = IndexOutOfBoundsException.class)
        public void getTweet_2_throws_IndexOutOfBoundsException() throws Exception {
            sut.getTweet(2);
        }
    }

    public static class InitializeWithJsonMultiple {
        RandomTweetBot sut;
        int retValreadJsonFile;

        @Before
        public void setUp() throws Exception {
            sut = new RandomTweetBot(true);
            retValreadJsonFile = sut.readJsonFile("test/rtb/multiple.json");
        }

        @Test
        public void readJsonFile_returns_2() throws Exception {
            assertThat(retValreadJsonFile, is(2));
        }

        @Test
        public void getTweet_0_returns_tweet_including_1() throws Exception {
            assertThat(sut.getTweet(0).toString(), is("1 [user] [0 like] [1990-01-18 01:23]"));
        }

        @Test
        public void getTweet_1_returns_tweet_including_2() throws Exception {
            assertThat(sut.getTweet(1).toString(), is("2 [user] [0 like] [1990-01-18 01:23]"));
        }

        @Test
        public void nextTweet_retrns_tweet_including_1_or_2() throws Exception {
            boolean one = false, two = false;
            for (int i = 0; i < 100; ++i) {
                if (sut.nextTweet().toString().equals("1 [user] [0 like] [1990-01-18 01:23]")) one = true;
                if (sut.nextTweet().toString().equals("2 [user] [0 like] [1990-01-18 01:23]")) two = true;
            }
            assertThat(one, is(true));
            assertThat(two, is(true));
        }

        @Test
        public void nextTweet_returns_tweet_which_is_different_from_prevTweet() throws Exception {
            sut.setPrevTweet(new Tweet("test", "1", "1990-01-18 01:23", 0));
            String actual = sut.nextTweet().toString();
            assertThat(actual, is(not("1")));
        }
    }

    public static class Error {
        RandomTweetBot sut;

        @Before
        public void setUp() throws Exception {
            sut = new RandomTweetBot(true);
        }

        @Test(expected = JsonMappingException.class)
        public void readJsonFile_empty_json_file_throws_JsonMappingException() throws Exception {
            sut.readJsonFile("test/rtb/empty.json");
        }

        @Test(expected = IOException.class)
        public void readJSONFile_not_exit_json_file_throws_IOException() throws Exception {
            sut.readJsonFile("test/rtb/notfound.json");
        }

        @Test(expected = JsonProcessingException.class)
        public void readJSONFile_invalid_json_file_path_throws_JsonProcessingException() throws Exception {
            sut.readJsonFile("test/rtb/invalid.json");
        }
    }

    @RunWith(Enclosed.class)
    public static class InitializeWithJsonReply {
        public static class ReplyConfIsTrue {
            RandomTweetBot sut;

            @Before
            public void setUp() throws Exception {
                sut = new RandomTweetBot(true);
                sut.readJsonFile("test/rtb/reply.json");
            }

            @Test
            public void nextTweet_returns_both_reply_and_not_reply_tweet() throws Exception {
                boolean reply = false, notReply = false;
                for (int i = 0; i < 100; ++i) {
                    if (sut.nextTweet().toString().equals("@test_user test [user] [0 like] [1990-01-18 01:23]")) reply = true;
                    if (sut.nextTweet().toString().equals(".@test_user test [user] [0 like] [1990-01-18 01:23]")) notReply = true;
                }
                assertThat(reply, is(true));
                assertThat(notReply, is(true));
            }

            @Test
            public void allows_reply_tweet_returns_true() throws Exception {
                assertThat(sut.allows(new Tweet("test", "@test_user test", "1990-01-18 01:23", 0)), is(true));
            }
        }

        public static class ReplyConfIsFalse {
            RandomTweetBot sut;

            @Before
            public void setUp() throws Exception {
                sut = new RandomTweetBot(false);
                sut.readJsonFile("test/rtb/reply.json");
            }

            @Test
            public void nextTweet_returns_only_not_reply_tweet() throws Exception {
                boolean reply = false, notReply = false;
                for (int i = 0; i < 100; ++i) {
                    if (sut.nextTweet().toString().equals("@test_user test [user] [0 like] [1990-01-18 01:23]")) reply = true;
                    if (sut.nextTweet().toString().equals(".@test_user test [user] [0 like] [1990-01-18 01:23]")) notReply = true;
                }
                assertThat(reply, is(false));
                assertThat(notReply, is(true));
            }

            @Test
            public void allows_reply_tweet_returns_false() throws Exception {
                assertThat(sut.allows(new Tweet("test", "@test_user test", "1990-01-18 01:23", 0)), is(false));
            }
        }
    }
}
