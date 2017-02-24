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

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

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

        private Set<String> transientProperties = Sets.newHashSet(
                "class",
                // JAVA-1279: exclude Groovy's metaClass property
                "metaClass"
        );

        private PropertyAccessStrategy propertyAccessStrategy = PropertyAccessStrategy.BOTH;

        private PropertyMappingStrategy propertyMappingStrategy = PropertyMappingStrategy.OPT_OUT;

        private HierarchyScanStrategy hierarchyScanStrategy = HierarchyScanStrategy.builder().build();

        /**
         * Adds properties to globally exclude from mapping.
         * <p/>
         * Property names added here will be considered transient throughout
         * all the mappers created with this configuration;
         * if a more fine-grained tuning is required, it is also possible
         * to use the {@link com.datastax.driver.mapping.annotations.Transient @Transient} annotation
         * on a specific property.
         * <p/>
         * The default for this setting is {@code {"class", "metaClass"}}.
         * These properties pertain to the {@link Object} class –
         * {@code metaClass} being specific to the Groovy language – so it is not possible
         * to remove them from the set of excluded properties.
         * <p/>
         * Note that this setting has no effect if the
         * {@link #withPropertyMappingStrategy(PropertyMappingStrategy) property mapping strategy}
         * is set to {@link PropertyMappingStrategy#OPT_IN OPT_IN}, because with this
         * strategy all properties are transient by default.
         *
         * @param transientProperties a collection of properties to exclude from mapping.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder addTransientProperties(Collection<String> transientProperties) {
            this.transientProperties.addAll(transientProperties);
            return this;
        }

        /**
         * Adds properties to globally exclude from mapping.
         * <p/>
         * Property names added here will be considered transient throughout
         * all the mappers created with this configuration;
         * if a more fine-grained tuning is required, it is also possible
         * to use the {@link com.datastax.driver.mapping.annotations.Transient @Transient} annotation
         * on a specific property.
         * <p/>
         * The default for this setting is {@code {"class", "metaClass"}}.
         * These properties pertain to the {@link Object} class –
         * {@code metaClass} being specific to the Groovy language – so it is not possible
         * to remove them from the set of excluded properties.
         * <p/>
         * Note that this setting has no effect if the
         * {@link #withPropertyMappingStrategy(PropertyMappingStrategy) property mapping strategy}
         * is set to {@link PropertyMappingStrategy#OPT_IN OPT_IN}, because with this
         * strategy all properties are transient by default.
         *
         * @param transientProperties a collection of properties to exclude from mapping.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder addTransientProperties(String... transientProperties) {
            return addTransientProperties(Arrays.asList(transientProperties));
        }

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
         * Sets the {@link PropertyMappingStrategy property mapping strategy} to use.
         * The default is {@link PropertyMappingStrategy#OPT_OUT}.
         *
         * @param propertyMappingStrategy the {@link PropertyMappingStrategy property mapping strategy} to use.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withPropertyMappingStrategy(PropertyMappingStrategy propertyMappingStrategy) {
            this.propertyMappingStrategy = propertyMappingStrategy;
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
            return new PropertyScanConfiguration(transientProperties, propertyAccessStrategy, propertyMappingStrategy, hierarchyScanStrategy);
        }

    }

    private final Set<String> transientProperties;

    private final PropertyAccessStrategy propertyAccessStrategy;

    private final PropertyMappingStrategy propertyMappingStrategy;

    private final HierarchyScanStrategy hierarchyScanStrategy;

    private PropertyScanConfiguration(Set<String> transientProperties, PropertyAccessStrategy propertyAccessStrategy, PropertyMappingStrategy propertyMappingStrategy, HierarchyScanStrategy hierarchyScanStrategy) {
        this.transientProperties = transientProperties;
        this.propertyAccessStrategy = propertyAccessStrategy;
        this.propertyMappingStrategy = propertyMappingStrategy;
        this.hierarchyScanStrategy = hierarchyScanStrategy;
    }

    /**
     * Returns a collection of properties to globally exclude from mapping.
     * <p/>
     * Property names defined here will be considered transient throughout
     * all the mappers created with this configuration;
     * if a more fine-grained tuning is required, it is also possible
     * to use the {@link com.datastax.driver.mapping.annotations.Transient @Transient} annotation
     * on a specific property.
     * <p/>
     * Note that this setting has no effect if the {@link #getPropertyMappingStrategy()}
     * is set to {@link PropertyMappingStrategy#OPT_IN OPT_IN}, because with this
     * strategy all properties are transient by default.
     *
     * @return a collection of properties to exclude from mapping.
     */
    public Set<String> getTransientProperties() {
        return transientProperties;
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
