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
 * A strategy to determine how ancestors of mapped classes are scanned for annotations.
 */
public class HierarchyScanStrategy {

    /**
     * An instance that disables hierarchy scanning completely.
     */
    public static HierarchyScanStrategy DISABLED = new HierarchyScanStrategy(null, false);

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

        private Class<?> highestAncestor = Object.class;
        private boolean includeHighestAncestor = false;

        /**
         * Sets the highest ancestor that will be scanned for mapping annotations.
         * <p/>
         * If this method is not called, the default is to scan up to {@code Object}, excluded (the equivalent of
         * {@code withHighestAncestor(Object.class, false)}).
         * <p/>
         * To disable hierarchy scanning, use {@link #DISABLED}.
         */
        public Builder withHighestAncestor(Class<?> highestAncestor, boolean included) {
            this.highestAncestor = highestAncestor;
            this.includeHighestAncestor = included;
            return this;
        }

        /**
         * Builds a new instance of {@link HierarchyScanStrategy} with this builder's
         * settings.
         *
         * @return a new instance of {@link HierarchyScanStrategy}
         */
        public HierarchyScanStrategy build() {
            return new HierarchyScanStrategy(highestAncestor, includeHighestAncestor);
        }
    }

    private final Class<?> highestAncestor;

    private final boolean includeHighestAncestor;

    private HierarchyScanStrategy(Class<?> highestAncestor, boolean includeHighestAncestor) {
        this.highestAncestor = highestAncestor;
        this.includeHighestAncestor = includeHighestAncestor;
    }

    /**
     * Returns the highest ancestor that will be scanned for mapping annotations.
     * <p/>
     * A value of {@code null} indicates that hierarchy scanning is disabled, only the mapped classes themselves will be
     * processed for annotations.
     */
    public Class<?> getHighestAncestor() {
        return highestAncestor;
    }

    /**
     * Returns whether or not the highest ancestor is included.
     */
    public boolean includeHighestAncestor() {
        return includeHighestAncestor;
    }

}
