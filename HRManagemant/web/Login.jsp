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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <title>Log in</title>
    </head>
    <body>
        <style>
            h1 {
                font-family: "Segoe UI", sans-serif;
            }
            label {
                font-size: 20px;
                font-family: "Noto Sans", monospace;
            }
            .custom-text {
                font-family: "Noto Sans", monospace;
            }
            .custom-width {
                max-width: 400px;
                margin: auto;
            }
            .custom-width2 {
                max-width: 360px;
                margin: auto;
            }
        </style>
        <div class="container">
            <h1 class="display-1" align="center">Log in</h1>
            <%
                String error = (String)request.getAttribute("error");
                if (error != null) {
                    out.println("<div class=\"alert alert-danger custom-text custom-width\" role=\"alert\">" + error + "</div>");
                }
            %>
            <form action="MainPage">
                <div class="row mt-1 custom-text custom-width">
                    <label for="usernameInput" class="form-label">Username / Email:</label>
                    <input type="text" class="form-control" id="usernameInput" name="username">
                </div>
                <div class="row mt-1 custom-text custom-width">
                    <label for="passwordInput" class="form-label">Password:</label>
                    <input type="password" class="form-control" id="passwordInput" name="password">
                </div>
                <div class="row mt-3 custom-width2">
                    <input class="btn btn-primary custom-text" type="submit" value="Login">
                </div>
            </form>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
