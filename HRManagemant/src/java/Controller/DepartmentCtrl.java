/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Connector.EmployeeCtrl;
import Model.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Phong Linh
 */
public class DepartmentCtrl extends HttpServlet {

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
        //validate user
        HttpSession session = request.getSession();
        Employee emp = (Employee)session.getAttribute("currentEmp");
        if (emp == null || !(emp.getID() == 0 || emp.getPositionID() == 1)) {
            response.sendRedirect("MainPage");
            return;
        }
        EmployeeCtrl control = new EmployeeCtrl();
        String mode = request.getParameter("mode");
        String id_String = request.getParameter("id");
        int id = -1;
        switch(mode) {
            case "0": //create
                String Name = request.getParameter("name");
                control.createDepart(Name);
                break;
            case "1": //edit
                id = Integer.parseInt(id_String);
                String newName = request.getParameter("name");
                if (!isNullOrEmpty(newName)) {
                    control.editDepartment(id, newName);
                }
                break;
            case "2": //delete
                int replaceId = -1;
                id = Integer.parseInt(id_String);
                if (!isNullOrEmpty(request.getParameter("replaceId"))) {
                    replaceId = Integer.parseInt(request.getParameter("replaceId"));
                }
                control.deleteDepart(id, replaceId);
                break;
            default:
                break;
        }
        response.sendRedirect("control?tab=2");
    }

    private static boolean isNullOrEmpty(String id_String) {
        return id_String == null || id_String.length() <= 0;
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
