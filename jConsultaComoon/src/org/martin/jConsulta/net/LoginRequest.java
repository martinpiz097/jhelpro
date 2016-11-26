/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.net;

import java.io.Serializable;

/**
 *
 * @author martin
 */
public class LoginRequest implements Serializable{
    private final String nick;
    private final String password;

    public LoginRequest(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }
    
}
