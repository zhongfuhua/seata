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
package io.seata.integration.dubbo;

import io.seata.core.context.RootContext;
import io.seata.core.model.BranchType;
import io.seata.rm.saga.api.SagaCompensiable;
import io.seata.rm.saga.interceptor.ActionInterceptorHandler;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

@Activate(group = {Constants.CONSUMER}, order = 95)
public class SagaReferenceAnnotationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SagaReferenceAnnotationFilter.class);

    private ActionInterceptorHandler actionInterceptorHandler = new ActionInterceptorHandler();

    private final static String DUBBO_GENERIC_SERVICE_INVOKE = "$invoke";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            if (!RootContext.inGlobalTransactionSagaTcc()) {
                return invoker.invoke(invocation);
            }

            RpcInvocation rpcInvocation = (RpcInvocation) invocation;
            String methodName = rpcInvocation.getMethodName();
            Class<?>[] parameterTypes = rpcInvocation.getParameterTypes();
            Object[] arguments = rpcInvocation.getArguments();
            Class interfaceClass = invoker.getInterface();
            if (DUBBO_GENERIC_SERVICE_INVOKE.equals(methodName)) {
                return invoker.invoke(invocation);
            }

            Method method = interfaceClass.getMethod(methodName, parameterTypes);

            SagaCompensiable businessAction = method.getAnnotation(SagaCompensiable.class);
            //commit method
            if (businessAction != null && StringUtils.isEmpty(RootContext.getXIDInterceptorType())) {
                String xid = RootContext.getXID();
                String xidType = String.format("%s_%s", xid, BranchType.TCC.name());
                RootContext.unbind();
                RootContext.bindFilterType(xidType);
                try {
                    Map<String, Object> ret = actionInterceptorHandler
                            .proceed(method, arguments, xid, businessAction,
                                    () -> invoker.invoke(invocation));
                    return (Result) ret.get(io.seata.common.Constants.SAGA_METHOD_RESULT);
                } finally {
                    RootContext.unbindFilterType();
                    RootContext.bind(xid);
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Saga filter registration transaction branch exception:{}", e.getMessage(), e);
            throw new RpcException(e);
        }
        return invoker.invoke(invocation);
    }
}
