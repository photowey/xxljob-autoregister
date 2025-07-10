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
package io.github.photowey.xxljob.autoregister.lock.distributed;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

import org.springframework.core.Ordered;

/**
 * {@code DistributedLock}.
 *
 * @author photowey
 * @version 3.1.1.1.0.0
 * @since 2025/07/09
 */
public interface DistributedLock extends Ordered {

    /**
     * Acquire {@link Lock} instance.
     *
     * @param lockKey the Lock key.
     * @return {@link Lock}
     */
    Lock obtain(String lockKey);

    default void run(String lockKey, Runnable task) {
        Lock lock = this.obtain(lockKey);

        lock.lock();
        try {
            task.run();
        } finally {
            lock.unlock();
        }
    }

    default <T> T call(String lockKey, Callable<T> task) {
        Lock lock = this.obtain(lockKey);

        lock.lock();
        try {
            return task.call();
        } catch (Exception e) {
            if (e instanceof RuntimeException re) {
                throw re;
            }

            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
