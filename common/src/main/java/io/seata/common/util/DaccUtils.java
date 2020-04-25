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
package io.seata.common.util;

/**
 * <P>Copyright (C), 2003-2019, 浩鲸云计算科技股份有限公司</P>
 * <P>描述说明：</P>
 *
 * @author ZHONGFUHUA-PC
 * @date 2019/10/12 15:54
 * @since JDK1.8
 */
public class DaccUtils {

    private static final String DACC_CLIENT_CLASS  = "com.gigrt.cxm.component.dacc.intf.IDaccClient";

    private static final String DACC_CLIENT_METHOD  = "invoke";

    private static final String DACC_CALLBACK_CLASS  = "com.gigrt.cxm.component.dacc.intf.ICallbackService";
    
    private static final String DACC_CALLBACK_METHOD = "callback";
    
    public static boolean isDaccCallback(String serviceInterface, String methodName) throws NoSuchMethodException, ClassNotFoundException {
        if (DACC_CALLBACK_METHOD.equals(methodName)) {
            Class serviceInterfaceClass = Class.forName(serviceInterface);
            if (DACC_CALLBACK_CLASS.equals(serviceInterface)) {
                return true;
            }
            
            Class<?>[] serviceParentInterfaceClasses = serviceInterfaceClass.getInterfaces();
            if (serviceParentInterfaceClasses == null || serviceParentInterfaceClasses.length <= 0) {
                return false;
            }
            
            for (Class serviceParentInterfaceClass : serviceParentInterfaceClasses) {
                if (DACC_CALLBACK_CLASS.equals(serviceParentInterfaceClass.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static  boolean isDaccInvoke(String serviceInterface, String methodName) throws NoSuchMethodException {
        return DACC_CLIENT_CLASS.equals(serviceInterface) && DACC_CLIENT_METHOD.equals(methodName);
    }

    public static  boolean isDaccClient(String serviceInterface) throws NoSuchMethodException {
        return DACC_CLIENT_CLASS.equals(serviceInterface);
    }
}
