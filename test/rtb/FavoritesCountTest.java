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

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class FavoritesCountTest {
    @RunWith(Theories.class)
    public static class Normal {
        @DataPoints
        public static int[] favoritesCounts = {0, 1, 10};

        @Theory
        public void addTo_appends_favoritesCount_to_builder(int favoritesCount) throws Exception {
            FavoritesCount sut = new FavoritesCount(favoritesCount);
            StringBuilder builder = new StringBuilder();
            sut.addTo(builder);
            String unit = favoritesCount > 1 ? " likes]" : " like]";
            assertThat(builder.toString(), is("[" + favoritesCount + unit));
        }

        @Theory
        public void toValue_test(int favoritesCount) throws Exception {
            FavoritesCount sut = new FavoritesCount(favoritesCount);
            assertThat(sut.toValue(), is(favoritesCount));
        }
    }

    public static class Error {
        @Test(expected = IllegalArgumentException.class)
        public void constructor_minus_1_throws_IllegalArgumentException() throws Exception {
            new FavoritesCount(-1);
        }

        @Test(expected = NullPointerException.class)
        public void addTo_null_throws_NullPointerException() throws Exception {
            FavoritesCount sut = new FavoritesCount(0);
            sut.addTo(null);
        }
    }
}
