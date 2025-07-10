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
package io.github.photowey.xxljob.autoregister.storage.caffeine.config;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.photowey.xxljob.autoregister.core.property.XxljobProperties;
import io.github.photowey.xxljob.autoregister.storage.api.AuthenticationCookieStorage;
import io.github.photowey.xxljob.autoregister.storage.caffeine.CaffeineLocalAuthenticationCookieStorage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * {@code LocalAuthenticationCookieStorageAutoConfiguration}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@AutoConfiguration
public class LocalAuthenticationCookieStorageAutoConfiguration {

    @Bean
    public Cache<String, String> cache(XxljobProperties xxljobProperties) {
        Long expireSeconds = xxljobProperties.cache().expireSeconds();
        return Caffeine.newBuilder()
            .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationCookieStorage.class)
    public AuthenticationCookieStorage authenticationCookieStorage() {
        return new CaffeineLocalAuthenticationCookieStorage();
    }
}
