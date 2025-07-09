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
package io.github.photowey.xxljob.autoregister.core.domain.payload;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Group}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupAddPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = -6018925021838390781L;

    private int id;
    private String appname;
    private String title;
    private int addressType;
    private String addressList;
    private Date updateTime;
    private List<String> registryList;

    // ----------------------------------------------------------------


    public int id() {
        return id;
    }

    public String appname() {
        return appname;
    }

    public String title() {
        return title;
    }

    public int addressType() {
        return addressType;
    }

    public String addressList() {
        return addressList;
    }

    public Date updateTime() {
        return updateTime;
    }

    public List<String> registryList() {
        if (this.addressList != null && !this.addressList.trim().isEmpty()) {
            this.registryList = new ArrayList<>(Arrays.asList(this.addressList.split(",")));
        }

        return this.registryList;
    }
}
