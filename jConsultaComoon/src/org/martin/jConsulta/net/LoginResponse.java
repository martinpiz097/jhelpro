/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.net;

import java.io.Serializable;
import org.martin.jConsulta.model.User;

/**
 *
 * @author martin
 */
public class LoginResponse implements Serializable{
    private final User user;
    private final boolean rightLogin;

    public LoginResponse(User user) {
        this.user = user;
        rightLogin = user != null;
    }

    public User getUser() {
        return user;
    }

    public boolean isRightLogin() {
        return rightLogin;
    }
    
}
