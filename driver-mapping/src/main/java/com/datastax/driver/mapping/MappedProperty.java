/*
 *      Copyright (C) 2012-2015 DataStax Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.datastax.driver.mapping;

import com.datastax.driver.core.TypeCodec;
import com.google.common.reflect.TypeToken;

/**
 *
 */
public interface MappedProperty<T> {

    String getPropertyName();

    String getColumnName();

    TypeToken<T> getPropertyType();

    TypeCodec<T> getCustomCodec();

    boolean isComputed();

    boolean isPartitionKey();

    boolean isClusteringColumn();

    int getPosition();

    /**
     * Reads the value of a property defined on {@code entity} and named {@code propertyName},
     * possibly using the given {@code field} and/or the given {@code getter} method.
     *
     * @param entity The instance to read the property from; never {@code null}.
     * @return The property value.
     * @throws IllegalArgumentException if the property cannot be read.
     */
    T getValue(Object entity);

    /**
     * Writes the value {@code value} to the property defined on {@code entity} and named {@code propertyName},
     * possibly using the given {@code field} and/or the given {@code setter} method.
     *
     * @param entity The instance to read the property from; never {@code null}.
     * @throws IllegalArgumentException if the property cannot be written.
     */
    void setValue(Object entity, T value);

}
