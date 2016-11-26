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
import org.martin.jConsulta.model.Alert;

/**
 *
 * @author martin
 */
public class DAOAlert extends DAO<Alert>{
    private static final String TABLE_NAME = "alert";
    
    public DAOAlert() {}
    
    @Override
    public void addElement(Alert element) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(TABLE_NAME);
        sb.append(" values(null,'");
        sb.append(element.getUser().getId());
        sb.append("','");
        sb.append(element.getDateTime().toString());
        sb.append("')");
        execQuery(sb.toString());
    }

    @Override
    public Alert getById(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(TABLE_NAME);
        sb.append(" where id = ");
        sb.append(id);
        ResultSet res = executeSelect(sb.toString());
        sb = null;
        
        try {
            if (res.next())
                return new Alert(id, new DAOUser().getById(res.getInt(2)), res.getTimestamp(3));
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        res = null;
        return null;
    }
    
}
