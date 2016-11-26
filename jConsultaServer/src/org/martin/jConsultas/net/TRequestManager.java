/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsultas.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.jConsulta.model.User;
import org.martin.jConsulta.net.LoginRequest;
import org.martin.jConsulta.net.LoginResponse;
import org.martin.jConsulta.net.RegisterRequest;
import org.martin.jConsulta.net.RegisterResponse;
import org.martin.jConsulta.net.interfaces.Connectable;
import org.martin.jConsultas.db.DAOUser;

/**
 *
 * @author martin
 */
public class TRequestManager extends Thread implements Connectable{
    private final Socket sockRequest;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    
    public TRequestManager(Socket sockRequest) throws IOException {
        this.sockRequest = sockRequest;
        output = new ObjectOutputStream(sockRequest.getOutputStream());
        input = new ObjectInputStream(sockRequest.getInputStream());
        setName("tRequest->"+getId());
    }

    @Override
    public void sendObject(Object obj) {
        try {
            output.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(TRequestManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getRecceivedObject() {
        try {
            return input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TRequestManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void run() {
        DAOUser daoUser = new DAOUser();
        Object objRec = getRecceivedObject();
    
        if (objRec instanceof RegisterRequest) {
            RegisterRequest request = (RegisterRequest) objRec;
            // Por ahora solo regista alumnos, más adelante registrar todo tipo de usuario
            boolean registered = daoUser.registerUser(new User(request.getNick(), request.getPassword(), (byte)2));
            sendObject(new RegisterResponse(registered));
        }
        else if (objRec instanceof LoginRequest) {
            LoginRequest request = (LoginRequest) objRec;
            User login = daoUser.getByNickAndPassw(request.getNick(), request.getPassword());
            sendObject(new LoginResponse(login));
    
            if (login != null)
                Server.getServer().addClient(new Client(login, sockRequest, output, input));
            
        }
    }

}
