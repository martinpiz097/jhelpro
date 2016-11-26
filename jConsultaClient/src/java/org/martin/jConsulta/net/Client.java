/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.jConsulta.net.interfaces.Connectable;
import org.martin.jConsulta.system.SysInfo;

/**
 *
 * @author martin
 */
public class Client implements Connectable{
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    public Client() throws IOException {
        socket = new Socket(SysInfo.LOCALHOST, SysInfo.DEFAULT_PORT);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void sendObject(Object obj) {
        try {
            output.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getRecceivedObject() {
        try {
            return input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void sendDisconnectPetition(){
        sendObject(Petition.DISCONNECT);
        try {
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconnect(){
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
