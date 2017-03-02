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
 * The configuration to use for the mappers.
 * <p/>
 * The following categories can be configured:
 * <ol>
 * <li>
 * {@link PropertyScanConfiguration property scanning}
 * <ul>
 * <li>{@link PropertyAccessStrategy property access strategy}: defines how to access mapped properties.</li>
 * <li>{@link PropertyTransienceStrategy property mapping strategy}: defines whether or not to map all discovered
 * properties, or only those explicitly annotated.</li>
 * <li>{@link HierarchyScanStrategy hierarchy scanning strategy}: defines how to scan for mapped properties in
 * parent classes.</li>
 * </ul>
 * </li>
 * </ol>
 */
public class MappingConfiguration {

    /**
     * Returns a new {@link Builder} instance.
     *
     * @return a new {@link Builder} instance.
     */
    public static MappingConfiguration.Builder builder() {
        return new MappingConfiguration.Builder();
    }

    /**
     * Builder for {@link MappingConfiguration} instances.
     */
    public static class Builder {

        private PropertyScanConfiguration propertyScanConfiguration = PropertyScanConfiguration.builder().build();

        /**
         * Sets the {@link PropertyScanConfiguration} to use.
         *
         * @param propertyScanConfiguration the {@link PropertyScanConfiguration} to use.
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withPropertyScanConfiguration(PropertyScanConfiguration propertyScanConfiguration) {
            this.propertyScanConfiguration = propertyScanConfiguration;
            return this;
        }

        /**
         * Builds a new instance of {@link MappingConfiguration} with this builder's
         * settings.
         *
         * @return a new instance of {@link MappingConfiguration}
         */
        public MappingConfiguration build() {
            return new MappingConfiguration(propertyScanConfiguration);
        }
    }

    private final PropertyScanConfiguration propertyScanConfiguration;

    private MappingConfiguration(PropertyScanConfiguration propertyScanConfiguration) {
        this.propertyScanConfiguration = propertyScanConfiguration;
    }

    /**
     * Returns {@link PropertyScanConfiguration property scanning configuration}.
     *
     * @return the property scanning configuration.
     */
    public PropertyScanConfiguration getPropertyScanConfiguration() {
        return propertyScanConfiguration;
    }

}
