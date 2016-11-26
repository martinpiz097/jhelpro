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
import org.martin.jConsulta.model.User;

/**
 *
 * @author martin
 */
public class DAOUser extends DAO<User>{
    private static final String TABLE_NAME = "user";
    
    public DAOUser() {}

    public boolean registerUser(User user){
        if (getByNick(user.getNick()) != null)
            return false;
        addElement(user);
        return true;
    }
    
    @Override
    public void addElement(User element) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(TABLE_NAME);
        sb.append(" values(null,'");
        sb.append(element.getNick());
        sb.append("',");
        sb.append("AES_ENCRYPT((select getAesKey()), '").append(element.getPassword()).append("')");
        sb.append(",'");
        sb.append(element.getIdType());
        sb.append("')");
        execQuery(sb.toString());
        //System.out.println("Query: "+sb.toString());
    }
    
    @Override
    public User getById(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(TABLE_NAME);
        sb.append(" where id = ");
        sb.append(id);
        ResultSet res = executeSelect(sb.toString());
        sb = null;
        
        try {
            if (res.next())
                return new User(id, res.getString(2), res.getString(3), res.getByte(4));
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        res = null;
        return null;
    }
    
    public User getByNick(String nick){
        try {
            StringBuilder sb = new StringBuilder();
            
            sb.append("select * from user where nick='").append(nick).append('\'');
            
            ResultSet res = connection.con.createStatement().executeQuery(sb.toString());
            User u = null;

            if (res.next())
                u = new User(res.getInt(1), res.getString(2), res.getString(3), res.getByte(4));
            
            res.close();
            res = null;
            return u;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User getByNickAndPassw(String nick, String password){
        try {
            StringBuilder sb = new StringBuilder();
            
            sb.append("select * from user where nick='");
            sb.append(nick);
            sb.append("' and password = AES_ENCRYPT((select getAesKey()), '")
                    .append(password).append("')");
            
            ResultSet res = connection.con.createStatement().executeQuery(sb.toString());
            User u = null;

            if (res.next())
                u = new User(res.getInt(1), res.getString(2), res.getString(3), res.getByte(4));
            
            res.close();
            res = null;
            return u;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
