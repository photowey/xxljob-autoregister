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
package io.github.photowey.xxljob.autoregister.register.converter.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * {@code JsonConverter}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
public interface JsonConverter {

    <T> String toJSONString(T body);

    // ----------------------------------------------------------------

    <T> T parseObject(String json, Class<T> clazz);

    <T> T parseObject(String json, TypeReference<T> typeRef);

    <T> T parseObject(byte[] json, Class<T> clazz);

    <T> T parseObject(byte[] json, TypeReference<T> typeRef);

    // ----------------------------------------------------------------

    <T> List<T> parseArray(String json, Class<T> clazz);

    <T> List<T> parseArray(String json, TypeReference<List<T>> typeRef);

    <T> List<T> parseArray(byte[] json, Class<T> clazz);

    <T> List<T> parseArray(byte[] json, TypeReference<List<T>> typeRef);

    // ----------------------------------------------------------------

    <T> Map<String, Object> toMap(T body);
}
