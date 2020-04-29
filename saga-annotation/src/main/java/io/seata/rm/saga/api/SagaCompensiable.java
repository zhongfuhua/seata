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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SAGA annotation, Define a SAGA interface，which added on the try method
 *
 * @author zhangsen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD })
@Inherited
public @interface SagaCompensiable {
    
    /**
     * SAGA bean name, must be unique
     *
     * @return the string
     */
    String name() default "";
    
    /**
     * rollback method name
     *
     * @return the string
     */
    String rollbackMethod() default "rollback";

    String rollbackRpcClass() default "";
    
    String rollbackRpcVersion() default "";

}