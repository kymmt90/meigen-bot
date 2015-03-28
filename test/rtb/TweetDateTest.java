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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TweetDateTest {
    public static class InitializeWithString {
        TweetDate sut;
        
        @Before
        public void setUp() throws Exception {
            sut = new TweetDate("2015-01-01");
        }
        
        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("2015-01-01"));
        }
        
        @Test
        public void addTo_appends_date_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addTo(builder);
            assertThat(builder.toString(), is("[2015-01-01]"));
        }
    }
    
    public static class InitializeWithDate {
        TweetDate sut;
        
        @Before
        public void setUp() throws Exception {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            sut = new TweetDate(format.parse("2015-01-01"));
        }
        
        @Test
        public void toString_test() throws Exception {
            assertThat(sut.toString(), is("2015-01-01"));
        }
        
        @Test
        public void addTo_appends_date_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addTo(builder);
            assertThat(builder.toString(), is("[2015-01-01]"));
        }
    }
    
    public static class Error {
        @Test(expected = NullPointerException.class)
        public void constructor_String_null_throws_NullPointerException() throws Exception {
            String str = null;
            new TweetDate(str);
        }
        
        @Test(expected = NullPointerException.class)
        public void constructor_Date_null_throws_NullPointerException() throws Exception {
            Date date = null;
            new TweetDate(date);
        }
    }
}
