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
import java.lang.reflect.Modifier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The default access strategy used by the mapper.
 * <p/>
 * This strategy tries getters and setters first, if available,
 * then field access, as a last resort.
 * <p/>
 * It recognizes standard getter and setter methods (as defined by the Java Beans specification),
 * and also "relaxed" setter methods, i.e., setter methods whose return type are not {@code void}.
 * <p/>
 * Property values are read and written using the Java reflection API. Subclasses
 * may override {@link #getValue(Object, String, Field, Method)} and/or
 * {@link #setValue(Object, Object, String, Field, Method)} if they are capable of accessing
 * properties without incurring the cost of reflection.
 */
public class DefaultPropertyAccessStrategy implements PropertyAccessStrategy {

    @Override
    public boolean isFieldScanAllowed() {
        return true;
    }

    @Override
    public boolean isGetterSetterScanAllowed() {
        return true;
    }

    @Override
    public Method locateGetter(Class<?> mappedClass, PropertyDescriptor property) {
        return property.getReadMethod();
    }

    @Override
    public Method locateSetter(Class<?> mappedClass, PropertyDescriptor property) {
        Method setter = property.getWriteMethod();
        if (setter == null) {
            // JAVA-984: look for a "relaxed" setter, ie. a setter whose return type may be anything
            String propertyName = property.getName();
            String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            try {
                Method m = mappedClass.getMethod(setterName, property.getPropertyType());
                if (!Modifier.isStatic(m.getModifiers()))
                    setter = m;
            } catch (NoSuchMethodException ignored) {
            }
        }
        return setter;
    }

    @Override
    public Object getValue(Object entity, String propertyName, Field field, Method getter) {
        try {
            // try getter first, if available, otherwise direct field access
            if (getter != null && getter.isAccessible())
                return getter.invoke(entity);
            else
                return checkNotNull(field).get(entity);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to read property '" + propertyName + "' in " + entity.getClass(), e);
        }
    }

    @Override
    public void setValue(Object entity, Object value, String propertyName, Field field, Method setter) {
        try {
            // try setter first, if available, otherwise direct field access
            if (setter != null && setter.isAccessible())
                setter.invoke(entity, value);
            else
                field.set(entity, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to write property '" + propertyName + "' in " + entity.getClass(), e);
        }
    }


}
