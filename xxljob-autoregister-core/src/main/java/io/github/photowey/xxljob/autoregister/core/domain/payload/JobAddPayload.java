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
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code JobAddPayload}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobAddPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = 7966217081559884138L;

    // @see com.xxl.job.admin.core.model.XxlJobInfo
    // @see com.xxl.job.admin.controller.JobInfoController#add

    private int id;

    private int jobGroup;
    private String jobDesc;

    private Date addTime;
    private Date updateTime;

    private String author;
    private String alarmEmail;

    private String scheduleType;
    private String scheduleConf;
    private String misfireStrategy;

    private String executorRouteStrategy;
    private String executorHandler;
    private String executorParam;
    private String executorBlockStrategy;
    private int executorTimeout;
    private int executorFailRetryCount;

    private String glueType;
    private String glueSource;
    private String glueRemark;
    private Date glueUpdatetime;

    private String childJobId;

    private int triggerStatus;
    private long triggerLastTime;
    private long triggerNextTime;
}
