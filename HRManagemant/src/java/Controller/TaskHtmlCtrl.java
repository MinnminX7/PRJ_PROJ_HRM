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
import Model.Task.Member;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phong Linh
 */
public class TaskHtmlCtrl extends TaskCtrl {
    public String getTableOfTask (List<Task> list) {
        String rows = "";
        for (Task t : list) {
            rows += String.format("<tr> <td>%d</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </tr>",
                t.getId(), getShortVer(t.getName()), getShortVer(t.getDesc()), getRemainTime(t.getDeadline()), viewTaskModel(t) + "<a href=\"editTask?id=" + t.getId() + "\" class=\"btn btn-primary\">Edit</a>");
        }
        return  "<table class=\"table table-striped table-hover\">\n" +
                "   <thead><tr> <th>ID</th> <th>Name</th> <th>Description</th> <th>Status</th> <th>Action</th> </tr></thead>\n" +
                "   <tbody>\n" +
                rows +
                "   </tbody>\n" +
                "</table>";
    }
    private String viewTaskModel(Task t) {
        String memberHtml = "</br>-------------------";
        for (Member m : t.getAssignedEmp()) {
            memberHtml += String.format("</br>+ %s (%s/%s)", m.getFullName(), m.getEmail(), m.getNumber());
        }
        return "<button type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#editTask" + t.getId() + "\">\n" +
                "View\n" +
                "</button>\n" +
                "<div class=\"modal fade\" id=\"editTask" + t.getId() + "\">\n" +
                "  <div class=\"modal-dialog modal-lg modal-dialog-scrollable\">\n" +
                "    <div class=\"modal-content\">\n" +
                "     <form>\n" +
                "      <div class=\"modal-header\">\n" +
                "        <h5 class=\"modal-title\"><b>" + t.getName() + "</b></h5>\n" +
                "        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n" +
                "      </div>\n" +
                "      <div class=\"modal-body\">\n" +
                "           <div class=\"m-2\">\n" + t.getDesc() + memberHtml +
                "           </div>\n" +
                "      </div>\n" +
                "      <div class=\"modal-footer\">\n" +
                "        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n" +
                "        <a href=\"editTask?id=" + t.getId() + "\" type=\"button\" class=\"btn btn-primary\">Edit</a>\n" +
                "      </div>\n" +
                "     </form>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>";
    }
    public String getSearchMember(String type, String search) {
        String html = "";
        try {
            String sql = "SELECT e.[empid]\n" +
                    "      ,e.[fullname]\n" +
                    "      ,d.[name] as 'department'\n" +
                    "      ,p.[name] as 'position'\n" +
                    "      ,e.[email]\n" +
                    "      ,e.[number]\n" +
                    "  FROM (SELECT emp.[FName] + ' ' + emp.[LName] AS 'fullname', emp.[empid], emp.[email], emp.[number], emp.[departmentid], emp.[positionid] FROM [Employee] emp) as e JOIN [Department] d ON e.[departmentid]=d.[departmentid] JOIN [Position] p ON e.[positionid]=p.[positionid]\n" +
                    this.getConditionString(type, search);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {    
                return "<h5><i>No Result found</i></h5>";
            }
            while(rs.next())
            {
                html += String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </tr>",
                        "<input type='checkbox' onchange=\"onAddEmp(this," + rs.getInt("empid") + ",\'" + rs.getString("fullname") + "\',\'" + rs.getString("email") + "\',\'" + rs.getString("number") + "\')\">",
                        rs.getString("fullname"), rs.getString("department"), rs.getString("position"), rs.getString("email") + " / " + rs.getString("number"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskHtmlCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "<table class=\"table table-striped table-hover\" id=\"foundTable\">\n" +
                "   <thead><tr> <th>#</th> <th>Name</th> <th>Department</th> <th>Position</th> <th>Email/Number</th> </tr></thead>\n" +
                "   <tbody>\n" +
                html +
                "   </tbody>\n" +
                "</table>";
    }
    private String getConditionString (String type, String search) {
        if (search.length() <= 0) {
            return "";
        }
        String format;
        switch (type) {
            case "1": //department
                format = "WHERE d.[name] LIKE '%s'";
                break;
            case "2":
                format = "WHERE p.[name] LIKE '%s'";
                break;
            case "3":
                format = "WHERE e.[number] LIKE '%s'";
                break;
            default:
                format = "WHERE e.[fullname] LIKE '%s'";
                break;
        }
        return String.format(format, "%" + search + "%");
    }
    public String getMemberOption(int id, String fullname, String email, String number) {
        String contract = fullname + " (" + email + "/" + number + ")";
        return "<input form='editTaskForm' type='checkbox' name='members_' id='Emp" + id + "' onchange='uncheckMember(this)' value='" + id + "' checked>" +
                "<label id='Emp" + id + "_label' for='Emp" + id + "'> " + contract + "</label></br>";
    }
    public String getMemberOption(int id) {
        try {
            String sql = "SELECT e.[empid]\n" +
                    "      ,e.[fname] + ' ' + e.[lname] as 'fullname'\n" +
                    "      ,e.[email]\n" +
                    "      ,e.[number]\n" +
                    "  FROM [Employee] e WHERE e.[empid]=" + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
                return this.getMemberOption(id, rs.getString("fullname"), rs.getString("email"), rs.getString("number"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskHtmlCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NONE";
    }
    public List<Member> getMembers(String[] ids) {
        Task t = new Task();
        List<Member> members = new ArrayList<>();
        try {
            String sql = "SELECT e.[empid]\n" +
                    "      ,e.[fname], e.[lname]\n" +
                    "      ,e.[email]\n" +
                    "      ,e.[number]\n" +
                    "  FROM [Employee] e WHERE e.[empid]=%s";
            for (String id : ids) {
                PreparedStatement statement = connection.prepareStatement(String.format(sql, id));
                ResultSet rs = statement.executeQuery();
                if (rs.next())
                {
                    Member m = t.new Member();
                    m.setEmpID(rs.getInt("empid"));
                    m.setFName(rs.getString("fname"));
                    m.setLName(rs.getString("lname"));
                    m.setEmail(rs.getString("email"));
                    m.setNumber(rs.getString("number"));
                    members.add(m);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TaskHtmlCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return members;
    }
    private String getShortVer(String s) {
        if (s.length() <= 15) {
            return s;
        }
        return s.substring(0, 15);
    }
    private String getRemainTime (LocalDateTime t) {
        LocalDateTime now = LocalDateTime.now();
        if (t.isBefore(now)) {
            return "Finished.";
        }
        long num = 0;
        num = now.until(t, ChronoUnit.YEARS);
        if (num > 0) {
            return num + " year" + (num == 1 ? "" : "s") + " remain.";
        }
        num = now.until(t, ChronoUnit.MONTHS);
        if (num > 0) {
            return num + " month" + (num == 1 ? "" : "s") + " remain.";
        }
        num = now.until(t, ChronoUnit.DAYS);
        if (num > 0) {
            return num + " day" + (num == 1 ? "" : "s") + " remain.";
        }
        String time = "";
        num = now.until(t, ChronoUnit.HOURS);
        if (num > 0) {
            time = num + "h";
        }
        num = Long.max(now.until(t, ChronoUnit.MINUTES), 0);
        return time += num + "m remain.";
    }
}
