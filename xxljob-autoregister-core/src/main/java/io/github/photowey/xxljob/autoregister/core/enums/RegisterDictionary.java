/*
 * Copyright (c) 2025-present
 * the original author(photowey<photowey@gmail.com>) or authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.xxljob.autoregister.core.enums;

/**
 * {@code XxljobDictionary}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public enum RegisterDictionary {

    ;

    public enum ScheduleType {

        // Schedule type.

        NONE("NONE", 1),
        CRON("CRON", 2),
        FIX_RATE("FIX_RATE", 3),

        /*
         * Unsupported now.
         */
        /*FIX_DELAY("FIX_DELAY", 4),*/;

        // ----------------------------------------------------------------

        private final String type;
        private final int value;

        ScheduleType(String type, int value) {
            this.type = type;
            this.value = value;
        }

        // ----------------------------------------------------------------

        public String type() {
            return type;
        }

        public int value() {
            return value;
        }

    }

    public enum GlueType {

        // Glue type.

        BEAN("BEAN", 1),
        GLUE_GROOVY("GLUE_GROOVY", 2),
        GLUE_SHELL("GLUE_SHELL", 3),
        GLUE_PYTHON("GLUE_PYTHON", 4),
        GLUE_PHP("GLUE_PHP", 5),
        GLUE_NODEJS("GLUE_NODEJS", 6),
        GLUE_POWERSHELL("GLUE_POWERSHELL", 7),

        ;

        // ----------------------------------------------------------------

        private final String type;
        private final int value;

        GlueType(String type, int value) {
            this.type = type;
            this.value = value;
        }

        // ----------------------------------------------------------------

        public String type() {
            return type;
        }

        public int value() {
            return value;
        }

    }

    public enum TriggerType {

        // Glue type.

        MANUAL("MANUAL", 1),
        CRON("CRON", 2),
        RETRY("RETRY", 3),
        PARENT("PARENT", 4),
        API("API", 5),
        MISFIRE("MISFIRE", 6),

        ;

        // ----------------------------------------------------------------

        private final String type;
        private final int value;

        TriggerType(String type, int value) {
            this.type = type;
            this.value = value;
        }

        // ----------------------------------------------------------------

        public String type() {
            return type;
        }

        public int value() {
            return value;
        }

    }

    public enum RouteStrategy {

        // Route strategy.

        FIRST("FIRST", 1),
        LAST("LAST", 2),
        ROUND("ROUND", 3),
        RANDOM("RANDOM", 4),
        CONSISTENT_HASH("CONSISTENT_HASH", 5),
        LEAST_FREQUENTLY_USED("LEAST_FREQUENTLY_USED", 6),
        LEAST_RECENTLY_USED("LEAST_RECENTLY_USED", 7),
        FAILOVER("FAILOVER", 8),
        BUSYOVER("BUSYOVER", 9),
        SHARDING_BROADCAST("SHARDING_BROADCAST", 10),

        ;

        // ----------------------------------------------------------------

        private final String type;
        private final int value;

        RouteStrategy(String type, int value) {
            this.type = type;
            this.value = value;
        }

        // ----------------------------------------------------------------

        public String type() {
            return type;
        }

        public int value() {
            return value;
        }
    }

    public enum MisfireStrategy {

        // Misfire strategy.

        DO_NOTHING("FIRST", 1),
        FIRE_ONCE_NOW("FIRST", 2),

        ;

        // ----------------------------------------------------------------

        private final String type;
        private final int value;

        MisfireStrategy(String type, int value) {
            this.type = type;
            this.value = value;
        }

        // ----------------------------------------------------------------

        public String type() {
            return type;
        }

        public int value() {
            return value;
        }
    }

    public enum BlockStrategy {

        // Block strategy.

        SERIAL_EXECUTION("SERIAL_EXECUTION", 1),
        DISCARD_LATER("DISCARD_LATER", 2),
        COVER_EARLY("COVER_EARLY", 3),

        ;

        // ----------------------------------------------------------------

        private final String type;
        private final int value;

        BlockStrategy(String type, int value) {
            this.type = type;
            this.value = value;
        }

        // ----------------------------------------------------------------

        public String type() {
            return type;
        }

        public int value() {
            return value;
        }
    }
}
