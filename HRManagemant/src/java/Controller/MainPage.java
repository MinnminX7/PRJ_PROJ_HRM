/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Connector.EmployeeCtrl;
import Connector.TaskCtrl;
import Model.Employee;
import Model.Task;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
public class MainPage extends HttpServlet {

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
            out.println("<title>Servlet MainPage</title>");            
            out.println("</head>");
            out.println("<body>");
            
            HttpSession session = request.getSession();
            Employee me = (Employee)session.getAttribute("currentEmp");
            Boolean isAttended = (Boolean)session.getAttribute("attended");
            
            out.println("<style>body{font-family:\"Noto Sans\", sans-serif;}\n" +
                    ".container {margin-top:30px}" +
                    ".head {font-size:40px;}" +
                    ".status {font-size:23px;margin-top:6px; margin-bottom:6px}" +
                    ".progress {height: 40px;}" +
                    ".green {background-color: #57ff94 !important; font-size: 15px;}" +
                    ".red {background-color: #ff5757 !important; font-size: 15px;}" +
                    ".gray {background-color: #c7c7c7 !important; font-size: 15px;}" +
                    ".seen {max-width: 100%; font-size: 20px; background-color: #c7c7c7;text-align: left;}" +
                    ".unseen {max-width: 100%; font-size: 20px; background-color: #fafafa;text-align: left;outline-color:red;outline-style:solid;outline-width: thin;}" +
                    ".btn-group-vertical {max-width: 100%}" +
                    "</style>");
            out.println("<div class=\"container\">");
            out.println("<p class=\"head\">Welcome : " + me.getFullName() + "</p>");
            //show attended msg
            if (!isAttended) {
                out.println("<div class=\"alert alert-info\" role=\"alert\">Attended " + date2String(LocalDate.now()) + "</div>");
            }
            
