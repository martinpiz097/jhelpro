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
import org.martin.jConsulta.model.Message;

/**
 *
 * @author martin
 */
public class DAOMessage extends DAO<Message>{
    private static final String TABLE_NAME = "message";
    
    public DAOMessage() {}
    
    @Override
    public void addElement(Message element) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(TABLE_NAME);
        sb.append(" values(null,'");
        sb.append(element.getUser().getId());
        sb.append("','");
        sb.append(element.getText());
        sb.append("','");
        sb.append(element.getDateTime().toString());
        sb.append("')");
        execQuery(sb.toString());
    }

    @Override
    public Message getById(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(TABLE_NAME);
        sb.append(" where id = ");
        sb.append(id);
        ResultSet res = executeSelect(sb.toString());
        sb = null;
        
        try {
            if (res.next())
                return new Message(id, new DAOUser().getById(res.getInt(2)), res.getString(3), 
                        res.getTimestamp(4));
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        res = null;
        return null;
    }
    
}
