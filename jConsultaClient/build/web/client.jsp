<%-- 
    Document   : client.jsp
    Created on : 25-11-2016, 8:52:16
    Author     : martin
--%>
<%@page import="org.martin.jConsulta.model.User"%>
<%
    User login = (User)session.getAttribute("user");
    boolean alertReady = session.getAttribute("alertReady") != null;
    
    if(login == null || login.isAdmin()){
        response.sendRedirect("index.jsp?e="+org.martin.jConsulta.controller.Error.INVALID_ACCESS);
        return;
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <link rel="stylesheet" href="css/cssGuiAlumno.css" media="screen"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="js/jQuery.css"></script>
        <script src="js/funcionesClient.js"></script>

    </head>
    <body>
        <style>
            #btnFinish{
                background-color: <%
                        if(alertReady)
                            out.println("gray");
                        else
                            out.println("red");
                    %>;
            }
            
        </style>
        
        <div id="containerBoton">
            <a id="linkBtn" href="sendAlert.do">
                <button id="btnFinish">Finalizado</button>
            </a>
        </div>
        <br>
        <div id="containerMsg">
            <form action="sendMessage.do" method="post">
                <input id="txtMsg" name="text" type="text" placeholder="Mensaje: " required=""/>
                <br>
                <input type="submit" value="Enviar"/>
            </form>
        </div>
        
        <a href="closeSession.do"><button>Cerrar Sesión</button></a>
    </body>
</html>
