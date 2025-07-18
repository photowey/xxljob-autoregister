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
package io.github.photowey.xxljob.autoregister.register.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.github.photowey.xxljob.autoregister.core.event.LoadAuthenticationCookieEvent;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractApplicationContextHolder;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngineGetter;

/**
 * {@code LoadAuthenticationCookieEventListener}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Component
public class LoadAuthenticationCookieEventListener
    extends AbstractApplicationContextHolder
    implements ApplicationListener<LoadAuthenticationCookieEvent>, RegisterEngineGetter {

    @Override
    public void onApplicationEvent(LoadAuthenticationCookieEvent event) {
        this.sync(event);
    }

    private void sync(LoadAuthenticationCookieEvent event) {
        String cookie = this.registerEngine().loginService().tryAcquireAuthenticationCookie();
        if (StringUtils.hasText(cookie)) {
            event.cookie(cookie);
        }
    }
}
