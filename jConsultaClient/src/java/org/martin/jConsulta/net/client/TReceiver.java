/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.net.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private boolean alertReady;
    private boolean isRunning;
    
    public TReceiver() throws IOException {
        this(new Client());
    }

    public TReceiver(Client client) {
        this.client = client;
        alertReady = false;
        isRunning = true;
        start();
    }

    public boolean isAlertReady() {
        return alertReady;
    }

    public void setAlertReady(boolean alertReady) {
        this.alertReady = alertReady;
    }

    public void stopThread(){
        isRunning = false;
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
                if (objRec instanceof Petition)
                    if ((Petition)objRec == Petition.RESTORE_ALERTS)
                        alertReady = false;
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(TReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
