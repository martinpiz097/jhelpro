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
import org.martin.jConsulta.net.RegisterRequest;
import org.martin.jConsulta.net.RegisterResponse;

/**
 *
 * @author martin
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register.do"})
public class RegisterServlet extends HttpServlet {

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
        //Client client = new Client();
        String nick = request.getParameter("nick");
        String pass1 = request.getParameter("pass1").trim();
        String pass2 = request.getParameter("pass2").trim();
        
        if (!pass1.equals(pass2))
            response.sendRedirect("register.jsp?e="+Error.NOT_EQUALS_PASSWORDS);
        else{
            Client client = new Client();
            client.sendObject(new RegisterRequest(nick, pass1));
            RegisterResponse regResp = (RegisterResponse) client.getRecceivedObject();
        
            if (regResp.isRightRegister())
                response.sendRedirect("index.jsp?m="+MessageType.RIGHT_REGISTER);
            else
                response.sendRedirect("register.jsp?m="+MessageType.NICK_ALREADY_EXISTS);
        
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
