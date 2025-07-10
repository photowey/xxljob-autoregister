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
package io.github.photowey.xxljob.autoregister.register.service;

import java.util.List;
import java.util.function.Consumer;

import io.github.photowey.xxljob.autoregister.core.domain.dto.JobDTO;
import io.github.photowey.xxljob.autoregister.core.domain.payload.JobAddPayload;
import io.github.photowey.xxljob.autoregister.core.getter.EnvironmentGetter;
import io.github.photowey.xxljob.autoregister.core.getter.XxljobPropertiesGetter;
import io.github.photowey.xxljob.autoregister.register.context.RegisterContext;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngineGetter;
import org.springframework.util.MultiValueMap;

/**
 * {@code XxljobJobService}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public interface JobService extends RegisterEngineGetter, XxljobPropertiesGetter, EnvironmentGetter {

    /**
     * Add job.
     *
     * @param payload {@link JobAddPayload}
     * @return jobId
     */
    Integer add(JobAddPayload payload);

    // ----------------------------------------------------------------

    default List<JobDTO> groupJobs(Integer groupId, String executorHandler) {
        // @formatter:off
        return this.groupJobs(groupId, executorHandler, (x) -> { });
        // @formatter:on
    }

    List<JobDTO> groupJobs(Integer groupId, String executorHandler, Consumer<MultiValueMap<String, Object>> fx);

    // ----------------------------------------------------------------

    void triggerAutoRegister(RegisterContext ctx);

}
