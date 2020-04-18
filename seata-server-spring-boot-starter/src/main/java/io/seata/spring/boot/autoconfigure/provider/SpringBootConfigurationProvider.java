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
package io.seata.spring.boot.autoconfigure.provider;

import io.seata.common.util.DurationUtil;
import io.seata.config.Configuration;
import io.seata.config.ExtConfigurationProvider;
import io.seata.spring.boot.autoconfigure.StarterConstants;
import io.seata.spring.boot.autoconfigure.util.ApplicationContextUtils;
import io.seata.spring.boot.autoconfigure.util.StringFormatUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_CONFIG_APOLLO;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_CONFIG_ZK;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_METRICS;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_RECOVERY;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_REGISTRY_ZK;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_SERVICE;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_STORE;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_TRANSACTION;
import static io.seata.spring.boot.autoconfigure.StarterConstants.NORMALIZED_KEY_TRANSPORT_THREAD_FACTORY;
import static io.seata.spring.boot.autoconfigure.StarterConstants.PROPERTY_MAP;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_CONFIG_APOLLO;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_CONFIG_ZK;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_METRICS;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_RECOVERY;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_REGISTRY_ZK;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_SERVICE;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_STORE;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_TRANSACTION;
import static io.seata.spring.boot.autoconfigure.StarterConstants.SPECIAL_KEY_TRANSPORT_THREAD_FACTORY;


/**
 * <P>SpringBootConfigurationProvider</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 14:31
 * @since
 */
public class SpringBootConfigurationProvider implements ExtConfigurationProvider {

    private static final String INTERCEPT_METHOD_PREFIX = "get";

    @Override
    public Configuration provide(Configuration originalConfiguration) {
        return (Configuration) Enhancer.create(originalConfiguration.getClass(),
                (MethodInterceptor) (proxy, method, args, methodProxy) -> {
                    if (method.getName().startsWith(INTERCEPT_METHOD_PREFIX) && args.length > 0) {
                        Object result = null;
                        String rawDataId = (String) args[0];
                        if (args.length == 1) {
                            result = get(convertDataId(rawDataId));
                        } else if (args.length == 2) {
                            result = get(convertDataId(rawDataId), args[1]);
                        } else if (args.length == 3) {
                            result = get(convertDataId(rawDataId), args[1], (Long) args[2]);
                        }
                        if (null != result) {
                            if("getDuration".equals(method.getName())){
                                return DurationUtil.parse(String.valueOf(result));
                            }
                            return result;
                        }
                    }
                    return method.invoke(originalConfiguration, args);
                });
    }

    private Object get(String dataId, Object defaultValue, long timeoutMills)
            throws IllegalAccessException {
        return get(dataId, defaultValue);

    }

    private Object get(String dataId, Object defaultValue) throws IllegalAccessException {
        Object result = get(dataId);
        if (null == result) {
            return defaultValue;
        }
        return result;
    }

    private Object get(String dataId) throws IllegalAccessException {
        String propertySuffix = getPropertySuffix(dataId);
        Class propertyClass = getPropertyClass(getPropertyPrefix(dataId));
        if (null != propertyClass) {
            Object propertyObject = ApplicationContextUtils.getBean(propertyClass);
            Optional<Field> fieldOptional = Stream.of(propertyObject.getClass().getDeclaredFields())
                    .filter(f -> f.getName().equalsIgnoreCase(propertySuffix)).findAny();
            if (fieldOptional.isPresent()) {
                Field field = fieldOptional.get();
                field.setAccessible(true);
                return field.get(propertyObject);
            }
        }
        return null;
    }

    /**
     * convert data id
     *
     * @return dataId
     */
    private String convertDataId(String rawDataId) {
        if (rawDataId.startsWith(SPECIAL_KEY_STORE)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_STORE);
            return StarterConstants.STORE_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_RECOVERY)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_RECOVERY);
            return StarterConstants.RECOVERY_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_SERVICE)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_SERVICE);
            return StarterConstants.SERVICE_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_METRICS)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_METRICS);
            return StarterConstants.METRICS_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_TRANSACTION)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_TRANSACTION);
            return StarterConstants.TRANSACTION_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_TRANSPORT_THREAD_FACTORY)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_TRANSPORT_THREAD_FACTORY);
            return StarterConstants.TRANSPORT_THREAD_FACTORY_PREFIX + "." + StringFormatUtils
                    .minusToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_REGISTRY_ZK)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_REGISTRY_ZK);
            return StarterConstants.REGISTRY_ZK_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_CONFIG_ZK)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_CONFIG_ZK);
            return StarterConstants.CONFIG_ZK_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        if (rawDataId.startsWith(SPECIAL_KEY_CONFIG_APOLLO)) {
            String suffix = StringUtils.removeStart(rawDataId, NORMALIZED_KEY_CONFIG_APOLLO);
            return StarterConstants.CONFIG_APOLLO_PREFIX + "." + StringFormatUtils.dotToCamel(suffix);
        }

        return StarterConstants.SEATA_PREFIX + "." + rawDataId;
    }

    /**
     * Get property prefix
     *
     * @return propertyPrefix
     */
    private String getPropertyPrefix(String dataId) {
        return StringFormatUtils.underlineToCamel(
                StringFormatUtils.minusToCamel(StringUtils.substringBeforeLast(dataId, ".")));
    }

    /**
     * Get property suffix
     *
     * @return propertySuffix
     */
    private String getPropertySuffix(String dataId) {
        return StringFormatUtils.underlineToCamel(
                StringFormatUtils.minusToCamel(StringUtils.substringAfterLast(dataId, ".")));
    }

    /**
     * Get property class
     *
     * @return propertyClass
     */
    private Class getPropertyClass(String propertyPrefix) {
        Optional<Map.Entry<String, Class>> entry = PROPERTY_MAP.entrySet().stream()
                .filter(e -> propertyPrefix.equals(e.getKey())).findAny();
        return entry.map(Map.Entry::getValue).orElse(null);
    }
}
