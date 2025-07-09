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
package io.github.photowey.xxljob.autoregister.core.domain.http;

import java.io.Serial;
import java.io.Serializable;
import java.net.HttpCookie;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * {@code HttpResponse}.
 *
 * @param <T> the type ResponseBody
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7182659489902106353L;

    private HttpHeaders headers;
    private List<HttpCookie> cookies;

    private T body;
    private HttpStatusCode status;

    // ----------------------------------------------------------------

    public boolean determineIsSuccessful() {
        return HttpStatus.OK == status;
    }

    // ----------------------------------------------------------------


    public HttpHeaders headers() {
        return headers;
    }

    public List<HttpCookie> cookies() {
        return cookies;
    }

    public T body() {
        return body;
    }

    public HttpStatusCode status() {
        return status;
    }
}
