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
                                    "<a href=\"control?tab=0&page=" + page + "&emp=" + empList.indexOf(emp) + "&action=2\" role=\"button\">Delete</a>");
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
                        EmployeeStat empSt = empList.get(Integer.parseInt(request.getParameter("emp")));
                        if (action == 0 || action == 1) {
                            EmpViewNEdit(out, empSt, ctrl, action == 1);
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
                    "</script>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
            out.println("</body>");
            out.println("</html>");
            
        }
    }

    private void EmpViewNEdit(final PrintWriter out, EmployeeStat empSt, EmployeeCtrl ctrl, boolean edit) {
        //first row
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col-sm-2 me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">ID</label>\n" +
                "   <input type=\"number\" class=\"form-control\" value=\"" + empSt.getID() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2 me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">First Name</label>\n" +
                "   <input type=\"text\" class=\"form-control\" value=\"" + empSt.getFirstName() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2 ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Last Name</label>\n" +
                "   <input type=\"text\" class=\"form-control\" value=\"" + empSt.getLastName() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col-sm-3 ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Birth Date</label>\n" +
                "   <input type=\"date\" class=\"form-control\" value=\"" + empSt.getBirthDate().toString() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("</div>");
        //second row
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col input-group me-2\">\n" +
                "   <label class=\"input-group-text\" for=\"departmentPick\">Department:</label>\n" +
                "   <select class=\"form-select\" id=\"departmentPick\"" + (!edit ? " disabled" : "") + ">\n");
        List<Department> departments = ctrl.getDepartments();
        for (Department d : departments) {
            out.println("<option" + (d.getId() == empSt.getDepartmentID() ? " selected" : "") + ">" + d.getName() + "</option>\n");
        }
        out.println("   </select>\n" +
                "</div>");
        out.println("<div class=\"col input-group ms-2\">\n" +
                "   <label class=\"input-group-text\" for=\"positionPick\">Department:</label>\n" +
                "   <select class=\"form-select\" id=\"positionPick\"" + (!edit ? " disabled" : "") + ">\n");
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
                "   <input type=\"email\" class=\"form-control\" value=\"" + empSt.getEmail() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Contract Number</label>\n" +
                "   <input type=\"number\" class=\"form-control\" value=\"" + empSt.getNumber() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("</div>");
        //line
        out.println("<div class=\"row mt-4 mb-4\"><div class=\"divider\"></div></div>");
        //attendance
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col-sm-2 me-2\"><div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Strikes</label>\n" +
                "   <input type=\"number\" class=\"form-control\" value=\"" + empSt.getStrikes() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div></div>");
        int maxAttend = LocalDate.now().getDayOfMonth();
        out.println("<div class=\"col-auto ms-2\"><label for=\"attendance\">Attended: " + empSt.getAttendance() + ", Missed: " + (maxAttend - empSt.getAttendance()) +"<i>(Last attendance in " + empSt.getLastAttend().toString() + ")</i></label>\n" +
                "<input type=\"range\" class=\"form-range\" min=\"0\" max=\"" + maxAttend + "\" value=\"" + empSt.getAttendance() + "\" step=\"1\" id=\"attendance\"" + (!edit ? " disabled" : "") + ">");
        out.println("</div></div>");
        //salary
        out.println("<div class=\"row mt-4 mb-4\">");
        out.println("<div class=\"col me-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Base Salary:</label>\n" +
                "   <input type=\"number\" class=\"form-control\" value=\"" + empSt.getBaseSal() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("<div class=\"col ms-2\">");
        out.println("<div class=\"input-group\">\n" +
                "   <label class=\"input-group-text\">Extra Money:</label>\n" +
                "   <input type=\"number\" class=\"form-control\" value=\"" + empSt.getExtra() + "\"" + (!edit ? " readonly" : "") + ">\n" +
                        "</div>");
        out.println("</div>");
        out.println("</div>");
        //fine
        List<Fine> fines = empSt.getFine();
        //create modal to create fine
        if (edit) {
            out.println("<button type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#fineCreate\">Create Fine</button>");
            out.println("<div class=\"modal fade\" id=\"fineCreate\" tabindex=\"-1\">\n" +
                        "  <div class=\"modal-dialog\">\n" +
                        "    <div class=\"modal-content\">\n" +
                        "      <div class=\"modal-header\">\n" +
                        "        <h5 class=\"modal-title\">Create a new fine</h5>\n" +
                        "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n" +
                        "      </div>\n" +
                        "      <div class=\"modal-body\">\n" +
                        "        <form>\n" +
                        "          <div class=\"mb-3\">\n" +
                        "            <label for=\"recipient-name\" class=\"col-form-label\">Fine Amount:</label>\n" +
                        "            <input type=\"number\" class=\"form-control\" id=\"fineCreateValue\">\n" +
                        "          </div>\n" +
                        "          <div class=\"mb-3\">\n" +
                        "            <label for=\"message-text\" class=\"col-form-label\">Description:</label>\n" +
                        "            <textarea class=\"form-control\" id=\"fineCreateDesc\"></textarea>\n" +
                        "          </div>\n" +
                        "        </form>\n" +
                        "      </div>\n" +
                        "      <div class=\"modal-footer\">\n" +
                        "        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n" +
                        "        <button type=\"button\" class=\"btn btn-primary\">Create</button>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>");
        }
        
        out.println("<table class=\"table table-hover\">");
        out.println("<thead><tr><th>Fine</th> <th>Description</th>" + (edit ? "<th></th>" : "") + "</tr></thead>");
        out.println("<tbody>");
        for (Fine f : fines) {
            out.println("<tr><td>" + f.getFine() + "</td><td>" + f.getDesc() + "</td>" + (edit ? "<td>Edit</td>" : "") + "</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        //log in infos
        List<LoginInfo> loginInf = empSt.getLoginInfo();
        out.println("<table class=\"table table-hover\">");
        out.println("<thead><tr><th>Username</th> <th>Password</th>" + (edit ? "<th></th>" : "") + "</tr></thead>");
        out.println("<tbody>");
        for (LoginInfo l : loginInf) {
            out.println("<tr><td>" + l.getUsername() + "</td><td><div class=\"spoiler\">" + l.getPassword() + "</div></td></tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        //tasks
        List<SmallTask> tasks = empSt.getTasks();
        out.println("<table class=\"table table-hover\">");
        out.println("<thead><tr><th>Name</th> <th>Description</th> <th>Assesment</th></tr></thead>");
        out.println("<tbody>");
        for (SmallTask t : tasks) {
            out.println("<tr><td>" + getShortDesc(t.getName()) + "</td><td>" + getShortDesc(t.getDesc()) + "</td><td>" + (t.isFinished() ? t.getMark() + "/100" : "Unfinished") + "</td></tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
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
