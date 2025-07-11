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
import java.util.Objects;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.photowey.xxljob.autoregister.core.getter.BeanFactoryGetter;

/**
 * {@code ObjectMapperJsonConverter}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@SuppressWarnings("all")
public interface ObjectMapperJsonConverter extends JsonConverter, BeanFactoryGetter {

    default ObjectMapper objectMapper() {
        return this.beanFactory().getBean(ObjectMapper.class);
    }

    @Override
    default <T> String toJSONString(T body) {
        if (Objects.isNull(body)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().writeValueAsString(body);
        });
    }

    @Override
    default <T> T parseObject(String json, Class<T> clazz) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().readValue(json, clazz);
        });
    }

    @Override
    default <T> T parseObject(String json, TypeReference<T> typeRef) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().readValue(json, typeRef);
        });
    }

    @Override
    default <T> T parseObject(byte[] json, Class<T> clazz) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().readValue(json, clazz);
        });
    }

    @Override
    default <T> T parseObject(byte[] json, TypeReference<T> typeRef) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().readValue(json, typeRef);
        });
    }

    @Override
    default <T> List<T> parseArray(String json, Class<T> clazz) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            CollectionType collectionType = this.toListCollectionType(clazz);
            return this.objectMapper().readValue(json, collectionType);
        });
    }

    @Override
    default <T> List<T> parseArray(String json, TypeReference<List<T>> typeRef) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().readValue(json, typeRef);
        });
    }

    @Override
    default <T> List<T> parseArray(byte[] json, Class<T> clazz) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            CollectionType collectionType = this.toListCollectionType(clazz);
            return this.objectMapper().readValue(json, collectionType);
        });
    }

    @Override
    default <T> List<T> parseArray(byte[] json, TypeReference<List<T>> typeRef) {
        if (Objects.isNull(json)) {
            return null;
        }

        return this.call(() -> {
            return this.objectMapper().readValue(json, typeRef);
        });
    }

    // ----------------------------------------------------------------

    @Override
    default <T> Map<String, Object> toMap(T body) {
        if (Objects.isNull(body)) {
            return null;
        }

        return this.call(() -> {
            // @formatter:off
            return this.objectMapper().convertValue(body, new TypeReference<Map<String, Object>>() { });
            // @formatter:on
        });
    }

    // ----------------------------------------------------------------

    default <T> CollectionType toListCollectionType(Class<T> clazz) {
        TypeFactory typeFactory = this.objectMapper().getTypeFactory();
        return typeFactory.constructCollectionType(List.class, clazz);
    }

    // ----------------------------------------------------------------

    default void run(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            this.throwUnchecked(e);
        }
    }

    default <T> T call(Callable<T> task) {
        try {
            return task.call();
        } catch (Exception e) {
            return this.throwUnchecked(e);
        }
    }

    // ----------------------------------------------------------------

    default <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError("Unreachable here!");
    }

    default <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    default <T extends Throwable> void throwsUnchecked(Throwable throwable) throws T {
        throw (T) throwable;
    }
}
