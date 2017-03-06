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
public abstract class DefaultMappedProperty<T> implements MappedProperty<T> {

    private final String propertyName;
    private final TypeToken<T> type;
    private final String columnName;
    private final boolean partitionKey;
    private final boolean clusteringColumn;
    private final boolean computed;
    private final int position;
    private final TypeCodec<T> customCodec;

    public DefaultMappedProperty(
            String propertyName, String columnName,
            TypeToken<T> type, TypeCodec<T> customCodec,
            boolean partitionKey, boolean clusteringColumn, boolean computed, int position
    ) {
        this.propertyName = propertyName;
        this.type = type;
        this.columnName = columnName;
        this.partitionKey = partitionKey;
        this.clusteringColumn = clusteringColumn;
        this.computed = computed;
        this.position = position;
        this.customCodec = customCodec;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public TypeToken<T> getPropertyType() {
        return type;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public TypeCodec<T> getCustomCodec() {
        return customCodec;
    }

    @Override
    public boolean isComputed() {
        return computed;
    }

    @Override
    public boolean isPartitionKey() {
        return partitionKey;
    }

    @Override
    public boolean isClusteringColumn() {
        return clusteringColumn;
    }

    @Override
    public String toString() {
        return getPropertyName();
    }

}
