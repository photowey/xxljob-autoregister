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
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import io.github.photowey.xxljob.autoregister.core.domain.http.HttpResponse;
import io.github.photowey.xxljob.autoregister.core.exception.XxljobRpcException;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractBeanFactoryHolder;
import io.github.photowey.xxljob.autoregister.http.parser.HttpCookieParser;
import io.github.photowey.xxljob.autoregister.storage.api.AuthenticationCookieStorage;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code DefaultRestTemplateRequestExecutor}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Slf4j
public class DefaultRestTemplateRequestExecutor
    extends AbstractBeanFactoryHolder
    implements RestTemplateRequestExecutor {

    private static final Pattern BAD_REQUEST_RESPONSE_PATTERN =
        Pattern.compile("\\{\"code\"\\s*:\\s*500\\s*,\\s*\"msg\"\\s*:\\s*\"(.*)\"(,.*)}");

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
        String url, MultiValueMap<String, Object> body, HttpHeaders headers, Class<R> responseType) {
        if (Objects.isNull(headers)) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        this.enhanceCookieIfNecessary(headers);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
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
        String url, MultiValueMap<String, Object> body, HttpHeaders headers, Function<String, R> converter) {

        if (Objects.isNull(headers)) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        this.enhanceCookieIfNecessary(headers);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class);
        String responseBody = response.getBody();
        if (HttpStatus.OK != response.getStatusCode()) {
            throw new XxljobRpcException(
                "xxljob: request admin API:[%s] failed, response body:[%s]", url, responseBody);
        }

        Matcher matcher = BAD_REQUEST_RESPONSE_PATTERN.matcher(Objects.requireNonNull(responseBody));
        if (matcher.matches()) {
            String message = matcher.group(1);
            if (log.isDebugEnabled()) {
                log.debug("xxljob: request admin API:[{}] response body is:[{}]", url, responseBody);
            }
            throw new XxljobRpcException("xxljob: request admin API:[%s] failed, message is:[%s]", url, message);
        }

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookieHeaders = responseHeaders.get(HttpHeaders.SET_COOKIE);
        List<HttpCookie> httpCookies = this.httpCookieParser.parse(cookieHeaders);

        return HttpResponse.<R>builder()
            .headers(responseHeaders)
            .cookies(httpCookies)
            .body(converter.apply(responseBody))
            .status(response.getStatusCode())
            .build();
    }

    private void enhanceCookieIfNecessary(HttpHeaders headers) {
        if (headers.containsKey(XxljobConstants.Header.SKIP)) {
            headers.remove(XxljobConstants.Header.SKIP);
            // /login|... must be skip.
            return;
        }

        if (!headers.containsKey(HttpHeaders.COOKIE)) {
            String cacheKey = this.xxljobProperties().cache().key();
            String cookieValue = this.authenticationCookieStorage.tryAcquire(cacheKey);
            String cookie =
                String.format("%s=%s", XxljobConstants.Cookie.XXLJOB_AUTHENTICATION_COOKIE_NAME, cookieValue);

            headers.add(HttpHeaders.COOKIE, cookie);
        }
    }
}
