package rtb;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MetaDataTest {
    public static class InitializeWithStringDate {
        MetaData sut;
        
        @Before
        public void setUp() throws Exception {
            sut = new MetaData("user", "2015-01-01", 0);
        }
        
        @Test
        public void getScreenName_returns_user() throws Exception {
            assertThat(sut.getScreenName(), is("user"));
        }
        
        @Test
        public void getDate_returns_2015_01_01() throws Exception {
            assertThat(sut.getDate(), is("2015-01-01"));
        }
        
        @Test
        public void getFavoritesCount_returns_0() throws Exception {
            assertThat(sut.getFavoritesCount(), is(0));
        }
        
        @Test
        public void addScreenName_appends_screenName_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addScreenNameTo(builder);
            assertThat(builder.toString(), is("[user]"));
        }
        
        @Test
        public void addDate_appends_date_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addDateTo(builder);
            assertThat(builder.toString(), is("[2015-01-01]"));
        }
        
        @Test
        public void addFavoritesCount_appends_favoritesCount_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addFavoritesCountTo(builder);
            assertThat(builder.toString(), is("[0 fav]"));
        }
    }
    
    public static class InitializeWithDate {
        MetaData sut;
        
        @Before
        public void setUp() throws Exception {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            sut = new MetaData("user", format.parse("2015-01-01"), 0);
        }
        
        @Test
        public void getDate_returns_2015_01_01() throws Exception {
            assertThat(sut.getDate(), is("2015-01-01"));
        }
        
        @Test
        public void addDate_appends_date_to_builder() throws Exception {
            StringBuilder builder = new StringBuilder();
            sut.addDateTo(builder);
            assertThat(builder.toString(), is("[2015-01-01]"));
        }
    }
    
    @RunWith(Enclosed.class)
    public static class Error {
        public static class UsingStringDate {
            @Test(expected = NullPointerException.class)
            public void constructor_screetName_null_throws_NullPointerException() throws Exception {
                new MetaData(null, "2015-01-01", 0);
            }

            @Test(expected = NullPointerException.class)
            public void constructor_Date_null_throws_NullPointerException() throws Exception {
                new MetaData("user", (String)null, 0);
            }

            @Test(expected = IllegalArgumentException.class)
            public void constructor_favoritesCount_minus_1_throws_IllegalArgumentException() throws Exception {
                new MetaData("user", "2015-01-01", -1);
            }
            
            @Test(expected = NullPointerException.class)
            public void addScreenNameTo_null_throws_NullPointerException() throws Exception {
                new MetaData("user", "2015-01-01", 0).addScreenNameTo(null);
            }
            
            @Test(expected = NullPointerException.class)
            public void addDateTo_null_throws_NullPointerException() throws Exception {
                new MetaData("user", "2015-01-01", 0).addDateTo(null);
            }
            
            @Test(expected = NullPointerException.class)
            public void addFavoritesCountTo_null_throws_NullPointerException() throws Exception {
                new MetaData("user", "2015-01-01", 0).addFavoritesCountTo(null);
            }
        }
        
        public static class UsingDate {
            DateFormat format;
            
            @Before
            public void setUp() throws Exception {
                format = new SimpleDateFormat("yyyy-MM-dd");
            }
            
            @Test(expected = NullPointerException.class)
            public void constructor_screetName_null_throws_NullPointerException() throws Exception {
                new MetaData(null, format.parse("2015-01-01"), 0);
            }

            @Test(expected = NullPointerException.class)
            public void constructor_Date_null_throws_NullPointerException() throws Exception {
                new MetaData("user", (Date)null, 0);
            }

            @Test(expected = IllegalArgumentException.class)
            public void constructor_favoritesCount_minus_1_throws_IllegalArgumentException() throws Exception {
                new MetaData("user", format.parse("2015-01-01"), -1);
            }
        }
    }
}
