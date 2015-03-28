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
