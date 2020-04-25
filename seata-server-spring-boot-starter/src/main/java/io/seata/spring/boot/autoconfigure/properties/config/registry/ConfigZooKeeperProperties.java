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
package io.seata.spring.boot.autoconfigure.properties.config.registry;

import static io.seata.spring.boot.autoconfigure.StarterConstants.CONFIG_ZK_PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <P>ConfigZooKeeperProperties</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 15:22
 * @since
 */
@Component
@ConfigurationProperties(prefix = CONFIG_ZK_PREFIX)
public class ConfigZooKeeperProperties {

  private String serverAddr = "127.0.0.1:2181";
  private long sessionTimeout = 6000L;
  private long connectTimeout = 2000L;

  public String getServerAddr() {
    return serverAddr;
  }

  public ConfigZooKeeperProperties setServerAddr(String serverAddr) {
    this.serverAddr = serverAddr;
    return this;
  }

  public long getSessionTimeout() {
    return sessionTimeout;
  }

  public ConfigZooKeeperProperties setSessionTimeout(long sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
    return this;
  }

  public long getConnectTimeout() {
    return connectTimeout;
  }

  public ConfigZooKeeperProperties setConnectTimeout(long connectTimeout) {
    this.connectTimeout = connectTimeout;
    return this;
  }
}
