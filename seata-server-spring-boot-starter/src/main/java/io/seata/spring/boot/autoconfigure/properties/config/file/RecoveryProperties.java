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

import static io.seata.spring.boot.autoconfigure.StarterConstants.RECOVERY_PREFIX;

/**
 * <P>ClientProperties</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 15:17
 * @since
 */
@Component
@ConfigurationProperties(prefix = RECOVERY_PREFIX)
public class RecoveryProperties {

  /**
   * 二阶段提交未完成状态全局事务重试提交线程间隔时间(毫秒)
   */
  private long committingRetryPeriod = 1000;

  /**
   * 二阶段异步提交状态重试提交线程间隔时间(毫秒)
   */
  private long asynCommittingRetryPeriod = 1000;

  /**
   * 二阶段回滚状态重试回滚线程间隔时间(毫秒)
   */
  private long rollbackingRetryPeriod = 1000;

  /**
   * 超时状态检测重试线程间隔时间(毫秒)
   */
  private long timeoutRetryPeriod = 1000;

  public long getCommittingRetryPeriod() {
    return committingRetryPeriod;
  }

  public RecoveryProperties setCommittingRetryPeriod(long committingRetryPeriod) {
    this.committingRetryPeriod = committingRetryPeriod;
    return this;
  }

  public long getAsynCommittingRetryPeriod() {
    return asynCommittingRetryPeriod;
  }

  public RecoveryProperties setAsynCommittingRetryPeriod(long asynCommittingRetryPeriod) {
    this.asynCommittingRetryPeriod = asynCommittingRetryPeriod;
    return this;
  }

  public long getRollbackingRetryPeriod() {
    return rollbackingRetryPeriod;
  }

  public RecoveryProperties setRollbackingRetryPeriod(long rollbackingRetryPeriod) {
    this.rollbackingRetryPeriod = rollbackingRetryPeriod;
    return this;
  }

  public long getTimeoutRetryPeriod() {
    return timeoutRetryPeriod;
  }

  public RecoveryProperties setTimeoutRetryPeriod(long timeoutRetryPeriod) {
    this.timeoutRetryPeriod = timeoutRetryPeriod;
    return this;
  }
}
