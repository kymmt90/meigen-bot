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
public class ScreenNameTest {
    public static class Normal {
        ScreenName sut;
        
        @Before
        public void setUp() throws Exception {
            sut = new ScreenName("user");
        }
        
        @Test
        public void addTo_appends_screen_name_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addTo(builder);
            assertThat(builder.toString(), is("[user]"));
        }
        
        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("user"));
        }
    }
    
    public static class Error {
        @Test(expected = NullPointerException.class)
        public void constructor_null_throws_NullPointerException() throws Exception {
            new ScreenName(null);
        }
        
        @Test(expected = NullPointerException.class)
        public void addTo_null_throws_NullPointerException() throws Exception {
            ScreenName sut = new ScreenName("user");
            sut.addTo(null);
        }
    }
}
