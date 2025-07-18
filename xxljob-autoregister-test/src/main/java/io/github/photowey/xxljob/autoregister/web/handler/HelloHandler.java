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
package io.github.photowey.xxljob.autoregister.web.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.photowey.xxljob.autoregister.core.enums.RegisterDictionary;
import io.github.photowey.xxljob.autoregister.register.annotation.AutoJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * {@code HelloHandler}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Slf4j
@Component
public class HelloHandler {

    @XxlJob("io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#mixedScheduleTask")
    @AutoJob(
        base = @AutoJob.Base(
            description = "mixedScheduleTask",
            author = "photowey",
            email = "photowey@gmail.com"
        ),
        schedule = @AutoJob.Schedule(
            scheduleType = RegisterDictionary.ScheduleType.CRON,
            scheduleConf = "0/10 * * * * ? *"
        ),
        advanced = @AutoJob.Advanced(
            routeStrategy = RegisterDictionary.RouteStrategy.FIRST,
            misfireStrategy = RegisterDictionary.MisfireStrategy.DO_NOTHING,
            blockStrategy = RegisterDictionary.BlockStrategy.SERIAL_EXECUTION,
            executorTimeout = 0,
            executorFailRetryCount = 0
        )
    )
    public void mixedScheduleTask() {
        log.info("Hello mixedScheduleTask");
    }

    @AutoJob(
        job = @AutoJob.Job(
            value = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#proxyScheduleTask",
            enabled = true
        ),
        base = @AutoJob.Base(
            description = "proxyScheduleTask",
            author = "photowey",
            email = "photowey@gmail.com"
        ),
        schedule = @AutoJob.Schedule(
            scheduleType = RegisterDictionary.ScheduleType.CRON,
            scheduleConf = "0/10 * * * * ? *"
        ),
        task = @AutoJob.Task(
            handler = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#proxyScheduleTask",
            arguments = "photowey"
        ),
        advanced = @AutoJob.Advanced(
            routeStrategy = RegisterDictionary.RouteStrategy.FIRST,
            misfireStrategy = RegisterDictionary.MisfireStrategy.DO_NOTHING,
            blockStrategy = RegisterDictionary.BlockStrategy.SERIAL_EXECUTION,
            executorTimeout = 0,
            executorFailRetryCount = 0
        )
    )
    public void proxyScheduleTask() {
        log.info("Hello proxyScheduleTask");
    }

    @AutoJob(
        job = @AutoJob.Job(
            value = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#autoScheduleTask",
            enabled = true
        ),
        base = @AutoJob.Base(
            description = "autoScheduleTask",
            author = "photowey",
            email = "photowey@gmail.com"
        ),
        schedule = @AutoJob.Schedule(
            scheduleType = RegisterDictionary.ScheduleType.CRON,
            scheduleConf = "0/10 * * * * ? *"
        ),
        task = @AutoJob.Task(
            handler = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#autoScheduleTask",
            arguments = "photowey"
        ),
        advanced = @AutoJob.Advanced(
            routeStrategy = RegisterDictionary.RouteStrategy.FIRST,
            misfireStrategy = RegisterDictionary.MisfireStrategy.DO_NOTHING,
            blockStrategy = RegisterDictionary.BlockStrategy.SERIAL_EXECUTION,
            executorTimeout = 0,
            executorFailRetryCount = 0
        ),
        triggerStatus = 1
    )
    public void autoScheduleTask() {
        log.info("Hello autoScheduleTask");
    }

    @AutoJob(
        job = @AutoJob.Job(
            value = "${local.config.job.handler}",
            enabled = true
        ),
        base = @AutoJob.Base(
            description = "spelScheduleTask",
            author = "${local.config.job.base.author}",
            email = "${local.config.job.base.email}"
        ),
        schedule = @AutoJob.Schedule(
            scheduleType = RegisterDictionary.ScheduleType.CRON,
            scheduleConf = "${local.config.job.schedule.cron}"
        ),
        task = @AutoJob.Task(
            handler = "${local.config.job.handler}",
            arguments = "xxljob://spel?cron=${local.config.job.schedule.cron}"
        ),
        advanced = @AutoJob.Advanced(
            routeStrategy = RegisterDictionary.RouteStrategy.FIRST,
            misfireStrategy = RegisterDictionary.MisfireStrategy.DO_NOTHING,
            blockStrategy = RegisterDictionary.BlockStrategy.SERIAL_EXECUTION,
            executorTimeout = 0,
            executorFailRetryCount = 0
        ),
        triggerStatus = 1
    )
    public void spelScheduleTask() {
        log.info("Hello spelScheduleTask");
    }

    @AutoJob(
        job = @AutoJob.Job(
            value = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#eventScheduleTask",
            enabled = true
        ),
        base = @AutoJob.Base(
            description = "eventScheduleTask",
            author = "photowey",
            email = "photowey@gmail.com"
        ),
        schedule = @AutoJob.Schedule(
            scheduleType = RegisterDictionary.ScheduleType.CRON,
            scheduleConf = "0/10 * * * * ? *"
        ),
        task = @AutoJob.Task(
            handler = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#eventScheduleTask",
            arguments = "xxljob://event"
        ),
        advanced = @AutoJob.Advanced(
            routeStrategy = RegisterDictionary.RouteStrategy.FIRST,
            misfireStrategy = RegisterDictionary.MisfireStrategy.DO_NOTHING,
            blockStrategy = RegisterDictionary.BlockStrategy.SERIAL_EXECUTION,
            executorTimeout = 0,
            executorFailRetryCount = 0
        ),
        triggerStatus = 1
    )
    public void eventScheduleTask() {
        log.info("Hello eventScheduleTask");
    }

}
