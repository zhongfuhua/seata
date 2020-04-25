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
package io.seata.spring.boot.autoconfigure.properties.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static io.seata.spring.boot.autoconfigure.StarterConstants.STORE_PREFIX;

/**
 * <P>ShutdownProperties</P>
 *
 * @author zhong.fuhua@iwhalecloud.com
 * @date 2019/10/25 15:20
 * @since
 */
@Component
@ConfigurationProperties(prefix = STORE_PREFIX)
public class StoreProperties {

    private String mode = "file";

    private String fileDir = "sessionStore";

    private String dbDatasource = "dbcp";

    private String dbDbType = "mysql";

    private String dbDriverClassName = "com.mysql.jdbc.Driver";

    private String dbUrl = "jdbc:mysql://127.0.0.1:3306/seata";

    private String dbUser = "mysql";

    private String dbPassword = "mysql";

    private int dbMinConn = 1;

    private int dbMaxConn = 3;

    private String dbGlobalTable = "global_table";

    private String dbBranchTable = "branch_table";

    private String dbLockTable = "lock_table";

    private int dbQueryLimit = 1000;


    public String getMode() {
        return mode;
    }

    public StoreProperties setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getFileDir() {
        return fileDir;
    }

    public StoreProperties setFileDir(String fileDir) {
        this.fileDir = fileDir;
        return this;
    }

    public String getDbDatasource() {
        return dbDatasource;
    }

    public StoreProperties setDbDatasource(String dbDatasource) {
        this.dbDatasource = dbDatasource;
        return this;
    }

    public String getDbDbType() {
        return dbDbType;
    }

    public StoreProperties setDbDbType(String dbDbType) {
        this.dbDbType = dbDbType;
        return this;
    }

    public String getDbDriverClassName() {
        return dbDriverClassName;
    }

    public StoreProperties setDbDriverClassName(String dbDriverClassName) {
        this.dbDriverClassName = dbDriverClassName;
        return this;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public StoreProperties setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
        return this;
    }

    public String getDbUser() {
        return dbUser;
    }

    public StoreProperties setDbUser(String dbUser) {
        this.dbUser = dbUser;
        return this;
    }

    public int getDbMinConn() {
        return dbMinConn;
    }

    public StoreProperties setDbMinConn(int dbMinConn) {
        this.dbMinConn = dbMinConn;
        return this;
    }

    public int getDbMaxConn() {
        return dbMaxConn;
    }

    public StoreProperties setDbMaxConn(int dbMaxConn) {
        this.dbMaxConn = dbMaxConn;
        return this;
    }

    public String getDbGlobalTable() {
        return dbGlobalTable;
    }

    public StoreProperties setDbGlobalTable(String dbGlobalTable) {
        this.dbGlobalTable = dbGlobalTable;
        return this;
    }

    public String getDbBranchTable() {
        return dbBranchTable;
    }

    public StoreProperties setDbBranchTable(String dbBranchTable) {
        this.dbBranchTable = dbBranchTable;
        return this;
    }

    public String getDbLockTable() {
        return dbLockTable;
    }

    public StoreProperties setDbLockTable(String dbLockTable) {
        this.dbLockTable = dbLockTable;
        return this;
    }

    public int getDbQueryLimit() {
        return dbQueryLimit;
    }

    public StoreProperties setDbQueryLimit(int dbQueryLimit) {
        this.dbQueryLimit = dbQueryLimit;
        return this;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public StoreProperties setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
        return this;
    }
}
