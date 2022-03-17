/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Connector.EmployeeCtrl;
import Model.Department;
import Model.Employee;
import Model.EmployeeStat;
import Model.EmployeeStat.LoginInfo;
import Model.EmployeeStat.SmallTask;
import Model.Fine;
import Model.Position;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Phong Linh
 */
public class AdminPage extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
            out.println("<title>admin controller</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<style>body{font-family:\"Noto Sans\", sans-serif;}\n" +
                    ".container {margin-top:50px}" +
                    ".green {background-color: #57ff94 !important; font-size: 15px;}" +
                    ".red {background-color: #ff5757 !important; font-size: 15px;}" +
                    ".gray {background-color: #c7c7c7 !important; font-size: 15px;}" +
                    ".align-l {text-align: left !important;padding: 0;font-weight: bold;}" +
                    ".divider {height: 1px;background-image: linear-gradient(to right, transparent, rgb(48,49,51), transparent);}" +
                    ".spoiler {color: black;background-color:black;}" +
                    ".spoiler:hover {background-color:white;}" +
                    "</style>");
            out.println("<div class=\"container\">");
            out.println("<div style=\"text-align:right; position:relative;\"><a href=\"logout\">Log out</a></div>");
            HttpSession session = request.getSession();
            
            int curTab = 0;
            if (request.getParameter("tab") != null && !request.getParameter("tab").isEmpty()) {
                curTab = Integer.parseInt(request.getParameter("tab"));
            }
            int page = 1;int pageLen = 10;
            if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
                page = Integer.max(1, Integer.parseInt(request.getParameter("page")));
            }
            switch (curTab) {
                case 0:
                default:
                    out.println("<ul class=\"nav nav-tabs\">\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link active\" href=\"control?tab=0\">Employee</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=1\">Tasks</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=2\">Department</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=3\">Position</a>\n" +
                            "   </li>\n" +
                            "</ul>");
                    out.println("<table class=\"table table-hover\">\n");
                    EmployeeCtrl ctrl = new EmployeeCtrl();
                    String sort = "";
                    if (request.getParameter("sort") != null) {
                        sort = request.getParameter("sort");
                    }
                    List<EmployeeStat> empList = ctrl.getEmpStats(page - 1, pageLen, sort);
                    int empCount = ctrl.getEmpCount();
                    int pageCount = (empCount - (empCount % pageLen)) / pageLen + 1;
                    if (this.isParameterNullOrEmpty(request.getParameter("action")) || this.isParameterNullOrEmpty("emp")) {
                        //add if there's "createEmp"
                        if (!this.isParameterNullOrEmpty(request.getParameter("createEmp"))) {
                            EmployeeStat emp = new EmployeeStat();
                            emp.setFirstName(request.getParameter("createFName"));
                            emp.setLastName(request.getParameter("createLName"));
                            emp.setBirthDate(java.sql.Date.valueOf(request.getParameter("createBDate")));
                            emp.setDepartmentID(Integer.parseInt(request.getParameter("createEmpDepartment")));
                            emp.setPositionID(Integer.parseInt(request.getParameter("createEmpPosition")));
                            emp.setEmail(request.getParameter("createEmpEmail"));
                            emp.setNumber(request.getParameter("createEmpNumber"));
                            LoginInfo l = emp.new LoginInfo (request.getParameter("createEmpUsername"), request.getParameter("createEmpPassword"));
                            List<LoginInfo> loginInfos = new ArrayList<>();
                            loginInfos.add(l);
                            emp.setLoginInfo(loginInfos);
                            emp.setBaseSal(Integer.parseInt(request.getParameter("createEmpSalary")));
                            ctrl.addEmp(emp);
                            response.sendRedirect("control");
                            return;
                        } else if (!this.isParameterNullOrEmpty(request.getParameter("deleteEmp"))) {
                            int empID = Integer.parseInt(request.getParameter("deleteEmp"));
                            ctrl.delEmp(empID);
                            response.sendRedirect("control?tab=0&page=" + page);
                            return;
                        }
                        //create btn
                        List<Department> departs = ctrl.getDepartments();
                        List<Position> posis = ctrl.getPositions();
                        String departHtml = "", posiHtml = "";
                        for (Department d : departs) {
                            departHtml += String.format("<option value=\"%d\">%s</option>", d.getId(), d.getName());
                        }
                        for (Position p : posis) {
                            posiHtml += String.format("<option value=\"%d\">%s</option>", p.getId(), p.getName());
                        }
                        out.println("<button type=\"button\" class=\"btn btn-primary my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#createEmp\">\n" +
                                    "New Employee\n" +
                                    "</button>\n" +
                                    "\n" +
                                    "<div class=\"modal fade\" id=\"createEmp\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\">\n" +
                                    "  <div class=\"modal-dialog modal-lg\">\n" +
                                    "    <div class=\"modal-content\">\n" +
                                    "      <div class=\"modal-header\">\n" +
                                    "        <h5 class=\"modal-title\" id=\"staticBackdropLabel\">Create new employee</h5>\n" +
                                    "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n" +
                                    "      </div>\n" +
                                    "     <form action=\"control?createEmp=1\" method=\"POST\">\n" +
                                    "      <div class=\"modal-body\">\n" +
                                    "        <div class=\"container-fluid\">\n" +
                                    "           <div class=\"row my-2\">\n" +
                                    "               <div class=\"col-md-4\">\n" +
                                    "                   <label for=\"createFName\" class=\"form-label\">First Name:</label>\n" +
                                    "                   <input type=\"text\" class=\"form-control\" id=\"createFName\" name=\"createFName\" require>\n" +
                                    "               </div>\n" +
                                    "               <div class=\"col-md-4\">\n" +
                                    "                   <label for=\"createLName\" class=\"form-label\">Last Name:</label>\n" +
                                    "                   <input type=\"text\" class=\"form-control\" id=\"createLName\" name=\"createLName\" require>\n" +
                                    "               </div>\n" +
                                    "               <div class=\"col-md-4\">\n" +
                                    "                   <label for=\"createBDate\" class=\"form-label\">Birth Date:</label>\n" +
                                    "                   <input type=\"date\" class=\"form-control\" id=\"createBDate\" name=\"createBDate\" require>\n" +
                                    "               </div>\n" +
                                    "           </div>\n" +
                                    "           <div class=\"row my-2\">\n" +
                                    "               <div class=\"col-md-6\">\n" +
                                    "                   <label for=\"createEmpDepartment\" class=\"form-label\">Department:</label>\n" +
                                    "                   <select class=\"form-select\" id=\"createEmpDepartment\" name=\"createEmpDepartment\">\n" +
                                                            departHtml +
                                    "                   </select>\n" +
                                    "               </div>\n" +
                                    "               <div class=\"col-md-6\">\n" +
                                    "                   <label for=\"createEmpPosition\" class=\"form-label\">Position:</label>\n" +
                                    "                   <select class=\"form-select\" id=\"createEmpPosition\" name=\"createEmpPosition\">\n" +
                                                            posiHtml +
                                    "                   </select>\n" +
                                    "               </div>\n" +
                                    "           </div>\n" +
                                    "           <div class=\"row my-2\">\n" +
                                    "               <div class=\"col-md-6\">\n" +
                                    "                   <label for=\"createEmpEmail\" class=\"form-label\">Contract Email:</label>\n" +
                                    "                   <input type=\"email\" class=\"form-control\" id=\"createEmpEmail\" name=\"createEmpEmail\" require>\n" +
                                    "               </div>\n" +
                                    "               <div class=\"col-md-6\">\n" +
                                    "                   <label for=\"createEmpNumber\" class=\"form-label\">Contract Number:</label>\n" +
                                    "                   <input type=\"text\" class=\"form-control\" id=\"createEmpNumber\" name=\"createEmpNumber\" require>\n" +
                                    "               </div>\n" +
                                    "           </div>\n" +
                                    "           <div class=\"divider m-4\"></div>\n" +
                                    "           <h4><b>Employee's Login Account Infomation:</b></h4>\n" +
                                    "           <div class=\"row my-2\">\n" +
                                    "               <div class=\"col-md-6\">\n" +
                                    "                   <label for=\"createEmpUsername\" class=\"form-label\">Account:</label>\n" +
                                    "                   <input type=\"text\" class=\"form-control\" id=\"createEmpUsername\" name=\"createEmpUsername\" require>\n" +
                                    "               </div>\n" +
                                    "               <div class=\"col-md-6\">\n" +
                                    "                   <label for=\"createEmpPassword\" class=\"form-label\">Password:</label>\n" +
                                    "                   <input type=\"text\" class=\"form-control\" id=\"createEmpPassword\" name=\"createEmpPassword\" require>\n" +
                                    "               </div>\n" +
                                    "           </div>\n" +
                                    "           <div class=\"row my-2\">\n" +
                                    "               <div class=\"col-md-4\">\n" +
                                    "                   <label for=\"createEmpSalary\" class=\"form-label\">Base Salary:</label>\n" +
                                    "                   <input type=\"number\" class=\"form-control\" id=\"createEmpSalary\" name=\"createEmpSalary\" require>\n" +
                                    "               </div>\n" +
                                    "           </div>\n" +
                                    "        </div>\n" +
                                    "      </div>\n" +
                                    "      <div class=\"modal-footer\">\n" +
                                    "        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n" +
                                    "        <button type=\"submit\" class=\"btn btn-primary\">Create</button>\n" +
                                    "      </div>\n" +
                                    "     </form>\n" +
                                    "    </div>\n" +
                                    "  </div>\n" +
                                    "</div>");
                        //table
                        out.println("<thead class=\"gray\"><tr>" +
                                "<th><a href=\"control?tab=0&sort=0\" class=\"btn grey align-l\">ID</a></th>" +
                                "<th><a href=\"control?tab=0&sort=1\" class=\"btn grey align-l\">First Name</a></th>" +
                                "<th><a href=\"control?tab=0&sort=2\" class=\"btn grey align-l\">Last Name</a></th>" +
                                "<th><a href=\"control?tab=0&sort=3\" class=\"btn grey align-l\">Department</a></th>" +
                                "<th><a href=\"control?tab=0&sort=4\" class=\"btn grey align-l\">Position</a></th>" +
                                "<th><button class=\"btn grey align-l\">Action</button></th></tr></thead>");
                        out.println("<tbody>");
                        for (EmployeeStat emp : empList) {
                            out.println("<tr>");
                            out.println("<td>" + emp.getID() + "</td>");
                            out.println("<td>" + emp.getFirstName() + "</td>");
                            out.println("<td>" + emp.getLastName() + "</td>");
                            out.println("<td>" + ctrl.getDepartmentName(emp.getDepartmentID()) + "</td>");
                            out.println("<td>" + ctrl.getPositionName(emp.getPositionID()) + "</td>");
                            out.println("<td>" +
                                    "<a href=\"control?tab=0&page=" + page + "&emp=" + empList.indexOf(emp) + "&action=0\" role=\"button\">View</a> | " + 
                                    "<a href=\"control?tab=0&page=" + page + "&emp=" + empList.indexOf(emp) + "&action=1\" role=\"button\">Edit</a> | " +
                                    this.reconfirmDelEmp(emp.getID(), ctrl));
                            out.println("</tr>");
                        }
                        out.println("</tbody>");
                        out.println("</table>");
                        //pagination
                        out.println("<nav class=\"mt-3\">\n" +
                                    "  <ul class=\"pagination\">\n");
                        out.println("    <li class=\"page-item" + (page == 1 ? " disabled" : "") + "\"><a class=\"page-link\" href=\"control?tab=0&page=1\">First</a></li>\n");
                        if (page > 1) {
                            out.println("    <li class=\"page-item\"><a class=\"page-link\" href=\"control?tab=0&page=" + (page - 1) + "\">" + (page-1) + "</a></li>\n");
                        }
                        out.println("    <li class=\"page-item active\"><a class=\"page-link\" href=\"control?tab=0&page=" + page + "\">" + page + "</a></li>\n");
                        if (page < pageCount) {
                            out.println("    <li class=\"page-item\"><a class=\"page-link\" href=\"control?tab=0&page=" + (page + 1) + "\">" + (page + 1) + "</a></li>\n");
                        }
                        out.println("    <li class=\"page-item" + (page == pageCount ? " disabled" : "") + "\"><a class=\"page-link\" href=\"control?tab=0&page=" + pageCount + "\">Last</a></li>\n");
                        out.println("  </ul>\n" +
                                    "</nav>");
                    } else {
                        int action = Integer.parseInt(request.getParameter("action"));
                        int empid = Integer.parseInt(request.getParameter("emp"));
                        if (action == 1) {
                            //to add fine
                            if (!this.isParameterNullOrEmpty(request.getParameter("addFine"))) {
                                ctrl.addFine(empList.get(empid).getID(), Integer.parseInt(request.getParameter("fineCreateValue")), request.getParameter("fineCreateDesc"));
                                response.sendRedirect("control?tab=0&page=" + page + "&emp=" + empid + "&action=1");
                                return;
                            }
                            //edit fine
                            if (!this.isParameterNullOrEmpty(request.getParameter("editFine"))) {
                                int fineID = Integer.parseInt(request.getParameter("editFine"));
                                ctrl.editFine(fineID, Integer.parseInt(request.getParameter("fineEditValue")), request.getParameter("fineEditDesc"));
                                response.sendRedirect("control?tab=0&page=" + page + "&emp=" + empid + "&action=1");
                                return;
                            }
                            //delete fine
                            if (!this.isParameterNullOrEmpty(request.getParameter("delFine"))) {
                                int fineID = Integer.parseInt(request.getParameter("delFine"));
                                ctrl.removeFine(fineID);
                                response.sendRedirect("control?tab=0&page=" + page + "&emp=" + empid + "&action=1");
                                return;
                            }
                            //update empStat
                            if (!this.isParameterNullOrEmpty(request.getParameter("updateEmp"))) {
                                EmployeeStat emp = empList.get(empid);
                                emp.setFirstName(request.getParameter("empFName"));
                                emp.setLastName(request.getParameter("empLName"));
                                emp.setBirthDate(java.sql.Date.valueOf(request.getParameter("empBDate")));
                                emp.setDepartmentID(ctrl.getDepartmentID(request.getParameter("empDepartment")));
                                emp.setPositionID(ctrl.getPositionID(request.getParameter("empPosition")));
                                emp.setEmail(request.getParameter("empEmail"));
                                emp.setNumber(request.getParameter("empNumber"));
                                emp.setStrikes(Integer.parseInt(request.getParameter("empStrikes")));
                                emp.setAttendance(Integer.parseInt(request.getParameter("empAttend")));
                                emp.setLastAttend(LocalDate.now());
                                emp.setBaseSal(Integer.parseInt(request.getParameter("empSal")));
                                emp.setExtra(Integer.parseInt(request.getParameter("empExtra")));
                                ctrl.updateEmpStat(emp);
                                response.sendRedirect("control?tab=0&page=" + page + "&emp=" + empid + "&action=0");
                                return;
                            }
                        }
                        EmployeeStat empSt = empList.get(empid);
                        if (action == 0 || action == 1) {
                            EmpViewNEdit(out, request, empSt, ctrl, action == 1);
                        }
                    }
                    break;
                case 1:
                    out.println("<ul class=\"nav nav-tabs\">\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=0\">Employee</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link active\" href=\"control?tab=1\">Tasks</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=2\">Department</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=3\">Position</a>\n" +
                            "   </li>\n" +
                            "</ul>");
                    break;
                case 2:
                    out.println("<ul class=\"nav nav-tabs\">\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=0\">Employee</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=1\">Tasks</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link active\" href=\"control?tab=2\">Department</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=3\">Position</a>\n" +
                            "   </li>\n" +
                            "</ul>");
                    break;
                case 3:
                    out.println("<ul class=\"nav nav-tabs\">\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=0\">Employee</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=1\">Tasks</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"control?tab=2\">Department</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link active\" href=\"control?tab=3\">Position</a>\n" +
                            "   </li>\n" +
                            "</ul>");
                    break;
            }
            out.println("</div>");
            out.println("<script>" + 
                    "function updateAttend() {" +
                    "var now = new Date();" +
                    "var att = document.getElementById('attendance').value;" +
                    "var max = now.getDate();" +
                    "var miss = max - att;" +
                    "document.getElementById('attendStat').innerText = 'Attended: ' + att + ', Missed: ' + miss;" +
                    "}" +
                    "</script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
            out.println("</body>");
            out.println("</html>");
            
        }
    }

    private void EmpViewNEdit(final PrintWriter out, HttpServletRequest request, EmployeeStat empSt, EmployeeCtrl ctrl, boolean edit) {
        out.println("<form action=\"control?tab=0&page=" + request.getParameter("page") + "&emp=" + request.getParameter("emp") + "&action=1&updateEmp=1\" method=POST id=\"EditEmpForm\">");
        //first row
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col-sm-2 me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">ID</label>\n" +
                "   <input type=\"number\" name=\"empID\" class=\"form-control\" value=\"" + empSt.getID() + "\" readonly>\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2 me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">First Name</label>\n" +
                "   <input type=\"text\" name=\"empFName\" class=\"form-control\" value=\"" + empSt.getFirstName() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2 ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Last Name</label>\n" +
                "   <input type=\"text\" name=\"empLName\" class=\"form-control\" value=\"" + empSt.getLastName() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col-sm-3 ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Birth Date</label>\n" +
                "   <input type=\"date\" name=\"empBDate\" class=\"form-control\" value=\"" + empSt.getBirthDate().toString() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("</div>");
        //second row
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col input-group me-2\">\n" +
                "   <label class=\"input-group-text\" for=\"departmentPick\">Department</label>\n" +
                "   <select class=\"form-select\" name=\"empDepartment\" id=\"departmentPick\"" + (!edit ? " disabled" : "") + ">\n");
        List<Department> departments = ctrl.getDepartments();
        for (Department d : departments) {
            out.println("<option" + (d.getId() == empSt.getDepartmentID() ? " selected" : "") + ">" + d.getName() + "</option>\n");
        }
        out.println("   </select>\n" +
                "</div>");
        out.println("<div class=\"col input-group ms-2\">\n" +
                "   <label class=\"input-group-text\" for=\"positionPick\">Position</label>\n" +
                "   <select class=\"form-select\" name=\"empPosition\" id=\"positionPick\"" + (!edit ? " disabled" : "") + ">\n");
        List<Position> positions = ctrl.getPositions();
        for (Position p : positions) {
            out.println("<option" + (p.getId() == empSt.getPositionID()? " selected" : "") + ">" + p.getName() + "</option>\n");
        }
        out.println("   </select>\n" +
                "</div>");
        out.println("</div>");
        //third row
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col-sm-7 me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Email</label>\n" +
                "   <input type=\"email\" name=\"empEmail\" class=\"form-control\" value=\"" + empSt.getEmail() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Contract Number</label>\n" +
                "   <input type=\"text\" name=\"empNumber\" class=\"form-control\" value=\"" + empSt.getNumber() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("</div>");
        //line
        out.println("<div class=\"row mt-4 mb-4\"><div class=\"divider\"></div></div>");
        //attendance
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col-sm-2 me-2\"><div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Strikes</label>\n" +
                "   <input type=\"number\" name=\"empStrikes\" class=\"form-control\" value=\"" + empSt.getStrikes() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div></div>");
        int maxAttend = LocalDate.now().getDayOfMonth();
        out.println("<div class=\"col-auto ms-2\"><label for=\"attendance\" id=\"attendStat\">Attended: " + empSt.getAttendance() + ", Missed: " + (maxAttend - empSt.getAttendance()) +"</label><i> (Last attendance in " + LocalDate.now().toString() + ")</i>\n" +
                "<input type=\"range\" name=\"empAttend\" class=\"form-range\" min=\"0\" max=\"" + maxAttend + "\" value=\"" + empSt.getAttendance() + "\" step=\"1\" id=\"attendance\"" + (!edit ? " disabled" : "") + " onInput=\"updateAttend()\">");
        out.println("</div></div>");
        //salary
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Base Salary</label>\n" +
                "   <input type=\"number\" name=\"empSal\" class=\"form-control\" value=\"" + empSt.getBaseSal() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Extra Money</label>\n" +
                "   <input type=\"number\" name=\"empExtra\" class=\"form-control\" value=\"" + empSt.getExtra() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("</div>");
        
        out.println("</form>");
        if (edit) {
            out.println("<div class=\"row justify-content-end\"><div class=\"col-sm-8\"></div> <a href=\"control?tab=0&page=" + request.getParameter("page") + "\" type=\"button\" class=\"btn btn-secondary col-auto me-2\">Close</a>\n" +
                        "<button type=\"submit\" class=\"btn btn-primary col-auto\" form=\"EditEmpForm\">Update Changes</button>\n");
        }
        //line
        out.println("<div class=\"row mt-4 mb-4\"><div class=\"divider\"></div></div>");
        //fine
        out.println("<h4><b>Fine:</b></h4>");
        List<Fine> fines = empSt.getFine();
        //create modal to create fine
        if (edit) {
            out.println("<div class=\"row\"><button type=\"button\" class=\"btn btn-primary col-auto\" data-bs-toggle=\"modal\" data-bs-target=\"#fineCreate\">Create Fine</button> <h5 class=\"col-auto\"><i>(Change will be made immediately)</i></h5></div>");
            out.println("<div class=\"modal fade\" id=\"fineCreate\" tabindex=\"-1\">\n" +
                        "   <form action=\"control?tab=0&page=" + request.getParameter("page") + "&emp=" + request.getParameter("emp") + "&action=1&addFine=1\" method=\"POST\">" +
                        "       <div class=\"modal-dialog\">\n" +
                        "           <div class=\"modal-content\">\n" +
                        "               <div class=\"modal-header\">\n" +
                        "                   <h5 class=\"modal-title\">Create a new fine</h5>\n" +
                        "                   <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n" +
                        "               </div>\n" +
                        "           <div class=\"modal-body\">\n" +
                        "           <div class=\"mb-3\">\n" +
                        "               <label for=\"fineCreateValue\">Fine Amount:</label>\n" +
                        "               <input type=\"number\" class=\"form-control\" id=\"fineCreateValue\" name=\"fineCreateValue\" required>\n" +
                        "           </div>\n" +
                        "           <div class=\"mb-3\">\n" +
                        "               <label for=\"fineCreateDesc\">Description:</label>\n" +
                        "               <textarea class=\"form-control\" id=\"fineCreateDesc\" name=\"fineCreateDesc\" required></textarea>\n" +
                        "           </div>\n" +
                        "      </div>\n" +
                        "      <div class=\"modal-footer\">\n" +
                        "           <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n" +
                        "           <input class=\"btn btn-primary\" type=\"submit\" value=\"Create\">\n" +
                        "      </div>\n" +
                        "   </form>\n" +
                        "</div>");
        }
        out.println("<table class=\"table table-hover\">");
        out.println("<thead><tr><th>Fine</th> <th>Description</th>" + (edit ? "<th></th>" : "") + "</tr></thead>");
        out.println("<tbody>");
        
        String editBtnHtml = "<button type=\"button me-1\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#fineEdit%d\">Edit</button>\n" +
                            "<a href=\"control?tab=0&page=" + request.getParameter("page") + "&emp=" + request.getParameter("emp") + "&action=1&delFine=%d\" type=\"button\" class=\"btn btn-secondary me-1\">Delete</a>\n" +
                            "<div class=\"modal fade\" id=\"fineEdit%d\" tabindex=\"-1\">\n" +
                            "   <form action=\"control?tab=0&page=" + request.getParameter("page") + "&emp=" + request.getParameter("emp") + "&action=1&editFine=%d\" method=\"POST\">" +
                            "       <div class=\"modal-dialog\">\n" +
                            "           <div class=\"modal-content\">\n" +
                            "               <div class=\"modal-header\">\n" +
                            "                   <h5 class=\"modal-title\">Edit fine</h5>\n" +
                            "                   <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n" +
                            "               </div>\n" +
                            "           <div class=\"modal-body\">\n" +
                            "           <div class=\"mb-3\">\n" +
                            "               <label for=\"fineEditValue\">Fine Amount:</label>\n" +
                            "               <input type=\"number\" class=\"form-control\" id=\"fineEditValue\" name=\"fineEditValue\" value=\"%d\" required>\n" +
                            "           </div>\n" +
                            "           <div class=\"mb-3\">\n" +
                            "               <label for=\"fineEditDesc\">Description:</label>\n" +
                            "               <textarea class=\"form-control\" id=\"fineEditDesc\" name=\"fineEditDesc\" required>%s</textarea>\n" +
                            "           </div>\n" +
                            "      </div>\n" +
                            "      <div class=\"modal-footer\">\n" +
                            "           <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n" +
                            "           <input class=\"btn btn-primary\" type=\"submit\" value=\"Save change\">\n" +
                            "      </div>\n" +
                            "   </form>\n" +
                            "</div>";
        for (Fine f : fines) {
            out.println("<tr><td>" + f.getFine() + "</td><td>" + f.getDesc() + "</td>" +
                    (edit ? "<td>" + String.format(editBtnHtml, f.getId(), f.getId(), f.getId(), f.getId(), f.getFine(), f.getDesc()) + "</td>" : "") + "</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        //log in infos
        
        out.println("<h4><b>Log in Info:</b></h4>");
        List<LoginInfo> loginInf = empSt.getLoginInfo();
        out.println("<table class=\"table table-hover\">");
        out.println("<thead><tr><th>Username</th> <th>Password</th>" + (edit ? "<th></th>" : "") + "</tr></thead>");
        out.println("<tbody>");
        for (LoginInfo l : loginInf) {
            out.println("<tr><td>" + l.getUsername() + "</td><td><div class=\"spoiler\">" + l.getPassword() + "</div></td></tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        
        if (!edit) {
            //tasks
            List<SmallTask> tasks = empSt.getTasks();
            out.println("<h4><b>Task:</b></h4>");
            out.println("<table class=\"table table-hover\">");
            out.println("<thead><tr><th>Name</th> <th>Description</th> <th>Assesment</th></tr></thead>");
            out.println("<tbody>");
            for (SmallTask t : tasks) {
                out.println("<tr><td>" + getShortDesc(t.getName()) + "</td><td>" + getShortDesc(t.getDesc()) + "</td><td>" + (t.isFinished() ? t.getMark() + "/100" : "Unfinished") + "</td></tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
        }
    }
    private String reconfirmDelEmp (int empID, EmployeeCtrl ctrl) {
        Employee e = ctrl.getEmployee(empID);
        String empInfoHtml = String.format("<h4><b>ID: %d</br>Full name: %s</br>Position: %s</br>Department: %s</b></h4>", empID, e.getFullName(), ctrl.getPositionName(e.getPositionID()), ctrl.getDepartmentName(e.getDepartmentID()));
        return  "<a href=\"\" data-bs-toggle=\"modal\" data-bs-target=\"#reconfirmDeleteEmp" + empID + "\">\n" +
                "  Delete\n" +
                "</a>\n" +
                "<div class=\"modal fade\" id=\"reconfirmDeleteEmp" + empID + "\" tabindex=\"-1\">\n" +
                "  <div class=\"modal-dialog\">\n" +
                "    <div class=\"modal-content\">\n" +
                "      <div class=\"modal-header\">\n" +
                "        <h5 class=\"modal-title\" id=\"confirmTitle\">Reconfirm Employee Removal</h5>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n" +
                "      </div>\n" +
                "      <div class=\"modal-body\">\n" +
                "           <h5>Are you sure removing this Employee ?</br></h5>\n" +
                empInfoHtml +
                "      </div>\n" +
                "      <div class=\"modal-footer\">\n" +
                "        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cancel</button>\n" +
                "        <a href=\"control?deleteEmp=" + empID + "\" type=\"button\" class=\"btn btn-primary\">Confirm</a>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>";
    }
    private String getShortDesc(String desc) {
        if (desc.length() > 30) {
            return desc.replace("\\n", "</br>").replace("\\t", "&tab").substring(0, 30) + "...";
        }
        return desc.replace("\\n", "</br>").replace("\\t", "&tab");
    }
    private boolean isParameterNullOrEmpty(String para) {
        return para == null || para.length() <= 0;
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
        processRequest(request, response);
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
