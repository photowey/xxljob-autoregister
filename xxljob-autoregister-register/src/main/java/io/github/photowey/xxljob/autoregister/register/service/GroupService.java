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
package io.github.photowey.xxljob.autoregister.register.service;

import java.util.List;

import io.github.photowey.xxljob.autoregister.core.domain.dto.GroupDTO;
import io.github.photowey.xxljob.autoregister.core.domain.payload.GroupAddPayload;
import io.github.photowey.xxljob.autoregister.core.getter.XxljobPropertiesGetter;
import io.github.photowey.xxljob.autoregister.register.context.RegisterContext;
import io.github.photowey.xxljob.autoregister.register.engine.RegisterEngineGetter;

/**
 * {@code XxljobGroupService}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public interface GroupService extends RegisterEngineGetter, XxljobPropertiesGetter {

    default boolean add() {
        GroupAddPayload payload = GroupAddPayload.builder()
            .appname(this.xxljobProperties().group().appname())
            .title(this.xxljobProperties().group().title())
            .addressType(this.xxljobProperties().group().addressType())
            .addressList(this.xxljobProperties().group().addressList())
            .build();

        return this.add(payload);
    }

    boolean add(GroupAddPayload payload);

    // ----------------------------------------------------------------

    List<GroupDTO> groups();

    GroupDTO myself();

    // ----------------------------------------------------------------

    boolean determineIsRegistered();

    // ----------------------------------------------------------------

    void triggerAutoRegister(RegisterContext ctx);
}
