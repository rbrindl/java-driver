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
 * A strategy to determine how ancestors of annotated classes are mapped.
 */
public class HierarchyScanStrategy {

    /**
     * Returns a new {@link HierarchyScanStrategy.Builder} instance.
     *
     * @return a new {@link HierarchyScanStrategy.Builder} instance.
     */
    public static HierarchyScanStrategy.Builder builder() {
        return new HierarchyScanStrategy.Builder();
    }

    /**
     * Builder for {@link HierarchyScanStrategy} instances.
     */
    public static class Builder {

        private boolean hierarchyScanEnabled = true;

        private Class<?> nearestExcludedAncestor = Object.class;

        /**
         * Sets whether or not the strategy allows scanning of ancestors
         * of annotated classes.
         * The default is {@code true}.
         *
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withHierarchyScanEnabled(boolean hierarchyScanEnabled) {
            this.hierarchyScanEnabled = hierarchyScanEnabled;
            return this;
        }

        /**
         * Sets the nearest ancestor of any annotated class that should be prevented from mapping.
         * <p/>
         * This class and all its ancestors will be excluded from class hierarchy scan.
         * <p/>
         * The default is {@code Object.class}, which means that the entire class hierarchy is
         * scanned for mapped properties.
         *
         * @return this {@link Builder} instance (to allow for fluent builder pattern).
         */
        public Builder withNearestExcludedAncestor(Class<?> nearestExcludedAncestor) {
            this.nearestExcludedAncestor = nearestExcludedAncestor;
            return this;
        }

        /**
         * Builds a new instance of {@link HierarchyScanStrategy} with this builder's
         * settings.
         *
         * @return a new instance of {@link HierarchyScanStrategy}
         */
        public HierarchyScanStrategy build() {
            return new HierarchyScanStrategy(hierarchyScanEnabled, nearestExcludedAncestor);
        }
    }

    private final boolean hierarchyScanEnabled;

    private final Class<?> nearestExcludedAncestor;

    private HierarchyScanStrategy(boolean hierarchyScanEnabled, Class<?> nearestExcludedAncestor) {
        this.hierarchyScanEnabled = hierarchyScanEnabled;
        this.nearestExcludedAncestor = nearestExcludedAncestor;
    }

    /**
     * Returns whether or not the strategy allows scanning ancestors
     * of annotated classes.
     *
     * @return whether or not the strategy allows scanning ancestors
     * of annotated classes.
     */
    public boolean isHierarchyScanEnabled() {
        return hierarchyScanEnabled;
    }

    /**
     * Returns the nearest ancestor of any annotated class that should be prevented from mapping.
     * <p/>
     * This class and all its ancestors will be excluded from class hierarchy scan.
     * <p/>
     * The default is {@code Object.class}, which means that the entire class hierarchy is
     * scanned for mapped properties.
     *
     * @return the nearest ancestor of any annotated class that should be prevented from mapping.
     */
    public Class<?> getNearestExcludedAncestor() {
        return nearestExcludedAncestor;
    }

}
