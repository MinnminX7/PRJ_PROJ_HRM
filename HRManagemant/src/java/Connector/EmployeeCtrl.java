/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connector;

import Model.Department;
import Model.Employee;
import Model.EmployeeStat;
import Model.Fine;
import Model.EmployeeStat.LoginInfo;
import Model.EmployeeStat.SmallTask;
import Model.Position;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public int getEmpCount() {
        try {
            String sql = "SELECT [EmpID]\n" +
                    "      , COUNT(EmpID) as [count]\n" +
                    "  FROM [Employee]\n" +
                    "  GROUP BY [EmpID]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public List<EmployeeStat> getEmpStats(int curPage, int pageLen) {
        return this.getEmpStats(curPage, pageLen, "empid");
    }
    public List<EmployeeStat> getEmpStats(int curPage, int pageLen, String sortBy) {
        int empCount = this.getEmpCount();
        return this.get_EmpStat(curPage * pageLen + 1, curPage * pageLen + pageLen, sortBy);
    }
    private List<EmployeeStat> get_EmpStat(int fromRow, int toRow, String sortBy) {
        List<EmployeeStat> list = new ArrayList<>();
        String sort = getSortColumn(sortBy);
        try {
            String sql = "SELECT e.rownum\n" +
                    "      ,e.[empid]\n" +
                    "      ,e.[fname]\n" +
                    "      ,e.[lname]\n" +
                    "      ,e.[birthdate]\n" +
                    "      ,e.[departmentid]\n" +
                    "      ,e.[positionid]\n" +
                    "      ,e.[email]\n" +
                    "      ,e.[number]\n" +
                    "      ,es.[Attendance]\n" +
                    "      ,es.[LastAttend]\n" +
                    "      ,es.[Strikes]\n" +
                    "      ,s.[BaseSal]\n" +
                    "      ,s.[Extra]\n" +
                    "  FROM (SELECT ROW_NUMBER() OVER (ORDER BY [" + sort +"] ASC) as rownum, * FROM [Employee]) as e\n" +
                    "  LEFT JOIN [EmployeeStatus] es ON e.[empid]=es.[empid]\n" +
                    "  LEFT JOIN [Salary] s ON e.[empid]=s.[empid]\n" +
                    "  WHERE [rownum]>=" + fromRow + " AND [rownum]<=" + toRow;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                EmployeeStat e = new EmployeeStat();
                e.setID(rs.getInt("empid"));
                e.setFirstName(rs.getString("fname"));
                e.setLastName(rs.getString("lname"));
                e.setBirthDate(rs.getDate("birthdate"));
                e.setDepartmentID(rs.getInt("departmentid"));
                e.setPositionID(rs.getInt("positionid"));
                e.setEmail(rs.getString("email"));
                e.setNumber(rs.getString("number"));
                e.setAttendance(rs.getInt("Attendance"));
                e.setLastAttend(rs.getDate("LastAttend").toLocalDate());
                e.setStrikes(rs.getInt("Strikes"));
                e.setBaseSal(rs.getInt("BaseSal"));
                e.setExtra(rs.getInt("Extra"));
                //get fines
                e.setFine(getFine(e.getID()));
                //get login infos
                String sql_login = "SELECT [empid]\n" +
                        "   , [Account]\n" +
                        "   , [Password]\n" +
                        "   FROM [LogInInfo] e\n" +
                        "   WHERE [empid]=" + e.getID();
                List<LoginInfo> loginInf = new ArrayList<>();
                ResultSet rs_login = connection.prepareStatement(sql_login).executeQuery();
                while (rs_login.next()) {
                    LoginInfo inf = e.new LoginInfo();
                    inf.setUsername(rs_login.getString("Account"));
                    inf.setPassword(rs_login.getString("Password"));
                    loginInf.add(inf);
                }
                e.setLoginInfo(loginInf);
                //get tasks
                String sql_task = "SELECT s.[id]\n" +
                        "  , t.[Name]\n" +
                        "  , t.[Desc]\n" +
                        "  , s.[Mark]\n" +
                        "  , t.[Deadline]\n" +
                        "  FROM [StaffTask] s JOIN [TaskInfo] t ON s.id=t.id AND s.[EmpID]=" + e.getID();
                List<SmallTask> tasks = new ArrayList<>();
                ResultSet rs_task = connection.prepareStatement(sql_task).executeQuery();
                while (rs_task.next()) {
                    SmallTask st = e.new SmallTask();
                    st.setId(rs_task.getInt("id"));
                    st.setMark(rs_task.getInt("Mark"));
                    st.setName(rs_task.getString("Name"));
                    st.setDesc(rs_task.getString("Desc"));
                    st.setFinished(rs_task.getTimestamp("Deadline").toLocalDateTime().isBefore(LocalDateTime.now()));
                    tasks.add(st);
                }
                e.setTasks(tasks);
                
                list.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    private String getSortColumn (String sortBy) {
        if (sortBy == null || sortBy.length() <= 0) {
            return "empid";
        }
        switch (sortBy) {
            case "0":
            default:
                return "empid";
            case "1":
                return "fname";
            case "2":
                return "lname";
            case "3":
                return "departmentid";
            case "4":
                return "positionid";
        }
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
    public List<Department> getDepartments() {
        List<Department> list = new ArrayList<>();
        try {
            String sql = "SELECT [DepartmentID]\n" +
                    "      ,[Name]\n" +
                    "  FROM [Department]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                list.add(new Department(rs.getInt("DepartmentID"), rs.getString("Name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public List<Position> getPositions() {
        List<Position> list = new ArrayList<>();
        try {
            String sql = "SELECT [PositionID]\n" +
                    "      ,[Name]\n" +
                    "  FROM [Position]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                list.add(new Position(rs.getInt("PositionID"), rs.getString("Name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public List<Fine> getFine(int empid) {
        List<Fine> fines = new ArrayList<>();
        try {
            String sql_fine = "SELECT [id]\n" +
                    "   , [empid]\n" +
                    "   , [Fine]\n" +
                    "   , [Desc]\n" +
                    "   FROM [Fine]\n" +
                    "   WHERE [empid]=" + empid;
            ResultSet rs_fine = connection.prepareStatement(sql_fine).executeQuery();
            while (rs_fine.next()) {
                Fine fine = new Fine();
                fine.setId(rs_fine.getInt("id"));
                fine.setFine(rs_fine.getInt("Fine"));
                fine.setDesc(rs_fine.getString("Desc"));
                fines.add(fine);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fines;
    }
    public void addFine (int empID, int value, String desc) {
        try {
            String sql = "INSERT INTO Fine (empid, fine, [desc]) VALUES\n" +
                        String.format("(%d, %d, '%s')", empID, value, desc);
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void editFine (int id, int newValue, String newDesc) {
        try {
            String sql = "UPDATE Fine\n" +
                    String.format("SET fine=%d, [desc]=%s\n", newValue, newDesc) +
                    String.format("WHERE id=%d", id);
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removeFine(int id) {
        try {
            String sql = "DELETE FROM Fine\n" +
                    String.format("WHERE id=%d", id);
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
