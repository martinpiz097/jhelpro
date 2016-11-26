<%-- 
    Document   : admin
    Created on : 25-11-2016, 8:52:38
    Author     : martin
--%>
<%@page import="org.martin.jConsulta.model.Message"%>
<%@page import="org.martin.jConsulta.model.Alert"%>
<%@page import="org.martin.jConsulta.net.TReceiver"%>
<%@page import="org.martin.jConsulta.model.User"%>
<%
    User login = (User)session.getAttribute("user");
    boolean alertReady = session.getAttribute("alertReady") != null;
    
    if(login == null || !login.isAdmin()){
        response.sendRedirect("index.jsp?e="+org.martin.jConsulta.controller.Error.INVALID_ACCESS);
        return;
    }
    
    TReceiver reveiver = (TReceiver)session.getAttribute("tReceiver");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/cssAdmin.css" media="screen">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="contenedor">
            <div class="listUsers">
                <ul>
                    <%
                        for(User user : reveiver.getConnectedUsers()){
                            out.println("<li>"+user.getNick()+"</li>");
                        }
                    %>
                    
                </ul>
                
                
            </div>
            
            <div class="listReady">
                <h3>Listos</h3>
                <ul>
                    <%
                        for(Alert alert : reveiver.getAlerts()){
                            out.println("<li>"+alert.getUser().getNick()+"</li>");
                        }
                    %>
                </ul>
                
                <h3>Faltan</h3>
                <ul>
                    <%
                        for(User user : reveiver.getMissingUsers()){
                            out.println("<li>"+user.getNick()+"</li>");
                        }
                    %>
                    
                </ul>
                    <a href="cleanAlerts.do"><button>Limpiar</button></a>
                    <a href="admin.jsp"><button>Actualizar</button></a>
                    <a href="closeSession.do"><button>Cerrar Sesion</button></a>
                    
            </div>
            
            <div class="listMessages">
                <h3>Mensajes</h3>
                <ul>
                   <%
                       for(Message m : reveiver.getMessages()){
                           out.println("<li>"+m.getUser().getNick()+": "+m.getText()+"</li>");
                       }
                   %> 
                    
                </ul>
                
            </div>
        </div>
    </body>
</html>
