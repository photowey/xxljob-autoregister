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
package io.github.photowey.xxljob.autoregister.core.constant;

/**
 * {@code XxljobConstants}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public interface XxljobConstants {

    interface Symbol {
        String SLASH = "/";
    }

    interface Api {
        /**
         * Login API.
         * <pre>
         * Request URL        http://127.0.0.1:18080/admin/login
         * Request Method     POST
         * Status Code        200 OK
         * Remote Address     127.0.0.1:18080
         * Referrer Policy    strict-origin-when-cross-origin
         * </pre>
         */
        String LOGIN = "/login";
        String JOB_INFO_ADD = "/jobinfo/add";
        String GROUP_JOB_PAGE_LIST = "/jobinfo/pageList";

        String GROUP_ADD = "/jobgroup/save";
        String GROUP_PAGE_LIST = "/jobgroup/pageList";
    }

    interface Cookie {
        String XXLJOB_AUTHENTICATION_COOKIE_NAME = "XXL_JOB_LOGIN_IDENTITY";
    }

    interface Header {
        String SKIP = "SKIP";
    }
}