            int curTab = 0;
            if (request.getParameter("tab") != null && !request.getParameter("tab").isEmpty()) {
                curTab = Integer.parseInt(request.getParameter("tab"));
            }
            TaskCtrl taskCtrl = new TaskCtrl();
            int unseenTask = taskCtrl.getUnseenTaskCount(me.getID());
            String unseenNumNotif = "";
            if (unseenTask != 0) {
                unseenNumNotif = " <span class=\"badge red\">" + unseenTask + "</span>";
            }
            if (curTab == 0) {
                EmployeeCtrl control = new EmployeeCtrl();
                out.println("<ul class=\"nav nav-tabs\">\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link active\" href=\"MainPage?tab=0\">Status</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"MainPage?tab=1\">Task" + unseenNumNotif + "</a>\n" +
                            "   </li>\n" +
                            "</ul>");
                out.println("<div class=\"status\">Name:   " + me.getFullName() + "</div>");
                out.println("<div class=\"status\">Department:   " + control.getDepartmentName(me.getDepartmentID()) + "</div>");
                out.println("<div class=\"status\">Position:   " + control.getPositionName(me.getPositionID()) + "</div>");
                LocalDate lastattend = control.getLastAttendance(me.getID()).toLocalDate();
                int monthLen = LocalDate.now().lengthOfMonth();
                int attended = control.getAttendance(me.getID()) * 100 / monthLen;
                int missed = (lastattend.getDayOfMonth() - attended) * 100 / monthLen;
                out.println("<div class=\"status\">" + 
                            "   <lable for=\"attendProg\">Attendance:</label>" + 
                            "   <div class=\"progress mt-2 mb-2\" id=\"attendProg\">\n" +
                            "       <div class=\"progress-bar green\" role=\"progressbar\" style=\"width: " + attended + "%\" aria-valuenow=\"" + attended + "\" aria-valuemin=\"0\" aria-valuemax=\"100\">Attended</div>\n" +
                            "       <div class=\"progress-bar red\"  role=\"progressbar\" style=\"width: " + missed + "%\" aria-valuenow=\"" + missed + "\" aria-valuemin=\"0\" aria-valuemax=\"100\">Missed</div>\n" +
                            "       <div class=\"progress-bar gray\" role=\"progressbar\" style=\"width: " + (100 - attended - missed) +  "%\" aria-valuenow=\"" + (100 - attended - missed) + "\" aria-valuemin=\"0\" aria-valuemax=\"100\">Future</div>\n" +
                            "   </div>" +
                            "</div>");
                out.println("<div class=\"status\">Strike:");
                int strikes = control.getStrikes(me.getID());
                if (strikes == 0) {
                    out.println("<img src=\"resources/img/circle.svg\" data-bs-toggle=\"tooltip\" title=\"No Strike\" width=\"27\" height=\"27\">");
                } else {
                    for (int i = 0; i < strikes; i++) {
                        out.println("<img src=\"resources/img/x-lg.svg\" data-bs-toggle=\"tooltip\" title=\"Strike " + (i+1) + "\" width=\"27\" height=\"27\">");
                    }
                }
                out.println("</div>");
            } else if (curTab == 1) {
                out.println("<ul class=\"nav nav-tabs\">\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link\" href=\"MainPage?tab=0\">Status</a>\n" +
                            "   </li>\n" +
                            "   <li class=\"nav-item\">\n" +
                            "       <a class=\"nav-link active\" href=\"MainPage?tab=1\">Task" + unseenNumNotif + "</a>\n" +
                            "   </li>\n" +
                            "</ul>");
                int page = 1;
                if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
                    page = Integer.max(1, Integer.parseInt(request.getParameter("page")));
                }
                int taskCount = taskCtrl.getTaskCount(me.getID());
                int pageLen = 5;
                int pageCount = (taskCount - (taskCount % pageLen)) / pageLen + 1;
                List<Task> tasks = taskCtrl.getTasks(me.getID(), page-1, pageLen);
                if (request.getParameter("task") != null && !request.getParameter("task").isEmpty()) {
                    int taskIndex = Integer.parseInt(request.getParameter("task"));
                    Task task = tasks.get(taskIndex);
                    if (!task.isSeen()) {
                        taskCtrl.sawTask(me.getID(), task.getId());
                        task.setSeen(true);
                    }
                    out.println("<div class=\"card\">");
                    out.println("<div class=\"card-header row\"><div clas=\"col-md-2\"><a href=\"MainPage?tab=1&page=" + page + "\"><img class=\"back\" src=\"resources/img/arrow-left-square.svg\"></a></div> <div class=\"col-md-10\">" + toHtmlString(task.getName()) + "</div></div>");
                    out.println("<div class=\"card-body\">" + toHtmlString(task.getDesc()) + "</div>");
                    out.println("<div class=\"card-body\">Member: </br>");
                    for (int i = 0; i < task.getAssignedEmp().size(); i++) {
                        out.println(" - " + task.getAssignedEmp().get(i).toString(false) + "</br>");
                    }
                    out.println("</div>");
                    out.println("</div>");
                } else {
                    out.println("<div>");
                    for (int i = 0; i < tasks.size(); i++) {
                        out.println("<div class=\"row\"><a href=\"MainPage?tab=1&page=" + page + "&task=" + i + "\" class=\"btn mt-1" + (tasks.get(i).isSeen() ? " seen" : " unseen") +"\" role=\"button\">" + toHtmlString(tasks.get(i).getName()) + (tasks.get(i).isSeen() ? "" : "    <span class=\"badge red\">New</span>") + "</a></div>");
                    }
                    out.println("</div>");
                    //pagination
                    out.println("<nav class=\"mt-3\">\n" +
                                "  <ul class=\"pagination\">\n");
                    out.println("    <li class=\"page-item" + (page == 1 ? " disabled" : "") + "\"><a class=\"page-link\" href=\"MainPage?tab=1&page=1\">First</a></li>\n");
                    if (page > 1) {
                        out.println("    <li class=\"page-item\"><a class=\"page-link\" href=\"MainPage?tab=1&page=" + (page - 1) + "\">" + (page-1) + "</a></li>\n");
                    }
                    out.println("    <li class=\"page-item active\"><a class=\"page-link\" href=\"MainPage?tab=1&page=" + page + "\">" + page + "</a></li>\n");
                    if (page < pageCount) {
                        out.println("    <li class=\"page-item\"><a class=\"page-link\" href=\"MainPage?tab=1&page=" + (page + 1) + "\">" + (page + 1) + "</a></li>\n");
                    }
                    out.println("    <li class=\"page-item" + (page == pageCount ? " disabled" : "") + "\"><a class=\"page-link\" href=\"MainPage?tab=1&page=" + pageCount + "\">Last</a></li>\n");
                    out.println("  </ul>\n" +
                                "</nav>");
                }
            }
            
            out.println("</div>");
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private String date2String(LocalDate d) {
        return String.format("%02d/%02d/%d", d.getDayOfMonth(), d.getMonthValue(), d.getYear());
    }
    private String toHtmlString(String s) {
        return s.replace("\n", "</br>").replace("\t", "&tab");
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
