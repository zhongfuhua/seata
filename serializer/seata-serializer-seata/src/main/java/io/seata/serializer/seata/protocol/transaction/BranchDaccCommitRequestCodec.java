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
import io.seata.core.protocol.transaction.BranchDaccCommitRequest;
import java.nio.ByteBuffer;

/**
 * <P>BranchDaccCommitRequestCodec</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2020/4/28 15:33
 * @since 
 */
public class BranchDaccCommitRequestCodec extends AbstractTransactionRequestToTCCodec {

    @Override
    public Class<?> getMessageClassType() {
        return BranchDaccCommitRequest.class;
    }

    @Override
    public <T> void encode(T t, ByteBuf out) {
        BranchDaccCommitRequest branchDaccCommitRequest = (BranchDaccCommitRequest)t;
        String xid = branchDaccCommitRequest.getXid();
        Long branchId = branchDaccCommitRequest.getBranchId();
        boolean retrying = branchDaccCommitRequest.isRetrying();
        // 1. xid
        if (xid != null) {
            byte[] bs = xid.getBytes(UTF8);
            out.writeShort((short)bs.length);
            if (bs.length > 0) {
                out.writeBytes(bs);
            }
        } else {
            out.writeShort((short)0);
        }

        if (branchId != null) {
            byte[] bs = String.valueOf(branchId).getBytes(UTF8);
            out.writeShort((short)bs.length);
            if (bs.length > 0) {
                out.writeBytes(bs);
            }
        } else {
            out.writeShort((short)0);
        }

        byte[] bs = String.valueOf(retrying).getBytes(UTF8);
        out.writeShort((short)bs.length);
        if (bs.length > 0) {
            out.writeBytes(bs);
        }

    }

    @Override
    public <T> void decode(T t, ByteBuffer in) {
        BranchDaccCommitRequest branchDaccCommitRequest = (BranchDaccCommitRequest)t;

        short xidLen = in.getShort();
        if (xidLen > 0) {
            byte[] bs = new byte[xidLen];
            in.get(bs);
            branchDaccCommitRequest.setXid(new String(bs, UTF8));
        }

        short len = in.getShort();
        if (len > 0) {
            byte[] bs = new byte[len];
            in.get(bs);
            branchDaccCommitRequest.setBranchId(Long.valueOf(new String(bs, UTF8)));
        }
        short retryLen = in.getShort();
        byte[] bs = new byte[retryLen];
        in.get(bs);
        branchDaccCommitRequest.setRetrying(Boolean.valueOf(new String(bs, UTF8)));

    }

}
