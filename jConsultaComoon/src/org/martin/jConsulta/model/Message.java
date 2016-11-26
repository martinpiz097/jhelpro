/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author martin
 */
public class Message implements Serializable{
    private int id;
    private User user;
    private String text;
    private Timestamp dateTime;

    public Message(int id, User user, String text, Timestamp dateTime) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message(User user, String text, Timestamp dateTime) {
        this.user = user;
        this.text = text;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }
    
}
