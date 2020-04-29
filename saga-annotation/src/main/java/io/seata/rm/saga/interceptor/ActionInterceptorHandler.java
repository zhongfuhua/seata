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
package io.seata.rm.saga.interceptor;

import com.alibaba.fastjson.JSON;
import com.gigrt.cxm.component.dacc.intf.model.DaccReq;
import io.seata.common.Constants;
import io.seata.common.exception.FrameworkException;
import io.seata.common.executor.Callback;
import io.seata.common.util.DaccUtils;
import io.seata.common.util.NetUtil;
import io.seata.common.util.StringUtils;
import io.seata.core.model.BranchType;
import io.seata.rm.DefaultResourceManager;
import io.seata.rm.saga.api.BusinessActionContext;
import io.seata.rm.saga.api.BusinessActionContextParameter;
import io.seata.rm.saga.api.SagaCompensiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler the SAGA_ANNOTATION Participant Aspect : Setting Context, Creating Branch Record
 *
 * @author zhangsen
 */
public class ActionInterceptorHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionInterceptorHandler.class);

    /**
     * Handler the SAGA_ANNOTATION Aspect
     *
     * @param method         the method
     * @param arguments      the arguments
     * @param businessAction the business action
     * @param targetCallback the target callback
     * @return map map
     * @throws Throwable the throwable
     */
    public Map<String, Object> proceed(Method method, Object[] arguments, String xid, SagaCompensiable businessAction,
                                       Callback<Object> targetCallback) throws Throwable {
        return proceed(businessAction.name(), method, arguments, xid, businessAction, targetCallback);
    }

    /**
     * Handler the SAGA_ANNOTATION Aspect
     *
     * @param method         the method
     * @param arguments      the arguments
     * @param businessAction the business action
     * @param targetCallback the target callback
     * @return map map
     * @throws Throwable the throwable
     */
    public Map<String, Object> proceed(String actionName, Method method, Object[] arguments, String xid, SagaCompensiable businessAction,
        Callback<Object> targetCallback)
        throws Throwable {
        Map<String, Object> ret = new HashMap<>(16);
        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        actionContext.setActionName(actionName);

        //Creating Branch Record
        String branchId = doSagaActionLogStore(method, arguments, businessAction, actionContext);
        actionContext.setBranchId(branchId);
        
        //set the parameter whose type is BusinessActionContext
        Class<?>[] types = method.getParameterTypes();
        int argIndex = 0;
        for (Class<?> cls : types) {
            if (cls.getName().equals(BusinessActionContext.class.getName())) {
                arguments[argIndex] = actionContext;
                break;
            }
            argIndex++;
        }
        //the final parameters of the try method
        ret.put(Constants.SAGA_METHOD_ARGUMENTS, arguments);
        //the final result
        ret.put(Constants.SAGA_METHOD_RESULT, targetCallback.execute());
        return ret;
    }
    
    /**
     * Handler the SAGA_ANNOTATION Aspect
     *
     * @param daccParams
     *
     * @param xid
     *
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws Throwable the throwable
     */
    public Map<String, Object> proceedDacc(Object daccParams, String xid) throws Throwable {
        Map<String, Object> ret = new HashMap<>(16);
        if(daccParams == null || !(daccParams instanceof DaccReq)){
            throw  new Exception("the dacc client invoke method params is illegal.");
        }
        DaccReq daccReq = (DaccReq) daccParams;
        String actionName = String.format("%s%s:%s", DaccUtils.DACC_BRANCH, daccReq.getCallServiceName(), daccReq.getCallServiceVersion());
        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        actionContext.setActionName(actionName);

        //Creating Branch Record
        doSagaActionLogStoreDacc(daccReq, actionContext);
        ret.put(Constants.SAGA_ACTION_CONTEXT, actionContext);
        return ret;
    }
    
    /**
     * Creating Branch Record
     *
     * @param method         the method
     * @param arguments      the arguments
     * @param businessAction the business action
     * @param actionContext  the action context
     * @return the string
     */
    protected String doSagaActionLogStore(Method method, Object[] arguments, SagaCompensiable businessAction, BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        //
        Map<String, Object> context = fetchActionRequestContext(method, arguments);
        context.put(Constants.ACTION_START_TIME, System.currentTimeMillis());
        
        //init business context
        initBusinessContext(context, method, businessAction, actionContext);
        //Init running environment context
        initFrameworkContext(context);
        actionContext.setActionContext(context);
        
        //init applicationData
        Map<String, Object> applicationContext = new HashMap<>(4);
        applicationContext.put(Constants.SAGA_ACTION_CONTEXT, context);
        String applicationContextStr = JSON.toJSONString(applicationContext);
        try {
            //registry branch record
            Long branchId = DefaultResourceManager.get().branchRegister(BranchType.SAGA_ANNOTATION, actionContext.getActionName(), null, xid, applicationContextStr, null);
            LOGGER.info("saga branch register xid:{} branchId:{}", xid, branchId);
            return String.valueOf(branchId);
        } catch (Throwable t) {
            String msg = "SAGA_ANNOTATION branch Register error, xid:" + xid;
            LOGGER.error("SAGA_ANNOTATION branch Register error, xid:{} actionName=[{}] applicationContextStr=[{}] ", xid, actionContext.getActionName(), applicationContextStr, t);
            throw new FrameworkException(t, msg);
        }
    }


    /**
     * Creating Branch Record
     *
     * @param daccReq        the dacc request
     * @param actionContext  the action context
     * @return the string
     */
    protected void doSagaActionLogStoreDacc(DaccReq daccReq, BusinessActionContext actionContext) {
        String actionName = actionContext.getActionName();
        String xid = actionContext.getXid();

        Map<String, Object> context = new HashMap<>(8);
        context.put(Constants.SAGA_DACC_METHOD_PARAMS, daccReq.getParam());
        context.put(Constants.SAGA_DACC_METHOD_BUSINESS_ID, daccReq.getBusinessId());
        context.put(Constants.ACTION_START_TIME, System.currentTimeMillis());
        context.put(Constants.ACTION_NAME, actionName);

        //Init running environment context
        initFrameworkContext(context);

        actionContext.setActionContext(context);

        //init applicationData
        Map<String, Object> applicationContext = new HashMap<String, Object>(4);
        applicationContext.put(Constants.SAGA_ACTION_CONTEXT, context);
        String applicationContextStr = JSON.toJSONString(applicationContext);
        try {
            //registry branch record
            Long branchId = DefaultResourceManager.get().branchRegister(BranchType.SAGA_ANNOTATION, actionName, null, xid, applicationContextStr, null);
            actionContext.setBranchId(branchId);
            LOGGER.info("saga branch register xid:{} branchId:{}", xid, branchId);
        } catch (Throwable t) {
            String msg = "SAGA_ANNOTATION branch Register error, xid:" + xid;
            LOGGER.error(msg, t);
            throw new FrameworkException(t, msg);
        }
    }

    /**
     * Init running environment context
     *
     * @param context the context
     */
    protected void initFrameworkContext(Map<String, Object> context) {
        try {
            context.put(Constants.HOST_NAME, NetUtil.getLocalIp());
        } catch (Throwable t) {
            LOGGER.warn("getLocalIP error", t);
        }
    }
    
    /**
     * Init business context
     *
     * @param context        the context
     * @param method         the method
     * @param businessAction the business action
     */
    protected void initBusinessContext(Map<String, Object> context, Method method, SagaCompensiable businessAction, BusinessActionContext actionContext) {
        if (method != null) {
            //the phase one method name
            context.put(Constants.COMMIT_METHOD, method.getName());
        }

        if (businessAction != null) {
            //the phase two method name
            context.put(Constants.ROLLBACK_METHOD, businessAction.rollbackMethod());
            context.put(Constants.ACTION_NAME, StringUtils.isBlank(businessAction.name()) ? actionContext : businessAction.name());
        }
    }
    
    /**
     * Extracting context data from parameters, add them to the context
     *
     * @param method    the method
     * @param arguments the arguments
     * @return map map
     */
    protected Map<String, Object> fetchActionRequestContext(Method method, Object[] arguments) {
        Map<String, Object> context = new HashMap<String, Object>(8);
        
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                if (parameterAnnotations[i][j] instanceof BusinessActionContextParameter) {
                    BusinessActionContextParameter param = (BusinessActionContextParameter) parameterAnnotations[i][j];
                    if (null == arguments[i]) {
                        throw new IllegalArgumentException("@BusinessActionContextParameter 's params can not null");
                    }
                    Object paramObject = arguments[i];
                    int index = param.index();
                    //List, get by index
                    if (index >= 0) {
                        Object targetParam = ((List<Object>) paramObject).get(index);
                        if (param.isParamInProperty()) {
                            context.putAll(ActionContextUtil.fetchContextFromObject(targetParam));
                        } else {
                            context.put(param.paramName(), targetParam);
                        }
                    } else {
                        if (param.isParamInProperty()) {
                            context.putAll(ActionContextUtil.fetchContextFromObject(paramObject));
                        } else {
                            context.put(param.paramName(), paramObject);
                        }
                    }
                }
            }
        }
        return context;
    }
    
}
