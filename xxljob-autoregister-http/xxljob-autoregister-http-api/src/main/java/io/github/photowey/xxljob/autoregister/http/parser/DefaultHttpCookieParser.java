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
package io.github.photowey.xxljob.autoregister.http.parser;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * {@code DefaultHttpCookieParser}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public class DefaultHttpCookieParser implements HttpCookieParser {

    @Override
    public List<HttpCookie> parse(List<String> cookieHeaders) {
        List<HttpCookie> cookies = new ArrayList<>();
        if (cookieHeaders == null || cookieHeaders.isEmpty()) {
            return cookies;
        }

        for (String header : cookieHeaders) {
            List<HttpCookie> parsedCookies = HttpCookie.parse(header);
            cookies.addAll(parsedCookies);
        }

        return cookies;
    }

    @Override
    public Optional<HttpCookie> parse(List<String> cookieHeaders, String cookieKey) {
        return this.parse(cookieHeaders, (it) -> it.getName().equals(cookieKey));
    }

    @Override
    public Optional<HttpCookie> parse(List<String> cookieHeaders, Predicate<HttpCookie> tester) {
        List<HttpCookie> httpCookies = this.parse(cookieHeaders);
        return httpCookies.stream()
            .filter(tester)
            .findFirst();
    }
}
