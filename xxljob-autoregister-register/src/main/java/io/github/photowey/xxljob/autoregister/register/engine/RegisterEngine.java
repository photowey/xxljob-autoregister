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
package io.github.photowey.xxljob.autoregister.register.engine;

import io.github.photowey.xxljob.autoregister.core.engine.Engine;
import io.github.photowey.xxljob.autoregister.core.property.XxljobProperties;
import io.github.photowey.xxljob.autoregister.http.executor.RequestExecutor;
import io.github.photowey.xxljob.autoregister.http.parser.HttpCookieParser;
import io.github.photowey.xxljob.autoregister.lock.distributed.DistributedLock;
import io.github.photowey.xxljob.autoregister.register.converter.json.JsonConverter;
import io.github.photowey.xxljob.autoregister.register.service.GroupService;
import io.github.photowey.xxljob.autoregister.register.service.JobService;
import io.github.photowey.xxljob.autoregister.register.service.LoginService;
import io.github.photowey.xxljob.autoregister.storage.api.AuthenticationCookieStorage;

/**
 * {@code RegisterEngine}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public interface RegisterEngine extends Engine {

    /**
     * Acquire {@link XxljobProperties} instance.
     *
     * @return {@link XxljobProperties}
     */
    default XxljobProperties xxljobProperties() {
        return this.beanFactory().getBean(XxljobProperties.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link LoginService} instance.
     *
     * @return {@link LoginService}
     */
    default LoginService loginService() {
        return this.beanFactory().getBean(LoginService.class);
    }

    /**
     * Acquire {@link JobService} instance.
     *
     * @return {@link JobService}
     */
    default JobService jobService() {
        return this.beanFactory().getBean(JobService.class);
    }

    /**
     * Acquire {@link GroupService} instance.
     *
     * @return {@link GroupService}
     */
    default GroupService groupService() {
        return this.beanFactory().getBean(GroupService.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link DistributedLock} instance.
     *
     * @return {@link DistributedLock}
     */
    default DistributedLock distributedLock() {
        return this.beanFactory().getBean(DistributedLock.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link AuthenticationCookieStorage} instance.
     *
     * @return {@link AuthenticationCookieStorage}
     */
    default AuthenticationCookieStorage authenticationCookieStorage() {
        return this.beanFactory().getBean(AuthenticationCookieStorage.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link RequestExecutor} instance.
     *
     * @return {@link RequestExecutor}
     */
    default RequestExecutor requestExecutor() {
        return this.beanFactory().getBean(RequestExecutor.class);
    }

    /**
     * Acquire {@link HttpCookieParser} instance.
     *
     * @return {@link HttpCookieParser}
     */
    default HttpCookieParser httpCookieParser() {
        return this.beanFactory().getBean(HttpCookieParser.class);
    }

    // ----------------------------------------------------------------

    /**
     * Acquire {@link JsonConverter} instance.
     *
     * @return {@link JsonConverter}
     */
    default JsonConverter json() {
        return this.beanFactory().getBean(JsonConverter.class);
    }
}
