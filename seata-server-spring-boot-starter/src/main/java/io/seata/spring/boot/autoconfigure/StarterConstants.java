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
package io.seata.spring.boot.autoconfigure;

import io.seata.spring.boot.autoconfigure.properties.config.ConfigProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.ConfigFileProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.RecoveryProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.ServiceProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.StoreProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.TransactionProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.TransportProperties;
import io.seata.spring.boot.autoconfigure.properties.config.file.TransportThreadFactoryProperties;
import io.seata.spring.boot.autoconfigure.properties.config.registry.ConfigApolloProperties;
import io.seata.spring.boot.autoconfigure.properties.config.registry.ConfigConsulProperties;
import io.seata.spring.boot.autoconfigure.properties.config.registry.ConfigEtcd3Properties;
import io.seata.spring.boot.autoconfigure.properties.config.registry.ConfigNacosProperties;
import io.seata.spring.boot.autoconfigure.properties.config.registry.ConfigZooKeeperProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.RegistryProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.file.RegistryFileProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistryConsulProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistryEtcd3Properties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistryEurekaProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistryNacosProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistryRedisProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistrySofaProperties;
import io.seata.spring.boot.autoconfigure.properties.discovery.registry.RegistryZooKeeperProperties;

import java.util.HashMap;

/**
 * <P>StarterConstants</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 14:27
 * @since
 */
public class StarterConstants {

  private static final int MAP_CAPACITY = 64;
  public static final String SEATA_PREFIX = "seata";

  public static final String TRANSPORT_PREFIX = SEATA_PREFIX + ".transport";
  public static final String TRANSPORT_THREAD_FACTORY_PREFIX = TRANSPORT_PREFIX + ".thread-factory";
  public static final String STORE_PREFIX = SEATA_PREFIX + ".store";
  public static final String SERVICE_PREFIX = SEATA_PREFIX + ".server";
  public static final String RECOVERY_PREFIX = SEATA_PREFIX + ".recovery";
  public static final String TRANSACTION_PREFIX = SEATA_PREFIX + ".transaction";
  public static final String METRICS_PREFIX = SEATA_PREFIX + ".metrics";

  public static final String REGISTRY_PREFIX = SEATA_PREFIX + ".registry";
  public static final String REGISTRY_NACOS_PREFIX = REGISTRY_PREFIX + ".nacos";
  public static final String REGISTRY_EUREKA_PREFIX = REGISTRY_PREFIX + ".eureka";
  public static final String REGISTRY_REDIS_PREFIX = REGISTRY_PREFIX + ".redis";
  public static final String REGISTRY_ZK_PREFIX = REGISTRY_PREFIX + ".zk";
  public static final String REGISTRY_CONSUL_PREFIX = REGISTRY_PREFIX + ".consul";
  public static final String REGISTRY_ETCD3_PREFIX = REGISTRY_PREFIX + ".etcd3";
  public static final String REGISTRY_SOFA_PREFIX = REGISTRY_PREFIX + ".sofa";
  public static final String REGISTRY_FILE_PREFIX = REGISTRY_PREFIX + ".file";

  public static final String CONFIG_PREFIX = SEATA_PREFIX + ".config";
  public static final String CONFIG_NACOS_PREFIX = CONFIG_PREFIX + ".nacos";
  public static final String CONFIG_CONSUL_PREFIX = CONFIG_PREFIX + ".consul";
  public static final String CONFIG_ETCD3_PREFIX = CONFIG_PREFIX + ".etcd3";
  public static final String CONFIG_APOLLO_PREFIX = CONFIG_PREFIX + ".apollo";
  public static final String CONFIG_ZK_PREFIX = CONFIG_PREFIX + ".zk";
  public static final String CONFIG_FILE_PREFIX = CONFIG_PREFIX + ".file";


  /**
   * The following special keys need to be normalized.
   */
  public static final String SPECIAL_KEY_METRICS = "metrics.";
  public static final String NORMALIZED_KEY_METRICS = "metrics.";
  public static final String SPECIAL_KEY_RECOVERY = "recovery.";
  public static final String NORMALIZED_KEY_RECOVERY = "recovery.";
  public static final String SPECIAL_KEY_SERVICE = "service.";
  public static final String NORMALIZED_KEY_SERVICE = "service.";
  public static final String SPECIAL_KEY_STORE = "store.";
  public static final String NORMALIZED_KEY_STORE = "store.";
  public static final String SPECIAL_KEY_TRANSACTION = "transaction.";
  public static final String NORMALIZED_KEY_TRANSACTION = "transaction.";
  public static final String SPECIAL_KEY_TRANSPORT_THREAD_FACTORY = "transport.thread-factory.";
  public static final String NORMALIZED_KEY_TRANSPORT_THREAD_FACTORY = "transport.thread-factory.";

  public static final String SPECIAL_KEY_REGISTRY_ZK = "registry.zk.";
  public static final String NORMALIZED_KEY_REGISTRY_ZK = "registry.zk.";
  public static final String SPECIAL_KEY_CONFIG_ZK = "config.zk.";
  public static final String NORMALIZED_KEY_CONFIG_ZK = "config.zk.";
  public static final String SPECIAL_KEY_CONFIG_APOLLO = "config.apollo.";
  public static final String NORMALIZED_KEY_CONFIG_APOLLO = "config.apollo.";


  public static final HashMap<String, Class> PROPERTY_MAP = new HashMap<String, Class>(
      MAP_CAPACITY) {
    private static final long serialVersionUID = -8902807645596274597L;

    {
      put(METRICS_PREFIX, StoreProperties.class);
      put(RECOVERY_PREFIX, RecoveryProperties.class);
      put(SERVICE_PREFIX, ServiceProperties.class);
      put(STORE_PREFIX, StoreProperties.class);
      put(TRANSPORT_THREAD_FACTORY_PREFIX, TransportThreadFactoryProperties.class);
      put(TRANSACTION_PREFIX, TransactionProperties.class);
      put(TRANSPORT_PREFIX, TransportProperties.class);

      put(CONFIG_PREFIX, ConfigProperties.class);
      put(CONFIG_FILE_PREFIX, ConfigFileProperties.class);
      put(REGISTRY_PREFIX, RegistryProperties.class);
      put(REGISTRY_FILE_PREFIX, RegistryFileProperties.class);

      put(CONFIG_NACOS_PREFIX, ConfigNacosProperties.class);
      put(CONFIG_CONSUL_PREFIX, ConfigConsulProperties.class);
      put(CONFIG_ZK_PREFIX, ConfigZooKeeperProperties.class);
      put(CONFIG_APOLLO_PREFIX, ConfigApolloProperties.class);
      put(CONFIG_ETCD3_PREFIX, ConfigEtcd3Properties.class);

      put(REGISTRY_CONSUL_PREFIX, RegistryConsulProperties.class);
      put(REGISTRY_ETCD3_PREFIX, RegistryEtcd3Properties.class);
      put(REGISTRY_EUREKA_PREFIX, RegistryEurekaProperties.class);
      put(REGISTRY_NACOS_PREFIX, RegistryNacosProperties.class);
      put(REGISTRY_REDIS_PREFIX, RegistryRedisProperties.class);
      put(REGISTRY_SOFA_PREFIX, RegistrySofaProperties.class);
      put(REGISTRY_ZK_PREFIX, RegistryZooKeeperProperties.class);
    }

  };

}
