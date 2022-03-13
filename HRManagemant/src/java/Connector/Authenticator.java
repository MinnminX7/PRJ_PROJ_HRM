/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong Linh
 */
public class Authenticator extends Connector {
    private final String SQL =  "SELECT %s [account], [password] FROM [%s]\n" +
                                "WHERE account='%s' AND password='%s'";
    public int Login (String account, String password) {
        if (account == null || password == null) {
            return -1;
        }
        //-1 for wrong acc/pass, 0 for adminstrator, otherwise return EmployeeID
        if (AdminAuth(account, password)) {
            return 0;
        }
        return MemberAuth(account, password);
    }
    private boolean AdminAuth(String account, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format(SQL, "", "AdminInfo", account, password));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    private int MemberAuth(String account, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format(SQL, "[empid],", "LogInInfo", account, password));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("empid");
            }
        } catch (SQLException e) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }
}
