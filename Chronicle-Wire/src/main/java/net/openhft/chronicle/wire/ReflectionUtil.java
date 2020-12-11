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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ReflectionUtil {

    private ReflectionUtil() {
    }

    /**
     * Creates and returns a new List of all interfaces implemented by
     * the provided {@code oClass} and all its super classes.
     *
     * @param oClass class
     * @return interfaces
     */
    public static List<Class<?>> interfaces(@NotNull final Class<?> oClass) {
        final List<Class<?>> list = new ArrayList<>();
        interfaces(oClass, list);
        return list;
    }

    private static void interfaces(final Class<?> oClass, final List<Class<?>> list) {
        final Class<?> baseClass = oClass.getSuperclass();
        if (baseClass == null)
            // We have reached java.lang.Object
            return;
        list.addAll(Arrays.asList(oClass.getInterfaces()));
        interfaces(baseClass, list);
    }
}