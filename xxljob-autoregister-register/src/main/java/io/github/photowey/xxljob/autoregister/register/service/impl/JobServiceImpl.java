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

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import io.github.photowey.xxljob.autoregister.core.constant.XxljobConstants;
import io.github.photowey.xxljob.autoregister.core.domain.dto.GroupDTO;
import io.github.photowey.xxljob.autoregister.core.domain.dto.JobDTO;
import io.github.photowey.xxljob.autoregister.core.domain.http.HttpResponse;
import io.github.photowey.xxljob.autoregister.core.domain.http.XxljobPageResponse;
import io.github.photowey.xxljob.autoregister.core.domain.http.XxljobResponse;
import io.github.photowey.xxljob.autoregister.core.domain.payload.JobAddPayload;
import io.github.photowey.xxljob.autoregister.core.holder.AbstractBeanFactoryHolder;
import io.github.photowey.xxljob.autoregister.register.annotation.AutoJob;
import io.github.photowey.xxljob.autoregister.register.context.RegisterContext;
import io.github.photowey.xxljob.autoregister.register.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * {@code JobService}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
@Slf4j
@Service
public class JobServiceImpl extends AbstractBeanFactoryHolder implements JobService {

    @Override
    public Integer add(JobAddPayload payload) {
        MultiValueMap<String, Object> formData = this.populateAddFormDataBody(payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String api = this.xxljobProperties().admin().wrapApi(XxljobConstants.Api.JOB_INFO_ADD);

        // TODO handle BAD_REQUEST response?
        HttpResponse<XxljobResponse<Integer>> response = this.registerEngine()
            .requestExecutor()
            .post(api, formData, headers, (body) -> {
                // @formatter:off
                return this.registerEngine().json()
                    .parseObject(body, new TypeReference<XxljobResponse<Integer>>() { });
                // @formatter:on
            });

        return response.body().data();
    }

    // ----------------------------------------------------------------

    @Override
    public List<JobDTO> groupJobs(Integer groupId, String executorHandler, Consumer<MultiValueMap<String, Object>> fx) {
        MultiValueMap<String, Object> formData = this.populatePageFormDataBody(groupId, executorHandler);
        fx.accept(formData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String api = this.xxljobProperties().admin().wrapApi(XxljobConstants.Api.GROUP_JOB_PAGE_LIST);
        // TODO handle BAD_REQUEST response?
        HttpResponse<XxljobPageResponse<JobDTO>> response = this.registerEngine()
            .requestExecutor()
            .post(api, formData, headers, (body) -> {
                // @formatter:off
                return this.registerEngine().json()
                    .parseObject(body, new TypeReference<XxljobPageResponse<JobDTO>>() { });
                // @formatter:on
            });

        return response.body().data();
    }

    // ----------------------------------------------------------------

    @Override
    public void triggerAutoRegister(RegisterContext ctx) {
        GroupDTO group = this.registerEngine().groupService().myself();

        String[] beanDefinitionNames = ctx.registry().getBeanDefinitionNames();

        Set<String> methods = new HashSet<>();

        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ctx.beanFactory().getBean(beanDefinitionName);

            /*
             * <pre>
             * {@literal @}Xxljob(...)
             * {@literal @}AutoJob(...)
             * public void executeXxx(XxlJobHelper jobHelper) {
             *     // do something.
             * }
             * </pre>
             */
            this.handleMixed(bean, methods, group);

            /*
             * <pre>
             * {@literal @}AutoJob(...)
             * public void executeXxx() {
             *     // do something.
             * }
             * </pre>
             */
            this.handleProxy(bean, methods, group);
        }
    }

    private void handleProxy(Object bean, Set<String> methods, GroupDTO group) {
        Map<Method, AutoJob> singleAutoJobMethods = MethodIntrospector.selectMethods(bean.getClass(),
            new MethodIntrospector.MetadataLookup<AutoJob>() {
                @Override
                public AutoJob inspect(Method method) {
                    return AnnotatedElementUtils.findMergedAnnotation(method, AutoJob.class);
                }
            });

        for (Map.Entry<Method, AutoJob> kvs : singleAutoJobMethods.entrySet()) {
            Method executeMethod = kvs.getKey();
            String methodName = executeMethod.getName();
            if (methods.contains(methodName)) {
                continue;
            }

            methods.add(methodName);
            AutoJob autoJob = kvs.getValue();

            // @see com.xxl.job.admin.controller.JobInfoController#pageList
            List<JobDTO> jobs = this.groupJobs(group.getId(), autoJob.job().value(), (ctx) -> {
                ctx.add("jobDesc", autoJob.base().description());
                ctx.add("author", autoJob.base().author());
            });

            JobAddPayload payload = this.toJobAddPayload(group, autoJob);

            boolean matches = false;
            if (!CollectionUtils.isEmpty(jobs)) {
                boolean matched = jobs.stream()
                    .allMatch(it -> it.executorHandler().equals(autoJob.job().value()));
                if (matched) {
                    matches = true;
                }
            }

            if (!matches) {
                this.add(payload);
            }

            MethodJobHandler methodJobHandler = this.populateMethodJobHandler(bean, executeMethod, autoJob);
            XxlJobExecutor.registJobHandler(payload.executorHandler(), methodJobHandler);
        }
    }

    private MethodJobHandler populateMethodJobHandler(Object bean, Method executeMethod, AutoJob autoJob) {
        Class<?> clazz = bean.getClass();
        String methodName = executeMethod.getName();

        executeMethod.setAccessible(true);

        Method initMethod = null;
        Method destroyMethod = null;

        if (StringUtils.hasText(autoJob.job().init().trim())) {
            try {
                initMethod = clazz.getDeclaredMethod(autoJob.job().init());
                initMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(
                    "xxl-job method-jobhandler initMethod invalid, for[" + clazz + "#" + methodName + "] .");
            }
        }
        if (StringUtils.hasText(autoJob.job().destroy().trim())) {
            try {
                destroyMethod = clazz.getDeclaredMethod(autoJob.job().destroy());
                destroyMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(
                    "xxl-job method-jobhandler destroyMethod invalid, for[" + clazz + "#" + methodName + "] .");
            }
        }

        return new MethodJobHandler(bean, executeMethod, initMethod, destroyMethod);
    }

    private void handleMixed(Object bean, Set<String> methods, GroupDTO group) {
        Map<Method, XxlJob> mixedXxljobAnnotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
            new MethodIntrospector.MetadataLookup<XxlJob>() {
                @Override
                public XxlJob inspect(Method method) {
                    return AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class);
                }
            });

