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

import com.datastax.driver.core.CCMTestsSupport;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for JAVA-1310 - validate ability configure ancestor property scanning:
 * - disable
 * - configure max depth ancestor (included or not)
 */
public class MappingConfigurationHierarchyScanStrategyTest extends CCMTestsSupport {

    @Override
    public void onTestContextInitialized() {
        execute("CREATE TABLE foo (k int primary key, v int)");
        execute("INSERT INTO foo (k, v) VALUES (1, 1)");
    }

    @Test(groups = "short")
    public void should_not_inherit_annotations_when_hierarchy_scan_disabled() {
        MappingConfiguration conf = MappingConfiguration.builder()
                .withPropertyScanConfiguration(PropertyScanConfiguration.builder()
                        .withHierarchyScanStrategy(HierarchyScanStrategy.DISABLED)
                        .build())
                .build();
        MappingManager mappingManager = new MappingManager(session(), conf);
        mappingManager.mapper(Foo1.class);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class Boo1 {

        @Column(name = "notAColumn")
        private int notAColumn;

    }

    @Table(name = "foo")
    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class Foo1 extends Boo1 {

        @PartitionKey
        private int k;

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k;
        }

    }

    @Test(groups = "short")
    public void should_inherit_annotations_up_to_highest_ancestor_exluded() {
        MappingConfiguration conf = MappingConfiguration.builder()
                .withPropertyScanConfiguration(PropertyScanConfiguration.builder()
                        .withHierarchyScanStrategy(HierarchyScanStrategy.builder()
                                .withHighestAncestor(Goo2.class, false)
                                .build())
                        .build())
                .build();
        MappingManager mappingManager = new MappingManager(session(), conf);
        Mapper<Foo2> mapper = mappingManager.mapper(Foo2.class);
        assertThat(mapper.get(1).getV()).isEqualTo(1);
    }

    @Test(groups = "short")
    public void should_inherit_annotations_up_to_highest_ancestor_included() {
        MappingConfiguration conf = MappingConfiguration.builder()
                .withPropertyScanConfiguration(PropertyScanConfiguration.builder()
                        .withHierarchyScanStrategy(HierarchyScanStrategy.builder()
                                .withHighestAncestor(Boo2.class, true)
                                .build())
                        .build())
                .build();
        MappingManager mappingManager = new MappingManager(session(), conf);
        Mapper<Foo2> mapper = mappingManager.mapper(Foo2.class);
        assertThat(mapper.get(1).getV()).isEqualTo(1);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class Goo2 {

        @Column(name = "notAColumn")
        private int notAColumn;

    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class Boo2 extends Goo2 {

        private int v;

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

    }

    @Table(name = "foo")
    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class Foo2 extends Boo2 {

        @PartitionKey
        private int k;

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k;
        }

    }

}
