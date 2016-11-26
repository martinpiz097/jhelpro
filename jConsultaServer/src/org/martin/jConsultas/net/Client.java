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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.jConsulta.model.Alert;
import org.martin.jConsulta.model.Message;
import org.martin.jConsulta.model.User;
import org.martin.jConsulta.net.DisconnectRequest;
import org.martin.jConsulta.net.Petition;
import org.martin.jConsulta.net.interfaces.Connectable;
import org.martin.jConsultas.db.DAOAlert;
import org.martin.jConsultas.db.DAOMessage;

/**
 *
 * @author martin
 */
public final class Client extends Thread implements Connectable{
    private final User user;
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final DAOAlert daoAlert;
    private final DAOMessage daoMessage;
    private boolean isRunning;
    
    public Client(User user, Socket socket) throws IOException {
        this.user = user;
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        daoAlert = new DAOAlert();
        daoMessage = new DAOMessage();
        isRunning = true;
        start();
        if (isAdmin() && Server.getServer().hasClientsConnected())
            sendObject(Server.getServer().getUsersConnectedForAdmin());
    }

    public Client(User user, Socket socket, ObjectOutputStream output, ObjectInputStream input) {
        this.user = user;
        this.socket = socket;
        this.output = output;
        this.input = input;
        daoAlert = new DAOAlert();
        daoMessage = new DAOMessage();
        isRunning = true;
        start();
        if (isAdmin() && Server.getServer().hasClientsConnected())
            sendObject(Server.getServer().getUsersConnectedForAdmin());
        setName("Client->"+user.getNick());
    }
    
    

    public boolean isAdmin(){
        return user.getIdType() == 1;
    }
    
    @Override
    public void sendObject(Object obj) {
        try {
            output.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToAdmins(Object obj){
        if (Server.getServer().hasClientsConnected()) {
            for (Client client : Server.getServer().getClientsConnected())
                if (client.getUser().getIdType() == 1)
                    client.sendObject(obj);
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
    
    public void stopThread(){
        isRunning = false;
    }
    
    public void closeStreams(){
        try {
            output.close();
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection(){
        closeStreams();
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User getUser() {
        return user;
    }

    @Override
    public void run() {
        Object recObject;
        Message msg;
        Alert alert;
        Petition pet;
        
        while (isRunning) {
            try {
                recObject = getRecceivedObject();
                
                if (recObject instanceof Message) {
                    msg = (Message) recObject;
                    daoMessage.addElement(msg);
                    sendToAdmins(msg);
                            
                }
                else if (recObject instanceof Alert) {
                    alert = (Alert) recObject;
                    daoAlert.addElement(alert);
                    sendToAdmins(alert);
                }
                else if (recObject instanceof Petition) {
                    pet = (Petition) recObject;
                    if (pet == Petition.DISCONNECT) {
                        // Se envia a los admmins la peticion de desconexion
                        // para que ellos sepan que un usuario se desconecto y 
                        // asi eliminar de la lista dicho usuario.
                        if(!user.isAdmin())
                            sendToAdmins(new DisconnectRequest(user));
                        closeConnection();
                        Server.getServer().removeClientById(getUser().getId());
                    }
                }
                
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
