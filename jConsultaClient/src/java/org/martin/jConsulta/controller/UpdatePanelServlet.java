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
import org.martin.jConsulta.model.Alert;
import org.martin.jConsulta.model.Message;
import org.martin.jConsulta.model.User;
import org.martin.jConsulta.net.admin.TReceiver;

/**
 *
 * @author martin
 */
@WebServlet(name = "UpdatePanelServlet", urlPatterns = {"/updatePanel.do"})
public class UpdatePanelServlet extends HttpServlet {

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
        String typeList = request.getParameter("typeList");
        PrintWriter out = response.getWriter();
        TReceiver receiver = (TReceiver) request.getSession().getAttribute("tReceiver");
        
        switch(typeList){
            case "users":
                out.println("Listado de Alumnos");
                out.println("<ul>");
                
                for(User user : receiver.getConnectedUsers())
                    out.println("<li>"+user.getNick()+"</li>");
                
                out.println("</ul>");
                break;
            case "ready":
                out.println("Listos");
                out.println("<ul>");
                
                for(Alert alert : receiver.getAlerts())
                    out.println("<li>"+alert.getUser().getNick()+"</li>");
                
                out.println("</ul>");
                
                out.println("Faltan");
                out.println("<ul>");
                
                for(User user : receiver.getMissingUsers())
                    out.println("<li>"+user.getNick()+"</li>");
                
                out.println("</ul>");
                break;
                
            case "messages":
                out.println("Mensajes");
                out.println("<ul>");
                
                for(Message m : receiver.getMessages())
                    out.println("<li>"+m.getUser().getNick()+": "+m.getText()+"</li>");
                
                out.println("</ul>");
                break;
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
