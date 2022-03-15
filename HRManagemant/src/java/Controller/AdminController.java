/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Connector.EmployeeCtrl;
import Model.Employee;
import Model.EmployeeStat;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AdminController extends HttpServlet {

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
                    ".container {margin-top:30px}" +
                    ".green {background-color: #57ff94 !important; font-size: 15px;}" +
                    ".red {background-color: #ff5757 !important; font-size: 15px;}" +
                    ".gray {background-color: #c7c7c7 !important; font-size: 15px;}" +
                    ".align-l {text-align: left !important;padding: 0;font-weight: bold;}" +
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
                    } else {
                        int action = Integer.parseInt(request.getParameter("action"));
                        EmployeeStat empSt = empList.get(Integer.parseInt(request.getParameter("emp")));
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
            out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>");
            out.println("</body>");
            out.println("</html>");
            
        }
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
