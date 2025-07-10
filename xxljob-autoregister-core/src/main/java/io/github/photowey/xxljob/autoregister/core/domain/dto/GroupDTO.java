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
package io.github.photowey.xxljob.autoregister.core.domain.dto;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.github.photowey.xxljob.autoregister.core.domain.payload.GroupAddPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code GroupDTO}.
 *
 * @author pgotowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupDTO extends GroupAddPayload {

    @Serial
    private static final long serialVersionUID = 7673827609262026940L;

    private int id;
    private Date updateTime;
    private List<String> registryList;

    public List<String> registryList() {
        if (addressList != null && !addressList.trim().isEmpty()) {
            registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
        }

        return registryList;
    }
}
