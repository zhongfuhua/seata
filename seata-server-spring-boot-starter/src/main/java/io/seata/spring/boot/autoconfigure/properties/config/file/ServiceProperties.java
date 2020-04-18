/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.spring.boot.autoconfigure.properties.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static io.seata.spring.boot.autoconfigure.StarterConstants.SERVICE_PREFIX;

/**
 * <P>ServiceProperties</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 15:20
 * @since
 */
@Component
@ConfigurationProperties(prefix = SERVICE_PREFIX)
public class ServiceProperties {

    /**
     * 二阶段提交重试超时时长
     */
    private long maxCommitRetryTimeout = -1L;

    /**
     * 二阶段回滚重试超时时长
     */
    private long maxRollbackRetryTimeout = -1L;

    public long getMaxCommitRetryTimeout() {
        return maxCommitRetryTimeout;
    }

    public ServiceProperties setMaxCommitRetryTimeout(long maxCommitRetryTimeout) {
        this.maxCommitRetryTimeout = maxCommitRetryTimeout;
        return this;
    }

    public long getMaxRollbackRetryTimeout() {
        return maxRollbackRetryTimeout;
    }

    public ServiceProperties setMaxRollbackRetryTimeout(long maxRollbackRetryTimeout) {
        this.maxRollbackRetryTimeout = maxRollbackRetryTimeout;
        return this;
    }
}
