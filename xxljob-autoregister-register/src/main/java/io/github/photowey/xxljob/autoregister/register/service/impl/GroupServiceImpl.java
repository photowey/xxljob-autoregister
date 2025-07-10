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
package io.github.photowey.xxljob.autoregister.register.service.impl;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import io.github.photowey.xxljob.autoregister.core.domain.dto.GroupDTO;
import io.github.photowey.xxljob.autoregister.core.domain.http.HttpResponse;
import io.github.photowey.xxljob.autoregister.core.domain.http.XxljobPageResponse;
import io.github.photowey.xxljob.autoregister.core.domain.http.XxljobResponse;
import io.github.photowey.xxljob.autoregister.core.domain.payload.GroupAddPayload;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractBeanFactoryHolder;
import io.github.photowey.xxljob.autoregister.register.context.RegisterContext;
import io.github.photowey.xxljob.autoregister.register.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * {@code GroupServiceImpl}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Slf4j
@Service
public class GroupServiceImpl extends AbstractBeanFactoryHolder implements GroupService {

    @Override
    public boolean add(GroupAddPayload payload) {
        MultiValueMap<String, Object> formData = this.populateAddFormDataBody(payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String api = this.xxljobProperties().admin().wrapApi(XxljobConstants.Api.GROUP_ADD);

        // TODO handle BAD_REQUEST response?
        HttpResponse<XxljobResponse<String>> response = this.registerEngine()
            .requestExecutor()
            .post(api, formData, headers, (body) -> {
                // @formatter:off
                return this.registerEngine().json()
                    .parseObject(body, new TypeReference<XxljobResponse<String>>() { });
                // @formatter:on
            });

        boolean ok = response.determineIsSuccessful();
        if (ok) {
            return ok;
        }

        throw new RuntimeException("xxljob: add group failed,info:" + response.body().message());
    }

    @Override
    public List<GroupDTO> groups() {
        MultiValueMap<String, Object> formData = this.populatePageFormDataBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String api = this.xxljobProperties().admin().wrapApi(XxljobConstants.Api.GROUP_PAGE_LIST);
        // TODO handle BAD_REQUEST response?
        HttpResponse<XxljobPageResponse<GroupDTO>> response = this.registerEngine()
            .requestExecutor()
            .post(api, formData, headers, (body) -> {
                // @formatter:off
                return this.registerEngine().json()
                    .parseObject(body, new TypeReference<XxljobPageResponse<GroupDTO>>() { });
                // @formatter:on
            });

        return response.body().data();
    }

    @Override
    public GroupDTO myself() {
        List<GroupDTO> groups = this.groups();
        if (CollectionUtils.isEmpty(groups)) {
            return null;
        }

        return groups.get(0);
    }

    // ----------------------------------------------------------------

    @Override
    public boolean determineIsRegistered() {
        List<GroupDTO> groups = this.groups();

        String appname = this.xxljobProperties().group().appname();
        String title = this.xxljobProperties().group().title();

        return groups.stream().anyMatch(
            it -> it.appname().equals(appname) && it.getTitle().equals(title)
        );
    }

    // ----------------------------------------------------------------

    @Override
    public void triggerAutoRegister(RegisterContext ctx) {
        if (this.determineIsRegistered()) {
            return;
        }

        this.onAutoRegister(ctx);
    }

    private void onAutoRegister(RegisterContext ctx) {
        this.add();
    }

    // ----------------------------------------------------------------

    private MultiValueMap<String, Object> populateAddFormDataBody(GroupAddPayload payload) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>(8);
        multiValueMap.add("appname", payload.appname());
        multiValueMap.add("title", payload.title());
        multiValueMap.add("addressType", payload.addressType());
        if (payload.determineIsManual()) {
            if (StringUtils.hasText(payload.addressList())) {
                multiValueMap.add("addressList", payload.addressList());
            } else {
                throw new RuntimeException("xxljob: addressType == 1, the addressList can't be empty");
            }

        }

        return multiValueMap;
    }

    private MultiValueMap<String, Object> populatePageFormDataBody() {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>(4);
        multiValueMap.add("appname", this.xxljobProperties().group().appname());
        multiValueMap.add("title", this.xxljobProperties().group().title());

        return multiValueMap;
    }
}
