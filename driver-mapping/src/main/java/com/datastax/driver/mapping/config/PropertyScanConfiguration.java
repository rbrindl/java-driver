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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration settings that determine how the mapper
 * scans Java classes looking for mapped properties.
 */
public class PropertyScanConfiguration {

    private final Set<String> excludedProperties;

    private final PropertyAccessStrategy propertyAccessStrategy;

    private final PropertyMappingStrategy propertyMappingStrategy;

    private final HierarchyScanStrategy hierarchyScanStrategy;

    public PropertyScanConfiguration() {
        this.excludedProperties = new HashSet<String>();
        this.propertyAccessStrategy = PropertyAccessStrategy.BOTH;
        this.propertyMappingStrategy = PropertyMappingStrategy.OPT_OUT;
        this.hierarchyScanStrategy = new HierarchyScanStrategy();
    }

    private PropertyScanConfiguration(Set<String> excludedProperties, PropertyAccessStrategy propertyAccessStrategy, PropertyMappingStrategy propertyMappingStrategy, HierarchyScanStrategy hierarchyScanStrategy) {
        this.excludedProperties = excludedProperties;
        this.propertyAccessStrategy = propertyAccessStrategy;
        this.propertyMappingStrategy = propertyMappingStrategy;
        this.hierarchyScanStrategy = hierarchyScanStrategy;
    }

    /**
     * Returns a collection of properties to exclude from mapping
     *
     * @return a collection of properties to exclude from mapping
     */
    public Set<String> getExcludedProperties() {
        return excludedProperties;
    }

    /**
     * Returns the {@link PropertyAccessStrategy}.
     *
     * @return the {@link PropertyAccessStrategy}.
     */
    public PropertyAccessStrategy getPropertyAccessStrategy() {
        return propertyAccessStrategy;
    }

    /**
     * Returns the {@link PropertyMappingStrategy}.
     *
     * @return the {@link PropertyMappingStrategy}.
     */
    public PropertyMappingStrategy getPropertyMappingStrategy() {
        return propertyMappingStrategy;
    }

    /**
     * Returns the {@link HierarchyScanStrategy}.
     *
     * @return the {@link HierarchyScanStrategy}.
     */
    public HierarchyScanStrategy getHierarchyScanStrategy() {
        return hierarchyScanStrategy;
    }

}
