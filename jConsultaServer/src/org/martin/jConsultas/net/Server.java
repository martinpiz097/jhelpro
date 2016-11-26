/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsultas.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.martin.electroList.structure.ElectroList;
import org.martin.jConsulta.model.User;
import org.martin.jConsulta.system.SysInfo;

/**
 *
 * @author martin
 */
public class Server {
    private ServerSocket serverSock;
    private ElectroList<Client> clientsConnected;
    private static Server server;
    
    static {
        try {
            server = new Server();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Server getServer(){
        return server;
    }
    
    private Server() throws IOException {
        this(SysInfo.DEFAULT_PORT);
    }
    
    private Server(int port) throws IOException{
        serverSock = new ServerSocket(port);
        clientsConnected = new ElectroList<>();
    }
    
    public synchronized boolean hasClientsConnected(){
        return clientsConnected.size() > 0;
    }
    
    public synchronized boolean hasAdmins(){
        if(!hasClientsConnected())
            return false;
        for (Client client : clientsConnected){
                JOptionPane.showMessageDialog(null, "Client: "+client+"\nUser: "+client.getUser());
            if (client.getUser().isAdmin())
                return true;
        }
        return false;
    }
    
    public synchronized int getClientsCount(){
        return clientsConnected.size();
    }
    
    public synchronized void updateListAdmins(){
        ElectroList<User> usersConnected = getUsersConnectedForAdmin();
        for (Client client : clientsConnected)
            if (client.isAdmin())
                client.sendObject(usersConnected);
    }
    
    public synchronized void addClient(Client client){
        clientsConnected.add(client);
        updateListAdmins();
    }
    
    public synchronized void removeClientById(int id){
        //Client cli = clientsConnected.findFirst(c->c.getUser().getId() == id);
        //cli.stopThread();
        //clientsConnected.removeIf(c->c.getUser().getId() == id);
//        for (Client client : clientsConnected) {
//            if (client.getUser().getId() == id) {
//                client.stopThread();
//                clientsConnected.remove(client);
//            }
//        }
        clientsConnected.removeFirst(c->c.getUser().getId() == id).stopThread();
        if(hasAdmins()) 
            updateListAdmins();
    }

    public ElectroList<Client> getClientsConnected() {
        return clientsConnected;
    }
    
    // Metodo que entrega los usuarios conectados al admin
    public synchronized ElectroList<User> getUsersConnectedForAdmin(){
        ElectroList<User> usersConnected = new ElectroList<>();
        
        for (Client client : clientsConnected)
            if (client.getUser().getIdType() != 1)
                usersConnected.add(client.getUser());
//        clientsConnected.stream().filter(cli->cli.getUser().getIdType() != 1)
//                .forEach(c->{
//                    usersConnected.add(c.getUser());
//                });
        return usersConnected;
    }
    
    public void start(){
        System.out.println("Servidor iniciado");
        while (true) {            
            try {
                new TRequestManager(serverSock.accept()).start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        if (args.length > 0)
            if (args[0].equals("start"))
                Server.getServer().start();
            else
                System.out.println("Parámetros incorrectos");
        else
            System.err.println("¡Error al iniciar! No existen parámetros de conexión");
    }
    
}
