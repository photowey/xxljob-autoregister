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
package io.github.photowey.xxljob.autoregister.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.github.photowey.xxljob.autoregister.core.domain.payload.JobAddPayload;
import io.github.photowey.xxljob.autoregister.core.event.JobRegisteredEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code JobRegisteredEventListener}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/18
 */
@Slf4j
@Component
public class JobRegisteredEventListener implements ApplicationListener<JobRegisteredEvent> {

    @Override
    public void onApplicationEvent(JobRegisteredEvent event) {
        JobAddPayload payload = event.payload();
        log.info("xxljob-client: received the registered event, info:[group:{},handler:{}]",
            payload.getJobGroup(),
            payload.executorHandler()
        );
    }
}
