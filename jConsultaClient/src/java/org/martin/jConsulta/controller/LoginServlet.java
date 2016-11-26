/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.jConsulta.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.martin.jConsulta.net.Client;
import org.martin.jConsulta.net.LoginRequest;
import org.martin.jConsulta.net.LoginResponse;
import org.martin.jConsulta.net.TReceiver;

/**
 *
 * @author martin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Client client = new Client();
        String nick = request.getParameter("nick");
        String password = request.getParameter("passw");
        
        client.sendObject(new LoginRequest(nick, password));
        LoginResponse loginResponse = (LoginResponse) client.getRecceivedObject();
    
        if (loginResponse.isRightLogin()){
            request.getSession().setAttribute("tReceiver", new TReceiver(client));
            request.getSession().setAttribute("client", client);
            request.getSession().setAttribute("user", loginResponse.getUser());
            if (loginResponse.getUser().getIdType() == 1)
                response.sendRedirect("admin.jsp");
            else
                response.sendRedirect("client.jsp");
        }
        else{
            response.sendRedirect("index.jsp?e="+Error.LOGIN);
            client.disconnect();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
