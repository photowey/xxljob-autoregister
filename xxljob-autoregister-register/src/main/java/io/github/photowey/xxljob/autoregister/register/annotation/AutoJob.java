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
package io.github.photowey.xxljob.autoregister.register.annotation;

import io.github.photowey.xxljob.autoregister.core.enums.RegisterDictionary;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code AutoJob}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE/*@Inherited*/, ElementType.METHOD})
// @XxlJob Unsupported now.
public @interface AutoJob {

    // Unsupported now.
    // @XxlJob
    Job job() default @Job(
        value = "",
        enabled = false
    );

    Base base();

    Schedule schedule();

    Task task() default @Task;

    Advanced advanced();

    /**
     * STOP
     */
    int triggerStatus() default 0;

    // ----------------------------------------------------------------

    @interface Job {
        String value() default "";

        String init() default "";

        String destroy() default "";

        boolean enabled() default true;
    }

    @interface Base {
        String description();

        String author();

        String email() default "unknown@github.io";
    }

    @interface Schedule {
        RegisterDictionary.ScheduleType scheduleType() default RegisterDictionary.ScheduleType.CRON;

        String cron() default "";

        int fixRate() default 60;

        /*
         * Unsupported now.
         */
        /*int fixDelay() default 60;*/
    }

    @interface Task {
        RegisterDictionary.GlueType glueType() default RegisterDictionary.GlueType.BEAN;

        // @Job#value
        String handler() default "";

        String arguments() default "";
    }

    @interface Advanced {
        RegisterDictionary.RouteStrategy routeStrategy() default RegisterDictionary.RouteStrategy.ROUND;

        /**
         * Split -> ,
         *
         * @return
         */
        String childJobId() default "";

        RegisterDictionary.MisfireStrategy misfireStrategy() default RegisterDictionary.MisfireStrategy.DO_NOTHING;

        RegisterDictionary.BlockStrategy blockStrategy() default RegisterDictionary.BlockStrategy.SERIAL_EXECUTION;

        int executorTimeout() default 0;

        int executorFailRetryCount() default 0;
    }
}
