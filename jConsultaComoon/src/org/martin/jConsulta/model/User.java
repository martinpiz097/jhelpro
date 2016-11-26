/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.model;

import java.io.Serializable;

/**
 *
 * @author martin
 */
public class User implements Serializable{
    private int id;
    private String nick;
    private String password;
    private byte idType;

    public User(int id, String nick, String password, byte idType) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.idType = idType;
    }

    public User(String nick, String password, byte idType) {
        this.nick = nick;
        this.password = password;
        this.idType = idType;
    }
    
    public boolean isAdmin(){
        return idType == 1;
    }

    public int getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public byte getIdType() {
        return idType;
    }
    
}
