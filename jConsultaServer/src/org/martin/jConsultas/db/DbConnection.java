/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsultas.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class DbConnection {
    public final Connection con;

    public DbConnection() throws SQLException {
        MysqlDataSource mysqlDs = new MysqlDataSource();
        mysqlDs.setServerName("localhost");
        mysqlDs.setUser("root");
        mysqlDs.setPassword("admin");
        mysqlDs.setDatabaseName("dbConsultas");
        con = mysqlDs.getConnection();
    }
    
}
