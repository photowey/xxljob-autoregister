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
package io.github.photowey.xxljob.autoregister.register.processor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.photowey.xxljob.autoregister.core.exception.XxljobRpcException;
import io.github.photowey.xxljob.autoregister.register.context.DefaultRegisterContext;
import io.github.photowey.xxljob.autoregister.register.context.RegisterContext;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngineGetter;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * {@code AutoRegisterBeanDefinitionRegistryPostProcessor}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Slf4j
@Getter
@Accessors(fluent = true)
public class AutoRegisterBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor,
    ApplicationListener<ContextRefreshedEvent>, RegisterEngineGetter {

    private ConfigurableListableBeanFactory beanFactory;
    private BeanDefinitionRegistry registry;

    private final AtomicBoolean started = new AtomicBoolean(false);

    @SuppressWarnings("all")
    private final ExecutorService taskExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.started.compareAndSet(false, true)) {
            this.triggerAutoRegisterIfNecessary();
        }
    }

    /**
     * Async register if Necessary.
     */
    private void triggerAutoRegisterIfNecessaryAsync() {
        CompletableFuture.runAsync(this::triggerAutoRegisterIfNecessary, this.taskExecutor);
    }

    private void triggerAutoRegisterIfNecessary() {
        try {
            this.onAutoRegisterIfNecessary();
        } catch (Throwable e) {
            log.error("xxljob: trigger auto register failed", e);
            throw new XxljobRpcException(e);
        }
    }

    private void onAutoRegisterIfNecessary() {
        RegisterContext ctx = DefaultRegisterContext.builder()
            .beanFactory(this.beanFactory)
            .registry(this.registry)
            .build();

        this.triggerAutoRegisterGroupIfNecessary(ctx);
        this.triggerAutoRegisterJobIfNecessary(ctx);
    }

    private void triggerAutoRegisterGroupIfNecessary(RegisterContext ctx) {
        this.registerEngine().groupService().triggerAutoRegister(ctx);
    }

    private void triggerAutoRegisterJobIfNecessary(RegisterContext ctx) {
        this.registerEngine().jobService().triggerAutoRegister(ctx);
    }
}
