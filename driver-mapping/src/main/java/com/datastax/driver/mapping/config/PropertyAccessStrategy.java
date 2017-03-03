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
     * Returns {@code true} if field scan is allowed, {@code false} otherwise.
     * <p/>
     * If this method returns {@code false}, the mapper will not attempt to scan mapped classes
     * for mappable fields.
     * <p/>
     * If both this method and {@link #isGetterSetterScanAllowed()} return {@code false},
     * then the mapper will not be able to read and write properties using built-in implementations
     * of this interface; the client application will have to provide its own implementation for this
     * strategy, possibly resorting to non-conventional access mechanisms, such as compile-time code generation.
     *
     * @return {@code true} if field access is allowed, {@code false} otherwise.
     */
    boolean isFieldScanAllowed();

    /**
     * Returns {@code true} if getter and setter scan is allowed, {@code false} otherwise.
     * <p/>
     * If this method returns {@code false}, the mapper will not attempt to scan mapped classes
     * for mappable getters and setters.
     * <p/>
     * If both this method and {@link #isFieldScanAllowed()} return {@code false},
     * then the mapper will not be able to read and write properties using built-in implementations
     * of this interface; the client application will have to provide its own implementation for this
     * strategy, possibly resorting to non-conventional access mechanisms, such as compile-time code generation.
     *
     * @return {@code true} if getter and setter access is allowed, {@code false} otherwise.
     */
    boolean isGetterSetterScanAllowed();

    /**
     * Locates a getter method for the given mapped class and given property.
     * <p/>
     * Most users should rely on the implementation provided in
     * {@link DefaultPropertyAccessStrategy#locateGetter(Class, PropertyDescriptor) DefaultPropertyAccessStrategy}.
     * It is however possible to return any non-standard method, as long as it does
     * not take parameters, and its return type is assignable to (and covariant with) the property's type.
     * This might be particularly useful for boolean properties whose names are verbs, e.g. "{@code hasAccount}":
     * one could then return the non-standard method {@code boolean hasAccount()} as its getter.
     * <p/>
     * This method is never called if {@link #isGetterSetterScanAllowed()} returns {@code false}.
     * Besides, implementors are free to return {@code null} if access to the property through reflection is not required
     * (in which case, they will likely have to provide a custom implementation of {@link #getValue(Object, String, Field, Method)}).
     *
     * @param mappedClass The mapped class; this is necessarily a class annotated with
     *                    either {@link com.datastax.driver.mapping.annotations.Table @Table} or
     *                    {@link com.datastax.driver.mapping.annotations.UDT @UDT}.
     * @param property    The property to locate a getter for; never {@code null}.
     * @return The getter method for the given base class and given property, or {@code null} if no getter was found, or reflection is not required.
     */
    Method locateGetter(Class<?> mappedClass, PropertyDescriptor property);

    /**
     * Locates a setter method for the given mapped class and given property.
     * <p/>
     * Most users should rely on the implementation provided in
     * {@link DefaultPropertyAccessStrategy#locateSetter(Class, PropertyDescriptor) DefaultPropertyAccessStrategy}.
     * It is however possible to return any non-standard method, as long as it accepts one single parameter type
     * that is contravariant with the property's type.
     * <p/>
     * This method is never called if {@link #isGetterSetterScanAllowed()} returns {@code false}.
     * Besides, implementors are free to return {@code null} if access to the property through reflection is not required
     * (in which case, they will likely have to provide a custom implementation of {@link #setValue(Object, Object, String, Field, Method)}).
     *
     * @param mappedClass The mapped class; this is necessarily a class annotated with
     *                    either {@link com.datastax.driver.mapping.annotations.Table @Table} or
     *                    {@link com.datastax.driver.mapping.annotations.UDT @UDT}.
     * @param property    The property to locate a setter for; never {@code null}.
     * @return The setter method for the given base class and given property, or {@code null} if no setter was found, or reflection is not required.
     */
    Method locateSetter(Class<?> mappedClass, PropertyDescriptor property);

    /**
     * Reads the value of a property defined on {@code entity} and named {@code propertyName},
     * possibly using the given {@code field} and/or the given {@code getter} method.
     *
     * @param entity       The instance to read the property from; never {@code null}.
     * @param propertyName The property name, as inferred by the mapper; never {@code null}.
     * @param field        The property field; may be {@code null} if a field is not available,
     *                     or if {@link #isFieldScanAllowed() field access} is disabled for this strategy.
     * @param getter       The property's getter method, as inferred by
     *                     {@link #locateGetter(Class, PropertyDescriptor) this strategy};
     *                     may be {@code null} if a getter is not available,
     *                     or if {@link #isGetterSetterScanAllowed() getter/setter scan} is disabled for this strategy.
     * @return The property value.
     * @throws IllegalArgumentException if the property cannot be read.
     */
    Object getValue(Object entity, String propertyName, Field field, Method getter);

    /**
     * Writes the value {@code value} to the property defined on {@code entity} and named {@code propertyName},
     * possibly using the given {@code field} and/or the given {@code setter} method.
     *
     * @param entity       The instance to read the property from; never {@code null}.
     * @param propertyName The property name, as inferred by the mapper; never {@code null}.
     * @param field        The property field; may be {@code null} if a field is not available.
     * @param setter       The property's setter method, as inferred by
     *                     {@link #locateSetter(Class, PropertyDescriptor) this strategy};
     *                     may be {@code null} if a setter is not available,
     *                     or if {@link #isGetterSetterScanAllowed() getter/setter scan} is disabled for this strategy.
     * @throws IllegalArgumentException if the property cannot be written.
     */
    void setValue(Object entity, Object value, String propertyName, Field field, Method setter);

}
