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
package io.seata.server.springboot;

import com.taobao.pandora.boot.PandoraBootstrap;
import io.seata.server.Server;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>ServerApplication</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/11/1 9:35
 * @since 
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) throws IOException {
        PandoraBootstrap.run(args);
        SpringApplication.run(ServerApplication.class, args);
        Server.main(new String[] {});
        PandoraBootstrap.markStartupAndWait();
    }

}
