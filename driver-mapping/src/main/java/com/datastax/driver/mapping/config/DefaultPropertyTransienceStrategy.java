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

import com.datastax.driver.mapping.annotations.Transient;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A {@link PropertyTransienceStrategy} that adopts a permissive, opt-out approach that
 * will consider a property to be non-transient by default, unless:
 * <ol>
 * <li>The property is annotated with {@link com.datastax.driver.mapping.annotations.Transient @Transient};</li>
 * <li>The corresponding field is non-null and is marked with the keyword {@code transient};</li>
 * <li>The property name has been explicitly black-listed.</li>
 * </ol>
 */
public class DefaultPropertyTransienceStrategy implements PropertyTransienceStrategy {

    private Set<String> transientProperties;

    private DefaultPropertyTransienceStrategy(Set<String> transientProperties) {
        this.transientProperties = transientProperties;
    }

    @Override
    public boolean isTransient(String propertyName, Field field, Method getter, Method setter, Map<Class<? extends Annotation>, Annotation> annotations) {
        return annotations.containsKey(Transient.class)
                || (field != null && Modifier.isTransient(field.getModifiers()))
                // If a property is both annotated and declared as transient,
                // annotations take precedence (the property will not be transient)
                || transientProperties.contains(propertyName) && annotations.isEmpty();
    }


    /**
     * Returns a new {@link DefaultPropertyTransienceStrategy.Builder} instance.
     *
     * @return a new {@link DefaultPropertyTransienceStrategy.Builder} instance.
     */
    public static DefaultPropertyTransienceStrategy.Builder builder() {
        return new DefaultPropertyTransienceStrategy.Builder();
    }

    /**
     * Builder for {@link DefaultPropertyTransienceStrategy} instances.
     */
    public static class Builder {

        private Set<String> transientProperties = Sets.newHashSet(
                "class",
                // JAVA-1279: exclude Groovy's metaClass property
                "metaClass"
        );

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
         *
         * @param transientProperties a collection of properties to exclude from mapping.
         * @return this {@link DefaultPropertyTransienceStrategy.Builder} instance (to allow for fluent builder pattern).
         */
        public DefaultPropertyTransienceStrategy.Builder addTransientProperties(Collection<String> transientProperties) {
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
         *
         * @param transientProperties a collection of properties to exclude from mapping.
         * @return this {@link DefaultPropertyTransienceStrategy.Builder} instance (to allow for fluent builder pattern).
         */
        public DefaultPropertyTransienceStrategy.Builder addTransientProperties(String... transientProperties) {
            return addTransientProperties(Arrays.asList(transientProperties));
        }


        /**
         * Builds a new instance of {@link DefaultPropertyTransienceStrategy} with this builder's
         * settings.
         *
         * @return a new instance of {@link DefaultPropertyTransienceStrategy}
         */
        public DefaultPropertyTransienceStrategy build() {
            return new DefaultPropertyTransienceStrategy(transientProperties);
        }

    }

}