        for (Map.Entry<Method, XxlJob> kvs : mixedXxljobAnnotatedMethods.entrySet()) {
            Method executeMethod = kvs.getKey();
            XxlJob xxlJob = kvs.getValue();

            methods.add(executeMethod.getName());

            if (executeMethod.isAnnotationPresent(AutoJob.class)) {
                AutoJob autoJob = executeMethod.getAnnotation(AutoJob.class);
                List<JobDTO> jobs = this.groupJobs(group.getId(), xxlJob.value(), (ctx) -> {
                    ctx.add("jobDesc", autoJob.base().description());
                    ctx.add("author", autoJob.base().author());
                });
                if (!CollectionUtils.isEmpty(jobs)) {
                    boolean matched = jobs.stream()
                        .allMatch(it -> it.executorHandler().equals(xxlJob.value()));
                    if (matched) {
                        continue;
                    }
                }

                JobAddPayload payload = this.toJobAddPayload(group, xxlJob, autoJob);
                this.add(payload);
            }
        }
    }

    private JobAddPayload toJobAddPayload(GroupDTO group, XxlJob xxlJob, AutoJob autoJob) {
        return JobAddPayload.builder()
            .jobGroup(group.getId())
            .jobDesc(autoJob.base().description())
            .author(autoJob.base().author())
            .alarmEmail(autoJob.base().email())
            .scheduleType(autoJob.schedule().scheduleType().name())
            .scheduleConf(autoJob.schedule().cron())
            .fixRate(autoJob.schedule().fixRate())
            .glueType(autoJob.task().glueType().name())
            // ----------------------------------------------------------------
            .executorHandler(xxlJob.value())
            // ----------------------------------------------------------------
            .executorParam(autoJob.task().arguments())
            .executorRouteStrategy(autoJob.advanced().routeStrategy().name())
            .childJobId(autoJob.advanced().childJobId())
            .misfireStrategy(autoJob.advanced().misfireStrategy().name())
            .executorBlockStrategy(autoJob.advanced().blockStrategy().name())
            .executorTimeout(autoJob.advanced().executorTimeout())
            .executorFailRetryCount(autoJob.advanced().executorFailRetryCount())
            .triggerStatus(autoJob.triggerStatus())
            .build();
    }

    private JobAddPayload toJobAddPayload(GroupDTO group, AutoJob autoJob) {
        String handler = autoJob.task().handler();

        return JobAddPayload.builder()
            .jobGroup(group.getId())
            .jobDesc(autoJob.base().description())
            .author(autoJob.base().author())
            .alarmEmail(autoJob.base().email())
            .scheduleType(autoJob.schedule().scheduleType().name())
            .scheduleConf(autoJob.schedule().cron())
            .fixRate(autoJob.schedule().fixRate())
            .glueType(autoJob.task().glueType().name())
            // ----------------------------------------------------------------
            .executorHandler(StringUtils.hasText(handler) ? handler : autoJob.job().value())
            // ----------------------------------------------------------------
            .executorParam(autoJob.task().arguments())
            .executorRouteStrategy(autoJob.advanced().routeStrategy().name())
            .childJobId(autoJob.advanced().childJobId())
            .misfireStrategy(autoJob.advanced().misfireStrategy().name())
            .executorBlockStrategy(autoJob.advanced().blockStrategy().name())
            .executorTimeout(autoJob.advanced().executorTimeout())
            .executorFailRetryCount(autoJob.advanced().executorFailRetryCount())
            .triggerStatus(autoJob.triggerStatus())
            .build();
    }

    // ----------------------------------------------------------------

    private MultiValueMap<String, Object> populateAddFormDataBody(JobAddPayload payload) {
        Map<String, Object> objectMap = this.registerEngine().json().toMap(payload);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            multiValueMap.add(entry.getKey(), entry.getValue());
        }

        objectMap.clear();
        objectMap = null;

        return multiValueMap;
    }

    private MultiValueMap<String, Object> populatePageFormDataBody(Integer groupId, String executorHandler) {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>(4);
        multiValueMap.add("jobGroup", groupId);
        multiValueMap.add("executorHandler", executorHandler);
        multiValueMap.add("triggerStatus", -1);

        return multiValueMap;
    }
}
