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
package io.github.photowey.xxljob.autoregister.core.property;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * {@code XxljobProperties}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class XxljobProperties implements InitializingBean, Serializable {

    @Serial
    private static final long serialVersionUID = -3531038330534926057L;

    public static final String XXL_JOB_PROPERTY_CONFIG_PREFIX_ENV_KEY = "XXL_JOB_PROPERTY_CONFIG_PREFIX";
    public static final String XXL_JOB_PROPERTY_CONFIG_PREFIX_PROPERTY_KEY = "xxljob.configuration.property.prefix";

    @Valid
    private Admin admin = new Admin();
    @Valid
    private Authentication authentication = new Authentication();
    @Valid
    private Executor executor = new Executor();
    @Valid
    private Cache cache = new Cache();
    @Valid
    private Lock lock = new Lock();
    @Valid
    private Group group = new Group();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.checkExecutorAddress();
    }

    private void checkExecutorAddress() {
        if (StringUtils.hasText(this.executor().address())) {
            return;
        }

        if (!StringUtils.hasText(this.executor().ip())) {
            throw new IllegalArgumentException("xxljob: executor ip can't be blank");
        }

        if (Objects.isNull(this.executor().port())) {
            throw new IllegalArgumentException("xxljob: executor port can't be null");
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Admin implements Serializable {

        @Serial
        private static final long serialVersionUID = -4796505863406068658L;

        /**
         * xxl-job admin address
         * |- <a href="http://127.0.0.1:8080/xxl-job-admin">domain</a>
         */
        @NotBlank(message = "Xxljob: admin address can't be blank")
        private String address = "http://127.0.0.1:8080/xxl-job-admin";

        @NotBlank(message = "Xxljob: admin accessToken can't be blank")
        private String accessToken;

        // ----------------------------------------------------------------

        public String address() {
            return address;
        }

        public String accessToken() {
            return accessToken;
        }

        // ----------------------------------------------------------------

        public String wrapApi(String path) {
            String cleanedPath = path.startsWith(XxljobConstants.Symbol.SLASH) ? path.substring(1) : path;
            String cleanedAddress = this.address.endsWith(XxljobConstants.Symbol.SLASH)
                ? this.address.substring(0, this.address.length() - 1)
                : this.address;

            return String.format("%s/%s", cleanedAddress, cleanedPath);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Authentication implements Serializable {

        @Serial
        private static final long serialVersionUID = 6639121343370575691L;

        @NotBlank(message = "Xxljob: admin username can't be blank")
        private String username;
        @NotBlank(message = "Xxljob: admin password can't be blank")
        private String password;

        // ----------------------------------------------------------------

        public String username() {
            return username;
        }

        public String password() {
            return password;
        }
    }

    /**
     * The properties of {@code com.xxl.job.core.executor.impl.XxlJobSpringExecutor}
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Executor implements Serializable {

        @Serial
        private static final long serialVersionUID = -5875792754347340180L;

        private Integer timeout = 30;
        @NotBlank(message = "Xxljob: executor appname can't be blank")
        private String appname;
        private String address;
        private String ip;
        private Integer port;
        private String logPath = "/var/logs/xxljob";
        private Integer logRetentionDays = 30;

        // ----------------------------------------------------------------

        public Integer timeout() {
            return timeout;
        }

        public String appname() {
            return appname;
        }

        public String address() {
            return address;
        }

        public String ip() {
            return ip;
        }

        public Integer port() {
            return port;
        }

        public String logPath() {
            return logPath;
        }

        public Integer logRetentionDays() {
            return logRetentionDays;
        }
    }

    // ----------------------------------------------------------------

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cache implements Serializable {

        @Serial
        private static final long serialVersionUID = 8208402025506998479L;

        private String key = "xxljob:autoregister:authentication:global:cookie";
        private Long expireSeconds = TimeUnit.HOURS.toSeconds(1);

        // ----------------------------------------------------------------

        public String key() {
            return key;
        }

        public Long expireSeconds() {
            return expireSeconds;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Lock implements Serializable {

        @Serial
        private static final long serialVersionUID = 8208402025506998479L;

        private String key = "xxljob:autoregister:authentication:global:lock";

        // ----------------------------------------------------------------

        public String key() {
            return key;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Group implements Serializable {

        @Serial
        private static final long serialVersionUID = -4682737001564698890L;

        private String appname;
        private String title;
        private Integer addressType;
        private String addressList;

        // ----------------------------------------------------------------

        public String appname() {
            return appname;
        }

        public String title() {
            return title;
        }

        public Integer addressType() {
            return addressType;
        }

        public String addressList() {
            return addressList;
        }
    }

    // ----------------------------------------------------------------

    public Admin admin() {
        return admin;
    }

    public Authentication authentication() {
        return authentication;
    }

    public Executor executor() {
        return executor;
    }

    public Cache cache() {
        return cache;
    }

    public Lock lock() {
        return lock;
    }

    public Group group() {
        return group;
    }

    // ----------------------------------------------------------------

    public static String defaultConfigPropertyPrefix() {
        return "xxljob.automatic.register";
    }

    public static String determineConfigPropertyPrefix() {
        String prefix = System.getenv(XXL_JOB_PROPERTY_CONFIG_PREFIX_ENV_KEY);
        if (StringUtils.hasText(prefix)) {
            return prefix;
        }

        prefix = System.getProperty(XXL_JOB_PROPERTY_CONFIG_PREFIX_PROPERTY_KEY);
        if (StringUtils.hasText(prefix)) {
            return prefix;
        }

        return defaultConfigPropertyPrefix();
    }
}
