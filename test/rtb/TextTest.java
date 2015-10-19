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

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TextTest {
    public static class Normal {
        Text sut;

        @Before
        public void setUp() throws Exception {
            sut = new Text("test");
        }

        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("test"));
        }

        @Test
        public void addTo_appends_text_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addTo(builder);
            assertThat(builder.toString(), is("test"));
        }

        @Test
        public void isReply_returns_false() {
            assertThat(sut.isReply(), is(false));
        }

        @Test
        public void equals_this_retuns_true() {
            assertThat(sut.equals(sut), is(true));
        }

        @Test
        public void equals_null_retuns_false() {
            assertThat(sut.equals(null), is(false));
        }

        @Test
        public void equals_test_returns_true() {
            assertThat(sut.equals(new Text("test")), is(true));
        }

        @Test
        public void equals_String_returns_false() {
            assertThat(sut.equals("@user test"), is(false));
        }

        @Test
        public void hashCode_test() {
            assertThat(sut.hashCode(), is(new Text("test").hashCode()));
        }

        @Test
        public void length_returns_4() {
            assertThat(sut.length(), is(4));
        }
    }

    public static class Reply {
        Text sut;

        @Before
        public void setUp() throws Exception {
            sut = new Text("@user test");
        }

        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("@user test"));
        }

        @Test
        public void addTo_appends_text_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addTo(builder);
            assertThat(builder.toString(), is("@user test"));
        }

        @Test
        public void isReply_returns_true() {
            assertThat(sut.isReply(), is(true));
        }

        @Test
        public void equals_test_returns_true() {
            assertThat(sut.equals(new Text("@user test")), is(true));
        }

        @Test
        public void equals_String_returns_false() {
            assertThat(sut.equals("@user test"), is(false));
        }

        @Test
        public void hashCode_test() {
            assertThat(sut.hashCode(), is(new Text("@user test").hashCode()));
        }

        @Test
        public void length_returns_9() {
            assertThat(sut.length(), is(10));
        }
    }

    public static class IncludingURL {
        @Test
        public void length_returns_5_plus_shortURLLength() {
            Text sut = new Text("test http://google.com");
            assertThat(sut.length(), is(5 + Text.shortURLLength));
        }

        @Test
        public void length_returns_5_plus_shortURLLength_2() {
            Text sut = new Text("test google.com");
            assertThat(sut.length(), is(5 + Text.shortURLLength));
        }

        @Test
        public void length_returns_5_plus_shortURLLengthHttps_2() {
            Text sut = new Text("test https://google.com");
            assertThat(sut.length(), is(5 + Text.shortURLLengthHttps));
        }

        @Test
        public void length_returns_6_plus_2_mult_shortURLLength() {
            Text sut = new Text("http://google.com test http://yahoo.com/index.html");
            assertThat(sut.length(), is(6 + 2 * Text.shortURLLength));
        }
    }

    public static class Error {
        @Test(expected = NullPointerException.class)
        public void constructor_null_throws_NullPointerException() throws Exception {
            new Text(null);
        }

        @Test(expected = NullPointerException.class)
        public void addTo_null_throws_NullPointerException() throws Exception {
            Text sut = new Text("test");
            sut.addTo(null);
        }
    }
}
