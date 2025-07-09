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
package io.github.photowey.xxljob.autoregister.storage.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractBeanFactoryHolder;
import io.github.photowey.xxljob.autoregister.storage.api.LocalAuthenticationCookieStorage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@code CaffeineLocalAuthenticationCookieStorage}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public class CaffeineLocalAuthenticationCookieStorage
    extends AbstractBeanFactoryHolder
    implements LocalAuthenticationCookieStorage {

    @Autowired
    private Cache<String, String> cache;

    @Override
    public String store(String cacheKey, String cookie) {
        this.cache.put(cacheKey, cookie);

        return cookie;
    }

    @Override
    public String tryAcquire(String cacheKey) {
        return this.cache.getIfPresent(cacheKey);
    }
}
