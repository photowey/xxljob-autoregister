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
package io.github.photowey.xxljob.autoregister.core.domain.payload;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code JobAddPayload}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JobAddPayload implements Serializable {

    @Serial
    protected static final long serialVersionUID = 7966217081559884138L;

    // @see com.xxl.job.admin.core.model.XxlJobInfo
    // @see com.xxl.job.admin.controller.JobInfoController#add

    // ---------------------------------------------------------------- Base

    protected int jobGroup;
    protected String jobDesc;
    protected String author;
    protected String alarmEmail;

    // ---------------------------------------------------------------- Schedule

    protected String scheduleType;
    protected String scheduleConf;

    // ---------------------------------------------------------------- Task

    protected String glueType;
    protected String executorHandler;
    protected String executorParam;

    // ---------------------------------------------------------------- Advanced

    protected String executorRouteStrategy;
    protected String childJobId;
    protected String misfireStrategy;
    protected String executorBlockStrategy;

    protected int executorTimeout;
    protected int executorFailRetryCount;

    // ----------------------------------------------------------------

    protected int triggerStatus;

    // ----------------------------------------------------------------

    public String executorHandler() {
        return executorHandler;
    }
}
