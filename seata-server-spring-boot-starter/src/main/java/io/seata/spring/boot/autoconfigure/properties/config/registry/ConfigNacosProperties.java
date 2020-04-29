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

import static io.seata.spring.boot.autoconfigure.StarterConstants.CONFIG_NACOS_PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <P>ConfigNacosProperties</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 15:21
 * @since
 */
@Component
@ConfigurationProperties(prefix = CONFIG_NACOS_PREFIX)
public class ConfigNacosProperties {

  private String serverAddr = "localhost";
  private String namespace = "";

  public String getServerAddr() {
    return serverAddr;
  }

  public ConfigNacosProperties setServerAddr(String serverAddr) {
    this.serverAddr = serverAddr;
    return this;
  }

  public String getNamespace() {
    return namespace;
  }

  public ConfigNacosProperties setNamespace(String namespace) {
    this.namespace = namespace;
    return this;
  }
}