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
package io.github.photowey.xxljob.autoregister.http.executor;

import java.net.HttpCookie;
import java.util.List;
import java.util.Objects;

import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import io.github.photowey.xxljob.autoregister.core.domain.http.HttpResponse;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractBeanFactoryHolder;
import io.github.photowey.xxljob.autoregister.http.parser.HttpCookieParser;
import io.github.photowey.xxljob.autoregister.storage.api.AuthenticationCookieStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * {@code DefaultRestTemplateRequestExecutor}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public class DefaultRestTemplateRequestExecutor
    extends AbstractBeanFactoryHolder
    implements RestTemplateRequestExecutor {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpCookieParser httpCookieParser;
    @Autowired
    private AuthenticationCookieStorage authenticationCookieStorage;

    @Override
    public <R, B> HttpResponse<R> post(String url, B body, HttpHeaders headers, Class<R> responseType) {
        if (Objects.isNull(headers)) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        this.enhanceCookieIfNecessary(headers);

        HttpEntity<?> request = new HttpEntity<>(body, headers);
        ResponseEntity<R> response = this.restTemplate.postForEntity(url, request, responseType);

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieHeaders = responseHeaders.get(HttpHeaders.SET_COOKIE);
        List<HttpCookie> httpCookies = this.httpCookieParser.parse(cookieHeaders);

        return HttpResponse.<R>builder()
            .headers(responseHeaders)
            .cookies(httpCookies)
            .body(response.getBody())
            .status(response.getStatusCode())
            .build();
    }

    @Override
    public <R> HttpResponse<R> post(
        String url, MultiValueMap<String, String> body, HttpHeaders headers, Class<R> responseType) {
        if (Objects.isNull(headers)) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        this.enhanceCookieIfNecessary(headers);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<R> response = this.restTemplate.postForEntity(url, request, responseType);

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieHeaders = responseHeaders.get(HttpHeaders.SET_COOKIE);
        List<HttpCookie> httpCookies = this.httpCookieParser.parse(cookieHeaders);

        return HttpResponse.<R>builder()
            .headers(responseHeaders)
            .cookies(httpCookies)
            .body(response.getBody())
            .status(response.getStatusCode())
            .build();
    }

    private void enhanceCookieIfNecessary(HttpHeaders headers) {
        if (!headers.containsKey(HttpHeaders.COOKIE)) {
            String cacheKey = this.xxljobProperties().cache().key();
            String cookieValue = this.authenticationCookieStorage.tryAcquire(cacheKey);
            String cookie =
                String.format("%s=%s", XxljobConstants.Cookie.XXLJOB_AUTHENTICATION_COOKIE_NAME, cookieValue);

            headers.put(HttpHeaders.COOKIE, List.of(cookie));
        }
    }
}
