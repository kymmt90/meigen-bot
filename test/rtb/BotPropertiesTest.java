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
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class BotPropertiesTest {
    BotProperties sut;
    
    @Before
    public void setUp() throws Exception {
        InputStream stream
            = new ByteArrayInputStream(("screenName=user1,user2,user3\n"
                                      + "filePath=test/config.properties\n"
                                      + "favCount=3,2,1\n"
                                      + "intervalMinutes=720\n"
                                      + "reply=true")
                                      .getBytes());
        PropertiesLoader mockLoader = mock(PropertiesLoader.class);
        when(mockLoader.getInputStream("test")).thenReturn(stream);
        sut = new BotProperties();
        sut.loader = mockLoader;
        sut.load("test");
    }
    
    @Test
    public void screenName_contains_user1_user2_user3() throws Exception {
        assertThat(sut.screenName().get(0), is("user1"));
        assertThat(sut.screenName().get(1), is("user2"));
        assertThat(sut.screenName().get(2), is("user3"));
    }
    
    @Test
    public void filePath_is_test_slash_config_dot_properties() throws Exception {
        assertThat(sut.filePath(), is("test/config.properties"));
    }
    
    @Test
    public void favCount_contains_3_2_1() throws Exception {
        assertThat(sut.favCount().get(0), is(3));
        assertThat(sut.favCount().get(1), is(2));
        assertThat(sut.favCount().get(2), is(1));
    }
    
    @Test
    public void intervalMinutes_is_720() throws Exception {
        assertThat(sut.intervalMinutes(), is(720L));
    }
    
    @Test
    public void reply_is_true() throws Exception {
        assertThat(sut.reply(), is(true));
    }
}
