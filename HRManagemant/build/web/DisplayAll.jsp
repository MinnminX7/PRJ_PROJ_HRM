<%-- 
    Document   : DisplayAll
    Created on : Mar 2, 2022, 12:46:05 AM
    Author     : Phong Linh
--%>

<%@page import="Connector.Connector"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Employee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Testing</title>
        <%
            //Will delete this after testing
            List<Employee> emp = new ArrayList<Employee>();
            Connector c = new Connector();
            emp = c.getAllEmployees();
        %>
    </head>
    <body>
        <table border="1px">
            <%
                for (Employee e : emp) {
            %>
            <tr>
                <td>
                    <%=e.getID()%>
                </td>
                <td>
                    <%=e.getFirstName() + " " + e.getLastName()%>
                </td>
                <td>
                    <%=e.getBirthDate()%>
                </td>
                <td>
                    <%=e.getAge()%>
                </td>
                <td>
                    <%=e.getDepartmentID()%>
                </td>
                <td>
                    <%=e.getPositionID()%>
                </td>
                <td>
                    <%=e.getEmail()%>
                </td>
                <td>
                    <%=e.getNumber()%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
