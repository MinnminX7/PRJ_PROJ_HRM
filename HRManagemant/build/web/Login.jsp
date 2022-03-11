<%-- 
    Document   : Login
    Created on : Mar 12, 2022, 12:59:43 AM
    Author     : Phong Linh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log in</title>
    </head>
    <body>
        <%
            String error = (String)request.getAttribute("error");
            if (error != null) {
                out.println("<font color=red size=4px>" + error + "</font></br>");
            }
        %>
        <form action="MainPage">
            User: <input type="text" name="username"/><br/>  
            Password: <input type="password" name="password"/><br/>
            <input type="submit" value="login">
        </form>
    </body>
</html>
