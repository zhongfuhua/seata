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
package io.seata.core.protocol.transaction;

import io.seata.core.protocol.MessageType;
import io.seata.core.rpc.RpcContext;

/**
 * The type Branch register request.
 *
 * @author sharajava
 */
public class BranchDaccCommitRequest extends AbstractTransactionRequestToTC {
    
    private String  xid;
    
    private Long    branchId;
    
    private boolean retrying;
    
    @Override
    public AbstractTransactionResponse handle(RpcContext rpcContext) {
        return handler.handle(this, rpcContext);
    }
    
    public String getXid() {
        return xid;
    }
    
    public void setXid(String xid) {
        this.xid = xid;
    }
    
    public Long getBranchId() {
        return branchId;
    }
    
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
    
    public boolean isRetrying() {
        return retrying;
    }
    
    public void setRetrying(boolean retrying) {
        this.retrying = retrying;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("xid=");
        result.append(xid);
        result.append(",");
        result.append("branchId=");
        result.append(branchId);
        result.append(",");
        result.append("retrying=");
        result.append(retrying);
        
        return result.toString();
    }

    @Override
    public short getTypeCode() {
        return MessageType.TYPE_BRANCH_DACC_COMMIT;
    }
}
