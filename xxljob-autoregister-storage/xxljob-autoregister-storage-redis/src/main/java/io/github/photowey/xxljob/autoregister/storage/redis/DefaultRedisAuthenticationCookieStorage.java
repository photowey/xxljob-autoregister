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
package io.github.photowey.xxljob.autoregister.storage.redis;

import java.time.Duration;

import io.github.photowey.xxljob.autoregister.core.event.LoadAuthenticationCookieEvent;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractApplicationContextHolder;
import io.github.photowey.xxljob.autoregister.storage.api.RedisAuthenticationCookieStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * {@code DefaultRedisAuthenticationCookieStorage}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public class DefaultRedisAuthenticationCookieStorage
    extends AbstractApplicationContextHolder
    implements RedisAuthenticationCookieStorage {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String store(String cacheKey, String cookie) {
        Long expireSeconds = this.xxljobProperties().cache().expireSeconds();
        this.redisTemplate.opsForValue().set(cacheKey, cookie, Duration.ofSeconds(expireSeconds));

        return cookie;
    }

    @Override
    public String tryFastAcquire(String cacheKey) {
        return this.redisTemplate.opsForValue().get(cacheKey);
    }

    @Override
    public String tryAcquire(String cacheKey) {
        String cookie = this.tryFastAcquire(cacheKey);
        if (StringUtils.hasText(cookie)) {
            return cookie;
        }

        LoadAuthenticationCookieEvent event = new LoadAuthenticationCookieEvent();
        this.applicationContext().publishEvent(event);
        if (StringUtils.hasText(event.cookie())) {
            return event.cookie();
        }

        return this.redisTemplate.opsForValue().get(cacheKey);
    }
}
