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

import com.gigrt.cxm.component.dacc.intf.model.DaccResp;
import io.seata.common.util.DaccUtils;
import io.seata.core.context.RootContext;
import io.seata.core.model.TransactionManager;
import io.seata.tm.TransactionManagerHolder;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Transaction propagation filter.
 *
 * @author sharajava
 */
@Activate(group = {Constants.CONSUMER, Constants.PROVIDER}, order = 101)
public class SagaReferenceDaccCallBackFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SagaReferenceDaccCallBackFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            if (!RootContext.inGlobalTransactionSagaTcc()) {
                return invoker.invoke(invocation);
            }

            String branchId = RpcContext.getContext().getAttachment(RootContext.KEY_BRANCH_ID);
            RpcInvocation rpcInvocation = (RpcInvocation) invocation;
            String methodName = rpcInvocation.getMethodName();
            Object[] arguments = rpcInvocation.getArguments();
            String serviceInterface = invoker.getInterface().getName();

            if (DaccUtils.isDaccCallback(serviceInterface, methodName)) {
                if (arguments[0] == null || !(arguments[0] instanceof DaccResp)) {
                    throw new Exception("the dacc callback method params is illegal.");
                }

                DaccResp daccResp = (DaccResp) arguments[0];
                TransactionManager transactionManager = TransactionManagerHolder.get();
                if (DaccResp.FAIL.equals(daccResp.getMessageStatus())) {
                    transactionManager.rollback(RootContext.getXID());
                } else {
                    transactionManager
                            .branchCommit(RootContext.getXID(), Long.valueOf(branchId), true);
                }
            }
        } catch (Throwable e) {
            LOGGER.error("process dubbo dacc callback filter exception:{}", e.getMessage(), e);
            throw new RpcException(e);
        }
        return invoker.invoke(invocation);
    }
}
