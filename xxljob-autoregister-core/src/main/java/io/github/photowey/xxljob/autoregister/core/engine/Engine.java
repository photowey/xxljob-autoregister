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
package io.github.photowey.xxljob.autoregister.core.engine;

import io.github.photowey.xxljob.autoregister.core.getter.ApplicationContextGetter;
import io.github.photowey.xxljob.autoregister.core.getter.BeanFactoryGetter;
import io.github.photowey.xxljob.autoregister.core.getter.EnvironmentGetter;

// @formatter:off

/**
 * {@code Engine}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public interface Engine extends BeanFactoryGetter, ApplicationContextGetter, EnvironmentGetter { }

// @formatter:on
