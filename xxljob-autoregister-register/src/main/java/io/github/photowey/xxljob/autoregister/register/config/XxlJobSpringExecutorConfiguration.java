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
package io.github.photowey.xxljob.autoregister.register.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import io.github.photowey.xxljob.autoregister.core.property.XxljobProperties;
import io.github.photowey.xxljob.autoregister.register.engine.DefaultRegisterEngine;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * {@code XxlJobSpringExecutorConfiguration}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Configuration
@ComponentScan(value = {
    "io.github.photowey.xxljob.autoregister.register.service",
})
public class XxlJobSpringExecutorConfiguration {

    @Bean
    public RegisterEngine registerEngine() {
        return new DefaultRegisterEngine();
    }

    @Bean
    @ConditionalOnMissingBean(XxlJobSpringExecutor.class)
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxljobProperties props) {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(props.admin().address());
        xxlJobSpringExecutor.setAppname(props.executor().appname());
        xxlJobSpringExecutor.setAddress(props.executor().address());
        xxlJobSpringExecutor.setIp(props.executor().ip());
        xxlJobSpringExecutor.setPort(props.executor().port());
        xxlJobSpringExecutor.setAccessToken(props.admin().accessToken());
        xxlJobSpringExecutor.setTimeout(props.executor().timeout());
        xxlJobSpringExecutor.setLogPath(props.executor().logPath());
        xxlJobSpringExecutor.setLogRetentionDays(props.executor().logRetentionDays());

        return xxlJobSpringExecutor;
    }
}
