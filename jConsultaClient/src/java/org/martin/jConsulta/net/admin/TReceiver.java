/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.net.admin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.electroList.structure.ElectroList;
import org.martin.jConsulta.model.Alert;
import org.martin.jConsulta.model.Message;
import org.martin.jConsulta.model.User;
import org.martin.jConsulta.net.Client;
import org.martin.jConsulta.net.DisconnectRequest;
import org.martin.jConsulta.net.Petition;
import org.martin.jConsulta.net.interfaces.Connectable;

/**
 *
 * @author martin
 */
public class TReceiver extends Thread implements Connectable{
    private final Client client;
    private ElectroList<User> connectedUsers;
    private ElectroList<Alert> alerts;
    private ElectroList<Message> messages;
    private int fromIndex;
    
    private boolean isRunning;
    
    public TReceiver() throws IOException {
        this(new Client());
    }

    public TReceiver(Client client) {
        this.client = client;
        connectedUsers = new ElectroList<>();
        alerts = new ElectroList<>();
        messages = new ElectroList<>();
        isRunning = true;
        start();
    }

    public ElectroList<User> getConnectedUsers() {
        return connectedUsers;
    }

    public ElectroList<Alert> getAlerts() {
        return alerts;
    }
    
    public ElectroList<User> getMissingUsers(){
        ElectroList<User> missings = new ElectroList<>();
        boolean isInAlertList;
        
        for (User user : connectedUsers) {
            isInAlertList = false;
            for (Alert alert : alerts) {
                user = alert.getUser();
                if (user.getId() == user.getId()) {
                    isInAlertList = true;
                    break;
                }
            }
            if (!isInAlertList)
                missings.add(user);
        }
        return missings;
    }
    
    public void cleanAlerts(){
        alerts.clear();
        sendObject(Petition.RESTORE_ALERTS);
    }
    
    public void stopThread(){
        isRunning = false;
    }

    public ElectroList<Message> getMessages() {
        return messages;
    }

    @Override
    public void sendObject(Object obj) {
        client.sendObject(obj);
    }

    @Override
    public Object getRecceivedObject() {
        return client.getRecceivedObject();
    }

    @Override
    public void run() {
        Object objRec;
        DisconnectRequest dr;
        while (isRunning) {
            try {
                objRec = getRecceivedObject();
                if (objRec instanceof ElectroList)
                    connectedUsers = (ElectroList<User>) objRec;
                else if (objRec instanceof Message)
                    messages.add((Message) objRec);
                else if (objRec instanceof Alert)
                    alerts.add((Alert) objRec);
                else if(objRec instanceof DisconnectRequest){
                    dr = (DisconnectRequest) objRec;
                    for (User connectedUser : connectedUsers)
                        if (connectedUser.getId() == dr.getUser().getId())
                            connectedUsers.remove(connectedUser);
                }
                    
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(TReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
