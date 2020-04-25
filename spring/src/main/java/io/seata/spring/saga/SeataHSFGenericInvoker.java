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

import com.alibaba.dubbo.rpc.service.GenericService;
import com.taobao.hsf.app.api.util.HSFApiConsumerBean;
import io.seata.rm.saga.api.DubboInvokerParam;
import io.seata.rm.saga.api.GenericInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeataHSFGenericInvoker implements GenericInvoker {
    
    public static final Logger logger = LoggerFactory.getLogger(SeataHSFGenericInvoker.class);
    
    /**
     * 范化调用dubbo
     *
     * @param param 调用基本参数
     */
    @Override
    public Object invoke(DubboInvokerParam param) throws Exception {
        logger.debug("hsf generic service invoke {}", param);
        HSFApiConsumerBean consumerBean = new HSFApiConsumerBean();
        consumerBean.setInterfaceName(param.getClassName());
        consumerBean.setVersion(param.getVersion());
        //consumerBean.setClientTimeout(param.getTimeout());
        //consumerBean.setRetries(param.getRetrys());
        consumerBean.setGeneric("true");
        consumerBean.init(true);
        //强制转换接口为 GenericService
        GenericService genericService = (GenericService) consumerBean.getObject();
        Object object = genericService.$invoke(param.getMethod(), param.getParamType(), param.getParam());
        logger.debug("invoke success");
        return object;
    }
    
}
