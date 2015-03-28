/*
 * Copyright 2015 Kohei Yamamoto
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class BotProperties {
    /*package*/ PropertiesLoader loader = new PropertiesLoader();
    private PropertiesConfiguration properties;
    
    public BotProperties() { 
        this.properties = new PropertiesConfiguration();
    }
    
    public void load(String fileName) throws IOException, ConfigurationException {
        if (fileName == null) throw new NullPointerException();
        InputStream stream = loader.getInputStream(fileName);
        if (stream == null) throw new NullPointerException();
        properties.load(new InputStreamReader(stream));
        if (!isValid()) throw new IllegalStateException();
    }
    
    private boolean isValid() {
        final int namesListSize = properties.getList("screenName").size();
        final int CountListSize = properties.getList("favCount").size();
        return namesListSize == CountListSize;
    }
    
    public List<String> screenName() {
        return Arrays.asList(properties.getStringArray("screenName"));
    }
    
    public String filePath() {
        return properties.getString("filePath");
    }
    
    public List<Integer> favCount() {
        List<Object> favCounts = properties.getList("favCount");
        return favCounts.stream().map(Object::toString)
                                 .map(Integer::valueOf)
                                 .collect(Collectors.toList());
    }
    
    public final long intervalMinutes() {
        return properties.getLong("intervalMinutes");
    }
    
    public boolean reply() {
        return properties.getBoolean("reply");
    }
}
