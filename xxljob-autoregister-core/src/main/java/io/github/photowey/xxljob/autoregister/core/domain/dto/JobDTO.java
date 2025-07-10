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
package io.github.photowey.xxljob.autoregister.core.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import io.github.photowey.xxljob.autoregister.core.domain.payload.JobAddPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code JobDTO}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JobDTO extends JobAddPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = -8350572942599995584L;

    /**
     * API: http://127.0.0.1:${port}/${context-path}/jobinfo/pageList
     * <pre>
     * {
     *   "id": 1,
     *   "jobGroup": 1,
     *   "jobDesc": "示例任务01",
     *   "addTime": "2025-07-09T07:14:14.000+00:00",
     *   "updateTime": "2025-07-09T07:14:14.000+00:00",
     *   "author": "XXL",
     *   "alarmEmail": "",
     *   "scheduleType": "CRON",
     *   "scheduleConf": "0 0 0 * * ? *",
     *   "misfireStrategy": "DO_NOTHING",
     *   "executorRouteStrategy": "FIRST",
     *   "executorHandler": "demoJobHandler",
     *   "executorParam": "",
     *   "executorBlockStrategy": "SERIAL_EXECUTION",
     *   "executorTimeout": 0,
     *   "executorFailRetryCount": 0,
     *   "glueType": "BEAN",
     *   "glueSource": "",
     *   "glueRemark": "GLUE代码初始化",
     *   "glueUpdatetime": "2025-07-09T07:14:14.000+00:00",
     *   "childJobId": "",
     *   "triggerStatus": 0,
     *   "triggerLastTime": 0,
     *   "triggerNextTime": 0
     * }
     * </pre>
     */

    private int id;

    private Date addTime;
    private Date updateTime;

    private String glueSource;
    private String glueRemark;
    private Date glueUpdatetime;

    private String childJobId;

    private long triggerLastTime;
    private long triggerNextTime;
}
