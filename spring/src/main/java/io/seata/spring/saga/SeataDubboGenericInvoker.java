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
package io.seata.spring.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;

import io.seata.rm.saga.api.DubboInvokerParam;
import io.seata.rm.saga.api.GenericInvoker;

public class SeataDubboGenericInvoker implements GenericInvoker {
    
    public static final Logger logger = LoggerFactory.getLogger(SeataDubboGenericInvoker.class);

    private String             applicationName;

    private String             referenceAddress;

    public SeataDubboGenericInvoker(){

    }

    public SeataDubboGenericInvoker(String applicationName, String referenceAddress){
        this.applicationName = applicationName;
        this.referenceAddress = referenceAddress;
    }

    @Override
    public Object invoke(DubboInvokerParam param) throws Exception {
        ApplicationConfig application = new ApplicationConfig();
        ReferenceConfig reference = new ReferenceConfig();
        application.setName(applicationName);
        reference.setApplication(application);
        if (referenceAddress != null) {
            RegistryConfig config = new RegistryConfig();
            config.setAddress(referenceAddress);
            reference.setRegistry(config);
        }

        logger.debug("dubbo generic service invoke {}", param);
        reference.setInterface(param.getClassName());
        reference.setVersion(param.getVersion());
        if(param.getRetrys() != null){
            reference.setRetries(param.getRetrys());
        }
        if(param.getTimeout() != null){
            reference.setTimeout(param.getTimeout());
        }
        reference.setGeneric(true);

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = (GenericService) cache.get(reference);
        Object object = genericService.$invoke(param.getMethod(), param.getParamType(), param.getParam());
        logger.debug("invoke success");
        return object;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getReferenceAddress() {
        return referenceAddress;
    }

    public void setReferenceAddress(String referenceAddress) {
        this.referenceAddress = referenceAddress;
    }
}
