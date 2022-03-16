/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connector;

import Model.Task;
import Model.Task.Member;
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
public class TaskCtrl extends Connector {
    public int getTaskCount (int empID) {
        try {
            String sql = "SELECT [EmpID]\n" +
                    "      , COUNT(EmpID) as [count]\n" +
                    "  FROM [StaffTask]\n" +
                    "  WHERE [EmpID]=" + empID +
                    "  GROUP BY [EmpID]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public int getUnseenTaskCount (int empID) {
        try {
            String sql = "SELECT [EmpID]\n" +
                    "      , COUNT(EmpID) as [count]\n" +
                    "  FROM [StaffTask]\n" +
                    "  WHERE [EmpID]=" + empID + " AND [Seen]=0" +
                    "  GROUP BY [EmpID]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public void sawTask (int empID, int taskID) {
        try {
            String sql = "UPDATE [StaffTask]" +
                    "SET [Seen]=1" + 
                    "WHERE [EmpID]=" + empID + " AND [id]=" + taskID;
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaskCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Task> getTasks (int empID, int curPage, int pageLen) {
        int taskCount = this.getTaskCount(empID);
        return this.get_Tasks(empID, curPage * pageLen + 1, curPage * pageLen + pageLen); //row_number start from 1
    }
    private List<Task> get_Tasks (int empID, int fromRow, int toRow) {
        List<Task> tasks = new ArrayList<>();
        try {
            String sql = "SELECT t.[id]\n" +
                    "      , t.[Name]\n" +
                    "      , t.[Desc]\n" +
                    "      , t.[Deadline]\n" +
                    "      , s.[Seen]\n" +
                    "      , s.[Mark]\n" +
                    "  FROM (SELECT ROW_NUMBER() OVER (ORDER BY id DESC) as rownum, id, Name, [Desc], Deadline FROM TaskInfo) as t JOIN [StaffTask] s ON s.id = t.id\n" +
                    "  WHERE [EmpID]=" + empID + " AND t.rownum >=" + fromRow + " AND t.rownum <=" + toRow;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("Name").replace("\\n", "\n").replace("\\t", "\t"));
                t.setDesc(rs.getString("Desc").replace("\\n", "\n").replace("\\t", "\t"));
                t.setDeadline(rs.getTimestamp("Deadline").toLocalDateTime());
                t.setSeen(rs.getBoolean("Seen"));
                t.setAssesment(rs.getInt("Mark"));
                sql = "SELECT t.[id]\n" +
                    "      , e.[EmpID]\n" +
                    "      , e.[FName]\n" +
                    "      , e.[LName]\n" +
                    "      , e.[Email]\n" +
                    "      , e.[Number]\n" +
                    "  FROM (SELECT id, EmpID FROM [StaffTask] WHERE id=" + t.getId() + ") as t JOIN [Employee] e ON t.EmpID = e.EmpID\n";
                PreparedStatement statement2 = connection.prepareStatement(sql);
                ResultSet rs2 = statement2.executeQuery();
                List<Member> members = new ArrayList<>();
                while (rs2.next()) {
                    Member m = t.new Member();
                    m.setEmpID(rs2.getInt("EmpID"));
                    m.setFName(rs2.getString("FName"));
                    m.setLName(rs2.getString("LName"));
                    m.setEmail(rs2.getString("Email"));
                    m.setNumber(rs2.getString("Number"));
                    members.add(m);
                }
                t.setAssignedEmp(members);
                tasks.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasks;
    }
}
