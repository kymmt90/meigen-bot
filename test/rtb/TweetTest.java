package rtb;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Enclosed.class)
public class TweetTest {
    public static class InitializeWithStringDate {
        Tweet sut;

        @Before
        public void setUp() throws Exception {
            sut = new Tweet("user", "test", "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000");
        }

        @Test
        public void getScreenName_returns_user() throws Exception {
            assertThat(sut.getScreenName(), is("user"));
        }

        @Test
        public void getText_return_test() throws Exception {
            assertThat(sut.getText(), is("test"));
        }

        @Test
        public void getDate_returns_2015_01_01() throws Exception {
            assertThat(sut.getDate(), is("2015-01-01 01:23"));
        }

        @Test
        public void getFavoritesCount_returns_0() throws Exception {
            assertThat(sut.getFavoritesCount(), is(0));
        }

        @Test
        public void isReply_returns_false() throws Exception {
            assertThat(sut.isReply(), is(false));
        }

        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("test [user] [0 like] [2015-01-01 01:23] https://twitter.com/null/0000000000"));
        }

        @Test
        public void equals_this_returns_true() throws Exception {
            assertThat(sut.equals(sut), is(true));
        }

        @Test
        public void equals_null_returns_false() throws Exception {
            assertThat(sut.equals(null), is(false));
        }

        @Test
        public void equals_String_returns_false() throws Exception {
            assertThat(sut.equals("test"), is(false));
        }

        @Test
        public void equals_returns_true() throws Exception {
            assertThat(sut.equals(new Tweet("user", "test", "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000")), is(true));
        }
    }

    public static class Reply {
        Tweet sut;

        @Before
        public void setUp() throws Exception {
            sut = new Tweet("user", "@user test", "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000");
        }

        @Test
        public void isReply_returns_true() throws Exception {
            assertThat(sut.isReply(), is(true));
        }
    }

    public static class TooLongTextWithoutMetaData {
        Tweet sut;

        // Text length is 130, so no space for meta data
        final String tooLongText = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        // Text length is 111 + Text.shortURLLength, so no space for meta data
        final String tooLongTextWithURL = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa http://example.com";

        @Test
        public void tweet_text_only_includes_text() throws Exception {
            sut = new Tweet("user", tooLongText, "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000");
            assertThat(sut.toString(), is(tooLongText));
        }

        @Test
        public void tweet_text_only_includes_text_and_URL() throws Exception {
            sut = new Tweet("user", tooLongTextWithURL, "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000");
            assertThat(sut.toString(), is(tooLongTextWithURL));
        }
    }

    public static class InitializeWithDate {
        Tweet sut;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        @Before
        public void setUp() throws Exception {
            sut = new Tweet("user", "test", format.parse("2015-01-01 01:23"), 0, "https://twitter.com/null/0000000000");
        }

        @Test
        public void getDate_returns_2015_01_01() throws Exception {
            assertThat(sut.getDate(), is("2015-01-01 01:23"));
        }

        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("test [user] [0 like] [2015-01-01 01:23] https://twitter.com/null/0000000000"));
        }

        @Test
        public void equals_returns_true() throws Exception {
            assertThat(sut.equals(new Tweet("user", "test", format.parse("2015-01-01 01:23"), 0, "https://twitter.com/null/0000000000")), is(true));
        }
    }

    public static class InitializeWithJson {
        Tweet sut;

        @Before
        public void setup() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = "{\"screenName\":\"user\","
                            + "\"text\":\"test\","
                            + "\"date\":\"2015-01-01 01:23\","
                            + "\"favorites_count\":0,"
                            + "\"url\":\"https://twitter.com/null/0000000000\"}";
            sut = mapper.readValue(jsonData, Tweet.class);
        }

        @Test
        public void getScreenName_returns_user() throws Exception {
            assertThat(sut.getScreenName(), is("user"));
        }

        @Test
        public void getText_return_test() throws Exception {
            assertThat(sut.getText(), is("test"));
        }

        @Test
        public void getDate_returns_2015_01_01() throws Exception {
            assertThat(sut.getDate(), is("2015-01-01 01:23"));
        }

        @Test
        public void getFavoritesCount_returns_0() throws Exception {
            assertThat(sut.getFavoritesCount(), is(0));
        }

        @Test
        public void isReply_returns_false() throws Exception {
            assertThat(sut.isReply(), is(false));
        }

        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("test [user] [0 like] [2015-01-01 01:23] https://twitter.com/null/0000000000"));
        }

        @Test
        public void equals_this_returns_true() throws Exception {
            assertThat(sut.equals(sut), is(true));
        }

        @Test
        public void equals_null_returns_false() throws Exception {
            assertThat(sut.equals(null), is(false));
        }

        @Test
        public void equals_String_returns_false() throws Exception {
            assertThat(sut.equals("test"), is(false));
        }

        @Test
        public void equals_returns_true() throws Exception {
            assertThat(sut.equals(new Tweet("user", "test", "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000")), is(true));
        }
    }

    @RunWith(Enclosed.class)
    public static class Error {
        public static class UsingStringDate {
            @Test(expected = NullPointerException.class)
            public void constructor_screenName_null_throws_NullPointerException() throws Exception {
                new Tweet(null, "test", "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000");
            }

            @Test(expected = NullPointerException.class)
            public void constructor_text_null_throws_NullPointerException() throws Exception {
                new Tweet("user", null, "2015-01-01 01:23", 0, "https://twitter.com/null/0000000000");
            }

            @Test(expected = NullPointerException.class)
            public void constructor_date_null_throws_NullPointerException() throws Exception {
                String date = null;
                new Tweet("user", "test", date, 0, "https://twitter.com/null/0000000000");
            }

            @Test(expected = IllegalArgumentException.class)
            public void constructor_favoritesCount_minus_1_throws_NullPointerException() throws Exception {
                new Tweet("user", "test", "2015-01-01 01:23", -1, "https://twitter.com/null/0000000000");
            }
        }

        public static class UsingDate {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            @Test(expected = NullPointerException.class)
            public void constructor_screenName_null_throws_NullPointerException() throws Exception {
                new Tweet(null, "test", format.parse("2015-01-01 01:23"), 0, "https://twitter.com/null/0000000000");
            }

            @Test(expected = NullPointerException.class)
            public void constructor_text_null_throws_NullPointerException() throws Exception {
                new Tweet("user", null, format.parse("2015-01-01 01:23"), 0, "https://twitter.com/null/0000000000");
            }

            @Test(expected = NullPointerException.class)
            public void constructor_date_null_throws_NullPointerException() throws Exception {
                new Tweet("user", "test", (Date)null, 0, "https://twitter.com/null/0000000000");
            }

            @Test(expected = IllegalArgumentException.class)
            public void constructor_favoritesCount_minus_1_throws_NullPointerException() throws Exception {
                new Tweet("user", "test", format.parse("2015-01-01 01:23"), -1, "https://twitter.com/null/0000000000");
            }
        }
    }
}
