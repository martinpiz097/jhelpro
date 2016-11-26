/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsultas.db;

/**
 *
 * @author martin
 */
public interface DAOFactory<T>{
    abstract void addElement(T element);
    abstract T getById(int id);
}
