/*
 * Copyright 2016-2020 chronicle.software
 *
 * https://chronicle.software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openhft.chronicle.wire;

// TODO add a pattern for validation
public interface LongConverter {

    /**
     * Parses the provided {@link CharSequence} and returns the parsed results as a
     * {@code long} primitive.
     *
     * @return the parsed {@code text} as an {@code long} primitive.
     */
    long parse(CharSequence text);

    /**
     * Appends the provided {@code value} to the provided {@code text}.
     */
    void append(StringBuilder text, long value);

    default String asString(long value) {
        StringBuilder sb = new StringBuilder();
        append(sb, value);
        return sb.toString();
    }

    default CharSequence asText(long value) {
        StringBuilder sb = new StringBuilder();
        append(sb, value);
        return sb;
    }
}