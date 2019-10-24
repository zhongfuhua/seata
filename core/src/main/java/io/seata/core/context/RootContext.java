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
package io.seata.core.context;

import io.seata.common.exception.ShouldNeverHappenException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Root context.
 *
 * @author jimin.jm @alibaba-inc.com
 */
public class RootContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootContext.class);

    /**
     * The constant KEY_XID.
     */
    public static final String KEY_XID = "TX_XID";

	public static final String  KEY_XID_FILTER_TYPE       = "TX_XID_FILTER_TYPE";
    
    public static final String  KEY_XID_INTTERCEPTOR_TYPE = "TX_XID_INTTERCEPTOR_TYPE";
    public static final String KEY_GLOBAL_LOCK_FLAG = "TX_LOCK";

    private static ContextCore CONTEXT_HOLDER = ContextCoreLoader.load();

    /**
     * Gets xid.
     *
     * @return the xid
     */
    public static String getXID() {
        String xid = CONTEXT_HOLDER.get(KEY_XID);
        if (StringUtils.isNotBlank(xid)) {
            return xid;
        }
        
        String xidType = CONTEXT_HOLDER.get(KEY_XID_FILTER_TYPE);
        if (StringUtils.isNotBlank(xidType)) {
            return xidType.split("_")[0];
        }

        xidType = CONTEXT_HOLDER.get(KEY_XID_INTTERCEPTOR_TYPE);
        if (StringUtils.isNotBlank(xidType)) {
            return xidType.split("_")[0];
        }

        return null;
    }
    
    /**
     * Gets xid.
     *
     * @return the xid
     */
    public static String getXIDFilterType() {
        return CONTEXT_HOLDER.get(KEY_XID_FILTER_TYPE);
    }

    /**
     * Gets xid.
     *
     * @return the xid
     */
    public static String getXIDInterceptorType() {
        return CONTEXT_HOLDER.get(KEY_XID_INTTERCEPTOR_TYPE);
    }
    
    /**
     * Bind.
     *
     * @param xid the xid
     */
    public static void bind(String xid) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("bind " + xid);
        }
        CONTEXT_HOLDER.put(KEY_XID, xid);
    }
    
    /**
     * Bind type
     *
     * @param xidType
     */
    public static void bindFilterType(String xidType) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("bind filter type {}", xidType);
        }
        CONTEXT_HOLDER.put(KEY_XID_FILTER_TYPE, xidType);
    }

    /**
     * Bind type
     *
     * @param xidType
     */
    public static void bindInterceptorType(String xidType) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("bind interceptor type {}", xidType);
        }
        CONTEXT_HOLDER.put(KEY_XID_INTTERCEPTOR_TYPE, xidType);
    }
    
    /**
     * declare local transactions will use global lock check for update/delete/insert/selectForUpdate SQL
     */
    public static void bindGlobalLockFlag() {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Local Transaction Global Lock support enabled");
        }

        //just put something not null
        CONTEXT_HOLDER.put(KEY_GLOBAL_LOCK_FLAG, KEY_GLOBAL_LOCK_FLAG);
    }

    /**
     * Unbind string.
     *
     * @return the string
     */
    public static String unbind() {
        String xid = CONTEXT_HOLDER.remove(KEY_XID);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unbind {} ", xid);
        }
        return xid;
    }

    /**
     * Unbind temporary string
     *
     * @return the string
     */
    public static String unbindFilterType() {
        String xidType = CONTEXT_HOLDER.remove(KEY_XID_FILTER_TYPE);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unbind filter type {}", xidType);
        }
        return xidType;
    }

    /**
     * Unbind temporary string
     *
     * @return the string
     */
    public static String unbindInterceptorType() {
        String xidType = CONTEXT_HOLDER.remove(KEY_XID_INTTERCEPTOR_TYPE);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unbind inteceptor type {}", xidType);
        }
        return xidType;
    }
    
    public static void unbindGlobalLockFlag() {
        String lockFlag = CONTEXT_HOLDER.remove(KEY_GLOBAL_LOCK_FLAG);
        if (LOGGER.isDebugEnabled() && lockFlag != null) {
            LOGGER.debug("unbind global lock flag");
        }
    }

    /**
     * In global transaction boolean.
     *
     * @return the boolean
     */
    public static boolean inGlobalTransaction() {
        return CONTEXT_HOLDER.get(KEY_XID) != null;
    }

    /**
     * requires global lock check
     *
     * @return
     */
    public static boolean requireGlobalLock() {
        return CONTEXT_HOLDER.get(KEY_GLOBAL_LOCK_FLAG) != null;
    }

    /**
     * Assert not in global transaction.
     */
    public static void assertNotInGlobalTransaction() {
        if (inGlobalTransaction()) {
            throw new ShouldNeverHappenException();
        }
    }
}
