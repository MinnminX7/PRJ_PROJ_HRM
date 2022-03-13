/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connector;

import Model.Employee;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong Linh
 */
public class EmployeeCtrl extends Connector {
    public Employee getEmployee(int id) {
        try {
            String sql = "SELECT [empid]\n" +
                    "      ,[fname]\n" +
                    "      ,[lname]\n" +
                    "      ,[birthdate]\n" +
                    "      ,[age]\n" +
                    "      ,[departmentid]\n" +
                    "      ,[positionid]\n" +
                    "      ,[email]\n" +
                    "      ,[number]\n" +
                    "  FROM [Employee]\n" +
                    "  WHERE [empid]=" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                Employee e = new Employee(
                        rs.getInt("empid"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getDate("birthdate"),
                        rs.getInt("age"),
                        rs.getInt("departmentid"),
                        rs.getInt("positionid"),
                        rs.getString("email"),
                        rs.getString("number")
                );
                return e;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Date getLastAttendance(int empID) {
        try {
            String sql = "SELECT [empid]\n" +
                    "      ,[lastattend]\n" +
                    "  FROM [EmployeeStatus]\n" +
                    "  WHERE [empid]=" + empID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                Date lastattend = rs.getDate("lastattend");
                return lastattend;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Date.valueOf(LocalDate.now());
    }
    public int getAttendance(int empID) {
        try {
            String sql = "SELECT [empid]\n" +
                    "      ,[attendance]\n" +
                    "  FROM [EmployeeStatus]\n" +
                    "  WHERE [empid]=" + empID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getInt("attendance");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public int getStrikes(int empID) {
        try {
            String sql = "SELECT [empid]\n" +
                    "      ,[Strikes]\n" +
                    "  FROM [EmployeeStatus]\n" +
                    "  WHERE [empid]=" + empID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Strikes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public boolean attendToday(int empID) {
        try {
            String sql = "SELECT [empid]\n" +
                    "      ,[attendance]\n" +
                    "      ,[lastattend]\n" +
                    "  FROM [EmployeeStatus]\n" +
                    "  WHERE [empid]=" + empID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            Date lastattend = Date.valueOf(LocalDate.now());
            Date now = lastattend;
            int attendance = 0;
            if(rs.next())
            {
                lastattend = rs.getDate("lastattend");
                attendance = rs.getInt("attendance");
            }
            if (lastattend.before(now)) {
                //if last attendance is before today
                sql =   "UPDATE [EmployeeStatus]\n" +
                        "SET [attendance]=" + (++attendance) + ", [lastattend]=\'" + now.toString() + "\'\n" +
                        "WHERE [empid]=" + empID;
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public String getDepartmentName(int DepartmentID) {
        try {
            String sql = "SELECT [DepartmentID]\n" +
                    "      ,[Name]\n" +
                    "  FROM [Department]\n" +
                    "  WHERE [DepartmentID]=" + DepartmentID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getString("Name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NONE";
    }
    public String getPositionName(int PositionID) {
        try {
            String sql = "SELECT [PositionID]\n" +
                    "      ,[Name]\n" +
                    "  FROM [Position]\n" +
                    "  WHERE [PositionID]=" + PositionID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getString("Name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NONE";
    }
}
