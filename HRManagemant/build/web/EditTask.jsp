<%-- 
    Document   : EditTask
    Created on : Mar 23, 2022, 1:27:03 AM
    Author     : Phong Linh
--%>

<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Model.Task.Member"%>
<%@page import="Model.Task"%>
<%@page import="Controller.TaskHtmlCtrl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <title>Edit Task</title>
        <%
            String prevUrl = request.getHeader("referer");
            Task t;
            TaskHtmlCtrl ctrl = new TaskHtmlCtrl();
            if (request.getParameter("id") == null || request.getParameter("id").length() <= 0) {
                t = new Task();
                t.setId(0);
                t.setName("");
                t.setDesc("");
                t.setDeadline(LocalDateTime.now().plusDays(15).truncatedTo(ChronoUnit.MINUTES));
                t.setAssignedEmp(new ArrayList<Member>());
            } else {
                t = ctrl.getTask(Integer.parseInt(request.getParameter("id")));
                if (t == null) {
                    response.sendRedirect(prevUrl);
                }
            }
            String name = t.getName();
            if (request.getParameter("name") != null) {
                name = request.getParameter("name");
            }
            String desc = t.getDesc();
            if (request.getParameter("desc") != null) {
                desc = request.getParameter("desc");
            }
            List<Member> members = t.getAssignedEmp();
            if (request.getParameterValues("members") != null) {
                members = ctrl.getMembers(request.getParameterValues("members"));
            }
            LocalDateTime deadline = t.getDeadline();
            if (request.getParameter("date") != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                deadline = LocalDateTime.parse(request.getParameter("date"), formatter);
            }
            String submit1 = "editTask";
            if (t.getId() != 0) {
                submit1 += "?id=" +t.getId();
            }
        %>
    </head>
    <body>
        <style>
            body {
                font-family:"Noto Sans", sans-serif;
            }
        </style>
        <div class="container">
            <h3 class="m-2">Currently editing task (ID: <%=t.getId()%>)</h3>
            
            <form action='TaskMng?id=<%=t.getId()%>' method='POST' id='editTaskForm'>
                <div class='my-2'>
                    <label for='editTaskName'>Task's Name:</label>
                    <input type='text' class='form-control' id='editTaskName' form='editTaskForm' name='name' value="<%=name%>">
                </div>
                <div class='my-2'>
                    <label for='editTaskDesc'>Task's Description: </label>
                    <textarea class='form-control' name='desc' form='editTaskForm' rows='15'><%=desc%></textarea>
                </div>
                <div class='my-2'>
                    <label for='editTaskDate'>Task's Deadline: </label>
                    <input type='datetime-local' class='form-control' id='editTaskDate' form='editTaskForm' name='date' value="<%=deadline.toString()%>">
                </div>
                <div class='my-2'>
                    <h5><b>Task's assigned Employees:</b></h5>
                    <div class='my-2' id='AddedEmp'>
                        <%
                            for (Member m : members) {
                                out.println(ctrl.getMemberOption(m.getEmpID(), m.getFullName(), m.getEmail(), m.getNumber()));
                            }
                        %>
                    </div>
                    <div>
                        <h5><i>Choose employee to add:</i></h5>
                        <div class='row'>
                            <form action='EditTask.jsp' method='POST'>
                            <select class='col-sm-3 ms-2' id='empSearchType' form='Search' name='empSearchType'>
                                <option selected value='0'>Name</option>
                                <option value='1'>Department</option>
                                <option value='2'>Position</option>
                                <option value='3'>Number</option>
                            </select>
                            <input class='col-sm-6 mx-2' type='text' id='empSearchInput' form='Search' name='empSearchInput' placeholder='Search for...'>
                            <button class="col-auto btn" form='Search' type='submit'>Search</button>
                            </form>
                        </div>
                        <div>
                            <%
                                if (request.getParameter("empSearchType") != null && request.getParameter("empSearchInput") != null) {
                                    out.println(ctrl.getSearchMember(request.getParameter("empSearchType"), request.getParameter("empSearchInput")));
                                }
                            %>
                        </div>
                    </div>
                </div>
                <div class='row justify-content-end'>
                    <a href='<%=prevUrl%>' class='col-sm-2 btn btn-primary'>Cancel</a>
                    <button type='submit' form="editTaskForm" class='col-sm-2 btn btn-primary'>Save</button>
                </div>
            </form>
                    <form action='<%=submit1%>' method='POST' id='Search'></form>
        </div>
        <script>
            function uncheckMember(memberEle) {
                if (memberEle.checked) {
                    
                } else {
                    const element = document.getElementById(memberEle.id);
                    const element_label = document.getElementById(memberEle.id + "_label");
                    element.remove();
                    element_label.remove();
                }
            }
            function onAddEmp(Ele, id, fullname, email, number) {
                if (Ele.checked) {
                    var exist = document.getElementById("Emp" + id);
                    if (exist === null) {
                        var contract = fullname + " (" + email + "/" + number + ")";
                        var Added = "<input form='editTaskForm' type='checkbox' name='members_' id='Emp" + id + "' onchange='uncheckMember(this)' value='" + id + "' checked>" +
                                    "<label id='Emp" + id + "_label' for='Emp" + id + "'> " + contract + "</label></br>";
                        var div = document.getElementById("AddedEmp");
                        div.insertAdjacentHTML('beforeend', Added);
                    }
                } else {
                    var Ele_ = document.getElementById("Emp" + id);
                    Ele_.checked = false;
                    uncheckMember(Ele_);
                }
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
