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
package io.github.photowey.xxljob.autoregister.register.service.impl;

import java.net.HttpCookie;
import java.util.Optional;

import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import io.github.photowey.xxljob.autoregister.core.domain.http.HttpResponse;
import io.github.photowey.xxljob.autoregister.core.exception.XxljobRpcException;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractBeanFactoryHolder;
import io.github.photowey.xxljob.autoregister.core.property.XxljobProperties;
import io.github.photowey.xxljob.autoregister.register.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * {@code XxljobLoginServiceImpl}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Slf4j
@Service
public class LoginServiceImpl extends AbstractBeanFactoryHolder implements LoginService {

    @Override
    public String login() {
        return this.tryLogin();
    }

    // ----------------------------------------------------------------

    @Override
    public String tryFastAcquireAuthenticationCookie() {
        String cacheKey = this.xxljobProperties().cache().key();
        return this.registerEngine().authenticationCookieStorage().tryFastAcquire(cacheKey);
    }

    @Override
    public String tryAcquireAuthenticationCookie() {
        String cacheKey = this.xxljobProperties().cache().key();
        String authenticationCookie = this.registerEngine().authenticationCookieStorage().tryFastAcquire(cacheKey);
        if (StringUtils.hasText(authenticationCookie)) {
            return authenticationCookie;
        }

        String lockKey = this.xxljobProperties().lock().key();
        return this.registerEngine().distributedLock().call(lockKey, () -> {
            String otherSet = this.registerEngine().authenticationCookieStorage().tryFastAcquire(cacheKey);
            if (StringUtils.hasText(otherSet)) {
                return otherSet;
            }

            return this.login();
        });
    }

    // ----------------------------------------------------------------

    private String tryLogin() {
        MultiValueMap<String, Object> formData = this.populateFormDataBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // The cookie enhancement must be skipped.
        headers.add(XxljobConstants.Header.SKIP, XxljobConstants.Header.SKIP_VALUE);

        String api = this.xxljobProperties().admin().wrapApi(XxljobConstants.Api.LOGIN);
        HttpResponse<String> responseEntity = this.registerEngine()
            .requestExecutor()
            .post(api, formData, headers, String.class);

        Optional<HttpCookie> cookieOpt = responseEntity.cookies().stream()
            .filter(it -> it.getName().equals(XxljobConstants.Cookie.XXLJOB_AUTHENTICATION_COOKIE_NAME))
            .findFirst();

        if (cookieOpt.isPresent()) {
            HttpCookie cookie = cookieOpt.get();
            if (StringUtils.hasText(cookie.getValue())) {
                String cacheKey = this.xxljobProperties().cache().key();
                this.registerEngine().authenticationCookieStorage().store(cacheKey, cookie.getValue());

                return cookie.getValue();
            }

            throw new XxljobRpcException("xxljob: the login authentication cookie not found");
        }

        throw new XxljobRpcException("xxljob: login failed,the response:[%s]", responseEntity.body());
    }

    private MultiValueMap<String, Object> populateFormDataBody() {
        XxljobProperties properties = this.registerEngine().xxljobProperties();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(4);
        body.add(XxljobConstants.Field.ADMIN_USER_NANE, properties.authentication().username());
        body.add(XxljobConstants.Field.ADMIN_PASSWORD, properties.authentication().password());

        return body;
    }
}
