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
 * Configuration settings that determine how the mapper
 * scans Java classes looking for mapped properties.
 */
public class PropertyScanConfiguration {

    /**
     * Returns a new {@link PropertyScanConfiguration.Builder} instance.
     *
     * @return a new {@link PropertyScanConfiguration.Builder} instance.
     */
    public static PropertyScanConfiguration.Builder builder() {
        return new PropertyScanConfiguration.Builder();
    }

    /**
     * Builder for {@link PropertyScanConfiguration} instances.
     */
    public static class Builder {

        private PropertyAccessStrategy propertyAccessStrategy = PropertyAccessStrategy.BOTH;

        private PropertyTransienceStrategy propertyTransienceStrategy = DefaultPropertyTransienceStrategy.builder().build();

        private HierarchyScanStrategy hierarchyScanStrategy = HierarchyScanStrategy.builder().build();

        /**
         * Sets the {@link PropertyAccessStrategy property access strategy} to use.
         * The default is {@link PropertyAccessStrategy#BOTH}.
         *
         * @param propertyAccessStrategy the {@link PropertyAccessStrategy property access strategy} to use.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withPropertyAccessStrategy(PropertyAccessStrategy propertyAccessStrategy) {
            this.propertyAccessStrategy = propertyAccessStrategy;
            return this;
        }

        /**
         * Sets the {@link PropertyTransienceStrategy property mapping strategy} to use.
         * The default is {@link DefaultPropertyTransienceStrategy}.
         *
         * @param propertyTransienceStrategy the {@link PropertyTransienceStrategy property mapping strategy} to use.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withPropertyMappingStrategy(PropertyTransienceStrategy propertyTransienceStrategy) {
            this.propertyTransienceStrategy = propertyTransienceStrategy;
            return this;
        }

        /**
         * Sets the {@link HierarchyScanStrategy hierarchy scan strategy} to use.
         *
         * @param hierarchyScanStrategy the {@link HierarchyScanStrategy hierarchy scan strategy} to use.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withHierarchyScanStrategy(HierarchyScanStrategy hierarchyScanStrategy) {
            this.hierarchyScanStrategy = hierarchyScanStrategy;
            return this;
        }

        /**
         * Builds a new instance of {@link PropertyScanConfiguration} with this builder's
         * settings.
         *
         * @return a new instance of {@link PropertyScanConfiguration}
         */
        public PropertyScanConfiguration build() {
            return new PropertyScanConfiguration(propertyAccessStrategy, propertyTransienceStrategy, hierarchyScanStrategy);
        }

    }

    private final PropertyAccessStrategy propertyAccessStrategy;

    private final PropertyTransienceStrategy propertyTransienceStrategy;

    private final HierarchyScanStrategy hierarchyScanStrategy;

    private PropertyScanConfiguration(PropertyAccessStrategy propertyAccessStrategy, PropertyTransienceStrategy propertyTransienceStrategy, HierarchyScanStrategy hierarchyScanStrategy) {
        this.propertyAccessStrategy = propertyAccessStrategy;
        this.propertyTransienceStrategy = propertyTransienceStrategy;
        this.hierarchyScanStrategy = hierarchyScanStrategy;
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
     * Returns the {@link PropertyTransienceStrategy}.
     *
     * @return the {@link PropertyTransienceStrategy}.
     */
    public PropertyTransienceStrategy getPropertyTransienceStrategy() {
        return propertyTransienceStrategy;
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
