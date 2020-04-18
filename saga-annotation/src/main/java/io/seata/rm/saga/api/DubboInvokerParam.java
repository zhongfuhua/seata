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
package io.seata.rm.saga.api;

/**
 * <P>DubboInvokerParam</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2020/4/17 17:47
 * @since
 */
public class DubboInvokerParam {
    
    private String   className;
    private String   method;
    private String   version;
    private Integer  timeout;
    private Integer  retrys;
    private String[] paramType;
    private Object[] param;
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public Integer getTimeout() {
        return timeout;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    public Integer getRetrys() {
        return retrys;
    }
    
    public void setRetrys(int retrys) {
        this.retrys = retrys;
    }
    
    public Object[] getParam() {
        return param;
    }
    
    public void setParam(Object[] param) {
        this.param = param;
    }
    
    public String[] getParamType() {
        return paramType;
    }
    
    public void setParamType(String[] paramType) {
        this.paramType = paramType;
    }
    
    @Override
    public String toString() {
        return "DubboInvokerParam{" + "className='" + className + '\'' + ", method='" + method + '\'' + ", version='" + version + '\'' + ", timeout="
            + timeout + ", retrys=" + retrys + '}';
    }
}
