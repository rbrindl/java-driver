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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A strategy to determine how mapped properties are read and written.
 */
public interface PropertyAccessStrategy {

    /**
     * Returns whether or not the given {@link Field} should
     * be used when accessing its underlying property.
     *
     * @return {@code true} if the given field should be considered, {@code false} otherwise.
     */
    boolean isFieldAccessAllowed();

    /**
     * Returns whether or not the given {@link PropertyDescriptor} should
     * be used when accessing its underlying property.
     *
     * @return {@code true} if the given {@link PropertyDescriptor} should be considered, {@code false} otherwise.
     */
    boolean isGetterSetterAccessAllowed();

    /**
     * Locates a getter method for the given base class and given property.
     * If this method returns {@code null} then no getter will be used to access the given property.
     *
     * @param mappedClass The mapped class; this is necessarily a class annotated with
     *                  either {@link com.datastax.driver.mapping.annotations.Table @Table} or
     *                  {@link com.datastax.driver.mapping.annotations.UDT @UDT}.
     * @param property  The property to locate a getter for; never {@code null}.
     * @return The getter method for the given base class and given property, or {@code null} if no getter was found.
     */
    Method locateGetter(Class<?> mappedClass, PropertyDescriptor property);

    /**
     * Locates a setter method for the given base class and given property.
     * If this method returns {@code null} then no setter will be used to access the given property.
     *
     * @param mappedClass The mapped class; this is necessarily a class annotated with
     *                  either {@link com.datastax.driver.mapping.annotations.Table @Table} or
     *                  {@link com.datastax.driver.mapping.annotations.UDT @UDT}.
     * @param property  The property to locate a setter for; never {@code null}.
     * @return The setter method for the given base class and given property, or {@code null} if no setter was found.
     */
    Method locateSetter(Class<?> mappedClass, PropertyDescriptor property);

    /**
     * Reads a property.
     *
     * @param entity       The instance to read the property from; never {@code null}.
     * @param propertyName The property name, as inferred by the mapper; never {@code null}.
     * @param field        The property field; may be {@code null}.
     *                     May be {@code null}, but {@code field} and {@code getter} cannot be both {@code null}.
     * @param getter       The property's getter method, as inferred by {@link #locateGetter(Class, PropertyDescriptor) this strategy}.
     *                     May be {@code null}, but {@code field} and {@code getter} cannot be both {@code null}.
     * @return The property value.
     * @throws IllegalArgumentException if the property cannot be read.
     */
    Object getValue(Object entity, String propertyName, Field field, Method getter);

    /**
     * Writes a property.
     *
     * @param entity       The instance to read the property from; never {@code null}.
     * @param propertyName The property name, as inferred by the mapper; never {@code null}.
     * @param field        The property field; may be {@code null}.
     *                     May be {@code null}, but {@code field} and {@code setter} cannot be both {@code null}.
     * @param setter       The property's setter method, as inferred by {@link #locateSetter(Class, PropertyDescriptor) this strategy}.
     *                     May be {@code null}, but {@code field} and {@code setter} cannot be both {@code null}.
     * @throws IllegalArgumentException if the property cannot be written.
     */
    void setValue(Object entity, Object value, String propertyName, Field field, Method setter);

}
