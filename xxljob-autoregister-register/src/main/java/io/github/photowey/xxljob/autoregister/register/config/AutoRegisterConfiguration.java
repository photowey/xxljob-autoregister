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

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import io.github.photowey.xxljob.autoregister.register.converter.json.DefaultObjectMapperJsonConverter;
import io.github.photowey.xxljob.autoregister.register.converter.json.JsonConverter;
import io.github.photowey.xxljob.autoregister.register.engine.DefaultRegisterEngine;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngine;
import io.github.photowey.xxljob.autoregister.register.processor.AutoRegisterBeanDefinitionRegistryPostProcessor;

/**
 * {@code AutoRegisterConfiguration}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Configuration
@Import(value = {
    AutoRegisterBeanDefinitionRegistryPostProcessor.class,
    AutoRegisterConfiguration.Scanner.class,
})
public class AutoRegisterConfiguration {

    // @formatter:off

    @ComponentScan(value = {
        "io.github.photowey.xxljob.autoregister.register.service",
        "io.github.photowey.xxljob.autoregister.register.listener",
    })
    public static class Scanner { }

    // @formatter:on

    @Bean
    public RegisterEngine registerEngine() {
        return new DefaultRegisterEngine();
    }

    @Bean
    public JsonConverter jsonConverter() {
        return new DefaultObjectMapperJsonConverter();
    }

    @Bean(XxljobConstants.Notify.NOTIFY_EXECUTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = XxljobConstants.Notify.NOTIFY_EXECUTOR_BEAN_NAME)
    public ThreadPoolTaskExecutor notifyAsyncTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(1 << 10);
        taskExecutor.setKeepAliveSeconds(1 << 6);

        // If necessary.
        // taskExecutor.setTaskDecorator

        taskExecutor.setThreadGroupName("async");
        taskExecutor.setThreadNamePrefix("xxljob-autoregister-notify" + "-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        taskExecutor.setAllowCoreThreadTimeOut(false);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        taskExecutor.initialize();

        return taskExecutor;
    }
}
