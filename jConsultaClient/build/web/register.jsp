<%-- 
    Document   : register.jsp
    Created on : 25-11-2016, 9:11:37
    Author     : martin
--%>

    <%@page import="org.martin.jConsulta.model.User"%>
<%
        User login = (User)session.getAttribute("user");
    
        if(login == null || !login.isAdmin()){
            response.sendRedirect("index.jsp?e="+org.martin.jConsulta.controller.Error.INVALID_ACCESS);
            return;
        }
    
    %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de Alumnos</title>
    </head>
    <body>
        <h1>Registrando nuevos usuarios</h1>
        <form action="register.do" method="post">
            <label>Nick:&nbsp;&nbsp;</label>
            <input type="text" name="nick" placeholder="Nick: " required=""/>
            <br>
            <label>Contraseña: </label>
            <input type="password" name="pass1" required=""/>
            <br>
            <label>Repita Contraseña: </label>
            <input type="password" name="pass2" required=""/>
            <br>
            <input type="submit" value="Registrarse"/>
        </form>
        
        <a href="index.jsp">Volver a Index</a>
        
    </body>
</html>
