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
package io.seata.serializer.seata.protocol.transaction;

import io.netty.buffer.ByteBuf;
import io.seata.core.model.GlobalStatus;
import io.seata.core.protocol.transaction.BranchDaccCommitResponse;
import java.nio.ByteBuffer;

/**
 * <P>BranchDaccCommitResponseCodec</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2020/4/28 15:32
 * @since 
 */
public class BranchDaccCommitResponseCodec extends AbstractTransactionResponseCodec {

    @Override
    public Class<?> getMessageClassType() {
        return BranchDaccCommitResponse.class;
    }

    @Override
    public <T> void encode(T t, ByteBuf in) {
        super.encode(t, in);
        BranchDaccCommitResponse branchDaccCommitResponse = (BranchDaccCommitResponse)t;
        GlobalStatus globalStatus = branchDaccCommitResponse.getGlobalStatus();
        in.writeByte(globalStatus.getCode());
    }

    @Override
    public <T> void decode(T t, ByteBuffer in) {
        super.decode(t, in);
        BranchDaccCommitResponse branchDaccCommitResponse = (BranchDaccCommitResponse)t;
        branchDaccCommitResponse.setGlobalStatus(GlobalStatus.get(in.get()));
    }
}
