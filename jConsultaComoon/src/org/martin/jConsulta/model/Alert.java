/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author martin
 */
public class Alert implements Serializable{
    private int id;
    private User user;
    private Timestamp dateTime;

    public Alert(int id, User user, Timestamp dateTime) {
        this.id = id;
        this.user = user;
        this.dateTime = dateTime;
    }

    public Alert(User user, Timestamp dateTime) {
        this.user = user;
        this.dateTime = dateTime;
    }
    
    public Alert(User user){
        this(user, new Timestamp(System.currentTimeMillis()));
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }
    
}
