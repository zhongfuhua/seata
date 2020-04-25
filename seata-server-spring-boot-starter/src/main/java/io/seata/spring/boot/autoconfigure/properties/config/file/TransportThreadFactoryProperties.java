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

import static io.seata.spring.boot.autoconfigure.StarterConstants.TRANSPORT_THREAD_FACTORY_PREFIX;

/**
 * <P>ThreadFactoryProperties</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 15:21
 * @since
 */
@Component
@ConfigurationProperties(prefix = TRANSPORT_THREAD_FACTORY_PREFIX)
public class TransportThreadFactoryProperties {

  private String bossThreadPrefix = "NettyBoss";
  private String workerThreadPrefix = "NettyServerNIOWorker";
  private String serverExecutorThreadPrefix = "NettyServerBizHandler";
  private boolean shareBossWorker = false;
  private String clientSelectorThreadPrefix = "NettyClientSelector";
  private int clientSelectorThreadSize = 1;
  private String clientWorkerThreadPrefix = "NettyClientWorkerThread";
  /**
   * netty boss thread size,will not be used for UDT
   */
  private int bossThreadSize = 1;
  /**
   * auto default pin or 8
   */
  private int workerThreadSize = 8;

  public String getBossThreadPrefix() {
    return bossThreadPrefix;
  }

  public TransportThreadFactoryProperties setBossThreadPrefix(String bossThreadPrefix) {
    this.bossThreadPrefix = bossThreadPrefix;
    return this;
  }

  public String getWorkerThreadPrefix() {
    return workerThreadPrefix;
  }

  public TransportThreadFactoryProperties setWorkerThreadPrefix(String workerThreadPrefix) {
    this.workerThreadPrefix = workerThreadPrefix;
    return this;
  }

  public String getServerExecutorThreadPrefix() {
    return serverExecutorThreadPrefix;
  }

  public TransportThreadFactoryProperties setServerExecutorThreadPrefix(
      String serverExecutorThreadPrefix) {
    this.serverExecutorThreadPrefix = serverExecutorThreadPrefix;
    return this;
  }

  public boolean isShareBossWorker() {
    return shareBossWorker;
  }

  public TransportThreadFactoryProperties setShareBossWorker(boolean shareBossWorker) {
    this.shareBossWorker = shareBossWorker;
    return this;
  }

  public String getClientSelectorThreadPrefix() {
    return clientSelectorThreadPrefix;
  }

  public TransportThreadFactoryProperties setClientSelectorThreadPrefix(
      String clientSelectorThreadPrefix) {
    this.clientSelectorThreadPrefix = clientSelectorThreadPrefix;
    return this;
  }

  public String getClientWorkerThreadPrefix() {
    return clientWorkerThreadPrefix;
  }

  public TransportThreadFactoryProperties setClientWorkerThreadPrefix(
      String clientWorkerThreadPrefix) {
    this.clientWorkerThreadPrefix = clientWorkerThreadPrefix;
    return this;
  }

  public int getClientSelectorThreadSize() {
    return clientSelectorThreadSize;
  }

  public TransportThreadFactoryProperties setClientSelectorThreadSize(
      int clientSelectorThreadSize) {
    this.clientSelectorThreadSize = clientSelectorThreadSize;
    return this;
  }

  public int getBossThreadSize() {
    return bossThreadSize;
  }

  public TransportThreadFactoryProperties setBossThreadSize(int bossThreadSize) {
    this.bossThreadSize = bossThreadSize;
    return this;
  }

  public int getWorkerThreadSize() {
    return workerThreadSize;
  }

  public TransportThreadFactoryProperties setWorkerThreadSize(int workerThreadSize) {
    this.workerThreadSize = workerThreadSize;
    return this;
  }
}
