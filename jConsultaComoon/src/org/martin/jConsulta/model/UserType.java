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
public class UserType implements Serializable{
    private byte id;
    private String name;

    public UserType(String name) {
        this.name = name;
    }

    public UserType(byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
