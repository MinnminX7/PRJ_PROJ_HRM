/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Connector.TaskCtrl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Phong Linh
 */
public class TaskMng extends HttpServlet {

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
        if (isNullOrEmpty(request.getParameter("name")) ||
                isNullOrEmpty(request.getParameter("desc")) ||
                isNullOrEmpty(request.getParameter("date")) ||
                request.getParameterValues("members_").length <= 0) {
            response.sendRedirect("Login");
            return;
        }
        TaskCtrl ctrl = new TaskCtrl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String date = Timestamp.valueOf(LocalDateTime.parse(request.getParameter("date"), formatter)).toString();
        if (isNullOrEmpty(request.getParameter("id")) || request.getParameter("id").equals("0")) {
            //create
            ctrl.createTask(request.getParameter("name"), request.getParameter("desc"), date, request.getParameterValues("members_"));
        } else {
            ctrl.editTask(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("desc"), date, request.getParameterValues("members_"));
        }
        response.sendRedirect("Login");
    }
    private boolean isNullOrEmpty(String s) {
        return s == null || s.length() <= 0;
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
