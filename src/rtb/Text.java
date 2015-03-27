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

import java.util.Objects;

class Text {
    private String text;
    
    Text(String text) {
        if (text == null) throw new NullPointerException();
        this.text = text;
    }
    
    void addTo(StringBuilder builder) {
        if (builder == null) throw new NullPointerException();
        builder.append(text);
    }
    
    boolean isReply() {
        return text.charAt(0) == '@';
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        Text other = (Text)object;
        return Objects.equals(text, other);
    }

    @Override
    public String toString() {
        return text;
    }
}
