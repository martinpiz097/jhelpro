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
public class RegisterRequest extends LoginRequest implements Serializable{

    public RegisterRequest(String nick, String password) {
        super(nick, password);
    }
}
