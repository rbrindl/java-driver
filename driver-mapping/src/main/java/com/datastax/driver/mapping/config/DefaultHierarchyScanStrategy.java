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

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The default {@link HierarchyScanStrategy}.
 * <p/>
 * This strategy assumes that there exists a common ancestor
 * for all mapped classes in the application, and allows all its
 * descendants (optionally including itself) to be scanned for annotations.
 * <p/>
 * By default, the common ancestor is {@link Object}, which implies
 * that every ancestor of a mapped class, except {@code Object} itself,
 * will be scanned for annotations.
 */
public class DefaultHierarchyScanStrategy implements HierarchyScanStrategy {

    /**
     * Returns a new {@link DefaultHierarchyScanStrategy.Builder} instance.
     *
     * @return a new {@link DefaultHierarchyScanStrategy.Builder} instance.
     */
    public static DefaultHierarchyScanStrategy.Builder builder() {
        return new DefaultHierarchyScanStrategy.Builder();
    }

    /**
     * Builder for {@link DefaultHierarchyScanStrategy} instances.
     */
    public static class Builder {

        private Class<?> highestAncestor = Object.class;
        private boolean includeHighestAncestor = false;

        /**
         * Sets the highest ancestor that will be scanned for mapping annotations.
         * <p/>
         * If this method is not called, the default is to scan up to {@code Object}, excluded (the equivalent of
         * {@code withHighestAncestor(Object.class, false)}).
         *
         * @param highestAncestor The highest ancestor class to consider; cannot be {@code null}.
         * @param included        Whether or not to include the highest ancestor itself.
         */
        public Builder withHighestAncestor(Class<?> highestAncestor, boolean included) {
            checkNotNull(highestAncestor);
            this.highestAncestor = highestAncestor;
            this.includeHighestAncestor = included;
            return this;
        }

        /**
         * Builds a new instance of {@link DefaultHierarchyScanStrategy} with this builder's
         * settings.
         *
         * @return a new instance of {@link DefaultHierarchyScanStrategy}
         */
        public DefaultHierarchyScanStrategy build() {
            return new DefaultHierarchyScanStrategy(highestAncestor, includeHighestAncestor);
        }
    }

    private final Class<?> highestAncestor;

    private final boolean includeHighestAncestor;

    private DefaultHierarchyScanStrategy(Class<?> highestAncestor, boolean includeHighestAncestor) {
        this.highestAncestor = highestAncestor;
        this.includeHighestAncestor = includeHighestAncestor;
    }

    @Override
    public List<Class<?>> filterClassHierarchy(Class<?> mappedClass) {
        List<Class<?>> classesToScan = new ArrayList<Class<?>>();
        Class<?> highestAncestor = this.highestAncestor;
        for (Class<?> clazz = mappedClass; clazz != null; clazz = clazz.getSuperclass()) {
            if (!clazz.equals(highestAncestor) || includeHighestAncestor) {
                classesToScan.add(clazz);
            }
            if (clazz.equals(highestAncestor)) {
                break;
            }
        }
        return classesToScan;
    }
}
