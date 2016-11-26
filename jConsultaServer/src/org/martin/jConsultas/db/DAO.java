/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsultas.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public abstract class DAO<T> implements DAOFactory<T>{
    protected DbConnection connection;

    public DAO() {
        try {
            connection = new DbConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void execQuery(String query) {
        try {
            connection.con.createStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected ResultSet executeSelect(String query) {
        try {
            return connection.con.createStatement().executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
