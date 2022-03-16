/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Phong Linh
 */
public class Task {
    private int id;
    private String Name;
    private String Desc;
    private LocalDateTime Deadline;
    private boolean Seen;
    private int Assesment;
    private List<Member> assignedEmp;

    public Task() {
    }

    public Task(int id, String Name, String Desc, LocalDateTime Deadline, boolean Seen, int Assesment, List<Member> assignedEmp) {
        this.id = id;
        this.Name = Name;
        this.Desc = Desc;
        this.Deadline = Deadline;
        this.Seen = Seen;
        this.Assesment = Assesment;
        this.assignedEmp = assignedEmp;
    }

    public Task(int id, String Name, String Desc, LocalDateTime Deadline, List<Member> assignedEmp) {
        this.id = id;
        this.Name = Name;
        this.Desc = Desc;
        this.Deadline = Deadline;
        this.assignedEmp = assignedEmp;
        this.Seen = false;
        this.Assesment = 0;
    }

    public boolean isSeen() {
        return Seen;
    }

    public int getAssesment() {
        return Assesment;
    }

    public void setAssesment(int Assesment) {
        this.Assesment = Assesment;
    }

    public void setSeen(boolean Seen) {
        this.Seen = Seen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public LocalDateTime getDeadline() {
        return Deadline;
    }

    public void setDeadline(LocalDateTime Deadline) {
        this.Deadline = Deadline;
    }

    public List<Member> getAssignedEmp() {
        return assignedEmp;
    }

    public void setAssignedEmp(List<Member> assignedEmp) {
        this.assignedEmp = assignedEmp;
    }
    
    public class Member {
        private int EmpID;
        private String FName;
        private String LName;
        private String Email;
        private String Number;
        
        public Member() {
            
        }
        
        public Member(int EmpID, String FName, String LName, String Email, String Number) {
            this.EmpID = EmpID;
            this.FName = FName;
            this.LName = LName;
            this.Email = Email;
            this.Number = Number;
        }
        
        public String getFullName() {
            return FName + " " + LName;
        }
        
        public int getEmpID() {
            return EmpID;
        }

        public void setEmpID(int EmpID) {
            this.EmpID = EmpID;
        }

        public String getFName() {
            return FName;
        }

        public void setFName(String FName) {
            this.FName = FName;
        }

        public String getLName() {
            return LName;
        }

        public void setLName(String LName) {
            this.LName = LName;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        @Override
        public String toString() {
            return this.toString(true);
        }
        
        public String toString(boolean includeID) {
            if (includeID) {
                return String.format("(%d) %s %s (%s/%s)", EmpID, FName, LName, Email, Number);
            }
            return String.format("%s %s (%s/%s)", FName, LName, Email, Number);
        }
    }
}
