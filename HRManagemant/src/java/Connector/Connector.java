/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connector;

import Model.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Phong Linh
 */
public class Connector {
    protected Connection connection;
    public Connector()
    {
        try {
            String user = "sa";
            String pass = "123456";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=HRManagement";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
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
                   "  FROM [Employee]";
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet rs = statement.executeQuery();
           while(rs.next())
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
               list.add(e);
           }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
