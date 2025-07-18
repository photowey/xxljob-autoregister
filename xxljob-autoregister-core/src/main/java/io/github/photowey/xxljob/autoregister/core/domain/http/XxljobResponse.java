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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code XxljobResponse}.
 *
 * @param <T> the type parameter
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XxljobResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -73985160573141555L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    private int code;
    @JsonProperty("msg")
    private String message;
    @JsonProperty("content")
    private T data;

    // ----------------------------------------------------------------

    public boolean determineIsSuccessful() {
        return SUCCESS_CODE == this.code;
    }

    public boolean determineIsFailed() {
        return FAIL_CODE == this.code;
    }

    // ----------------------------------------------------------------

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public T data() {
        return data;
    }
}
