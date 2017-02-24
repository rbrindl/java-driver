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
 *
 */
public class HierarchyScanStrategy {

    private final boolean hierarchyScanEnabled;

    private final Class<?> nearestExcludedAncestor;

    public HierarchyScanStrategy() {
        this.hierarchyScanEnabled = true;
        this.nearestExcludedAncestor = Object.class;
    }

    /**
     * Returns whether or not the strategy allows scanning of parent classes.
     *
     * @return whether or not the strategy allows scanning of parent classes.
     */
    public boolean isHierarchyScanEnabled() {
        return hierarchyScanEnabled;
    }

    /**
     * Returns the nearest parent class that should not be scanned.
     * <p/>
     * This class and all its ancestors will be excluded from
     * hierarchy scan.
     * <p/>
     * The default is {@code Object.class}.
     *
     * @return the nearest parent class that should not be scanned.
     */
    public Class<?> getNearestExcludedAncestor() {
        return nearestExcludedAncestor;
    }

}
