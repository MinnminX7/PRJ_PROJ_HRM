/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Phong Linh
 */
public class EmployeeStat extends Employee {
    //empStatus
    private int attendance;
    private LocalDate lastAttend;
    private int strikes;
    private int holidays;
    //salary
    private int baseSal;
    private int extra;
    //fine
    private List<Fine> fine;
    //login info
    private List<LoginInfo> loginInfo;
    //Tasks
    private List<Integer> tasksID;

    public EmployeeStat() {
    }

    public EmployeeStat(int attendance, LocalDate lastAttend, int strikes, int holidays, int baseSal, int extra, List<Fine> fine, List<LoginInfo> loginInfo, List<Integer> tasksID, int ID, String FirstName, String LastName, Date BirthDate, int Age, int DepartmentID, int PositionID, String Email, String Number) {
        super(ID, FirstName, LastName, BirthDate, Age, DepartmentID, PositionID, Email, Number);
        this.attendance = attendance;
        this.lastAttend = lastAttend;
        this.strikes = strikes;
        this.holidays = holidays;
        this.baseSal = baseSal;
        this.extra = extra;
        this.fine = fine;
        this.loginInfo = loginInfo;
        this.tasksID = tasksID;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public LocalDate getLastAttend() {
        return lastAttend;
    }

    public void setLastAttend(LocalDate lastAttend) {
        this.lastAttend = lastAttend;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public int getHolidays() {
        return holidays;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public int getBaseSal() {
        return baseSal;
    }

    public void setBaseSal(int baseSal) {
        this.baseSal = baseSal;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public List<Fine> getFine() {
        return fine;
    }

    public void setFine(List<Fine> fine) {
        this.fine = fine;
    }

    public List<LoginInfo> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(List<LoginInfo> loginInfo) {
        this.loginInfo = loginInfo;
    }

    public List<Integer> getTasksID() {
        return tasksID;
    }

    public void setTasksID(List<Integer> tasksID) {
        this.tasksID = tasksID;
    }
    
    public LoginInfo getDefaultLoginInfo() {
        if (this.loginInfo.size() <= 0) {
            return null;
        }
        return this.loginInfo.get(0);
    }
    
    public class Fine {
        private int Fine;
        private String Desc;

        public Fine() {
        }

        public Fine(int Fine, String Desc) {
            this.Fine = Fine;
            this.Desc = Desc;
        }

        public int getFine() {
            return Fine;
        }

        public void setFine(int Fine) {
            this.Fine = Fine;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String Desc) {
            this.Desc = Desc;
        }
    }
    public class LoginInfo {
        private String username;
        private String password;

        public LoginInfo() {
        }

        public LoginInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
    }
}
