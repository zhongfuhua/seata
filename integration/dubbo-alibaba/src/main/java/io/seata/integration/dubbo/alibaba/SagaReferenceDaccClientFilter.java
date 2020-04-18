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
package io.seata.integration.dubbo.alibaba;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import io.seata.common.util.DaccUtils;
import io.seata.core.context.RootContext;
import io.seata.core.model.BranchType;
import io.seata.rm.saga.api.BusinessActionContext;
import io.seata.rm.saga.interceptor.ActionInterceptorHandler;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Transaction propagation filter.
 *
 * @author sharajava
 */
@Activate(group = {Constants.CONSUMER }, order = 95)
public class SagaReferenceDaccClientFilter implements Filter {
    
    private static final Logger      LOGGER                        = LoggerFactory.getLogger(SagaReferenceDaccClientFilter.class);
    
    private ActionInterceptorHandler actionInterceptorHandler      = new ActionInterceptorHandler();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            if (!RootContext.inGlobalTransactionSagaTcc()) {
                return invoker.invoke(invocation);
            }

            RpcInvocation rpcInvocation = (RpcInvocation) invocation;
            String methodName = rpcInvocation.getMethodName();
            Object[] arguments = rpcInvocation.getArguments();
            String serviceInterface = invoker.getInterface().getName();

            if (DaccUtils.isDaccInvoke(serviceInterface, methodName)) {
                String xid = RootContext.getXID();
                String xidType = String.format("%s_%s", xid, BranchType.SAGA_ANNOTATION.name());
                RootContext.unbind();
                RootContext.bindFilterType(xidType);
                try {
                    Map<String, Object> ret = actionInterceptorHandler.proceedDacc(arguments[0], xid);
                    BusinessActionContext actionContext = (BusinessActionContext)ret.get(io.seata.common.Constants.SAGA_ACTION_CONTEXT);
                    RpcContext.getContext().setAttachment(RootContext.KEY_BRANCH_ID, String.valueOf(actionContext.getBranchId()));
                } finally {
                    RootContext.bind(xid);
                    RootContext.unbindFilterType();
                }
            }
        } catch (Throwable e) {
            LOGGER.error("process dubbo dacc client filter exception:{}", e.getMessage(), e);
        }
        return invoker.invoke(invocation);
    }
}
