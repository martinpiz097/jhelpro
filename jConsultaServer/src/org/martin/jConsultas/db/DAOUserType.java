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
import org.martin.jConsulta.model.UserType;

/**
 *
 * @author martin
 */
public class DAOUserType extends DAO<UserType>{
    private static final String TABLE_NAME = "userType";
    
    public DAOUserType() {}
    
    @Override
    public void addElement(UserType element) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(TABLE_NAME);
        sb.append(" values(null,'");
        sb.append(element.getName());
        sb.append("')");
        execQuery(sb.toString());
    }

    @Override
    public UserType getById(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(TABLE_NAME);
        sb.append(" where id = ");
        sb.append(id);
        ResultSet res = executeSelect(sb.toString());
        sb = null;
        
        try {
            if (res.next())
                return new UserType((byte) id, res.getString(2));
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        res = null;
        return null;
    }
}
