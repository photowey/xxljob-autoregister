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
            cron = "0/10 * * * * ? *"
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
            cron = "0/10 * * * * ? *"
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
            cron = "0/10 * * * * ? *"
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

}
