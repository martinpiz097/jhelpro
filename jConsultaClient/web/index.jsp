<%-- 
    Document   : index
    Created on : 24-11-2016, 17:38:04
    Author     : martin
--%>

<%@page import="org.martin.jConsulta.model.User"%>
<%
    User login = (User)session.getAttribute("user");

    if(login != null){
        response.sendRedirect(login.isAdmin() ? "admin.jsp" : "client.jsp");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" href="css/cssGuiAlumno.css" media="screen"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    </head>
    <body>
        <h1>Login jConsulta</h1>
        <form action="login.do" method="post">
            <label>Nick:&nbsp;&nbsp;</label>
            <input type="text" name="nick" placeholder="Nick: " required=""/>
            <br>
            <label>Password:&nbsp;&nbsp;</label>
            <input type="password" name="passw" placeholder="Password: " required=""/>
            <br>
            <input type="submit" value="Ingresar">
        </form>
        <!--<a href="register.jsp"><button>¿No tienes cuenta? Regístrate</button></a>-->
        
    </body>
</html>

