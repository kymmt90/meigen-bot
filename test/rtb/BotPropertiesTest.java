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

import org.junit.Test;

public class BotPropertiesTest {
    @Test
    public void test() throws Exception {
        InputStream stream = new ByteArrayInputStream("screenName=test_user\nfilePath=test/config.properties\nfavCount=3\nintervalMinutes=720".getBytes());
        PropertiesLoader mockLoader = mock(PropertiesLoader.class);
        when(mockLoader.getInputStream("test")).thenReturn(stream);
        BotProperties sut = new BotProperties();
        sut.loader = mockLoader;
        sut.load("test");
        assertThat(sut.screenName(), is("test_user"));
        assertThat(sut.filePath(), is("test/config.properties"));
        assertThat(sut.favCount(), is(3));
        assertThat(sut.intervalMinutes(), is(720L));
    }
}
