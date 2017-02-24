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
package com.datastax.driver.mapping.config;

/**
 * A strategy to determine which properties of a Java class are mapped to Cassandra columns or UDT fields.
 */
public enum PropertyMappingStrategy {

    /**
     * Unless a property is explicitly {@code transient} or annotated with {@code Transient}, it will be mapped.
     */
    OPT_OUT,

    /**
     * A property will only be mapped if it is explicitly annotated with either {@code PartitionKey},
     * {@code ClusteringColumn}, {@code Column}, {@code com.datastax.driver.mapping.annotations.Field} or
     * {@code Computed}.
     */
    OPT_IN

}
