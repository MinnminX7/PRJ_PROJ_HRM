/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Phong Linh
 */
public class Employee {
    private int ID;
    private String FirstName;
    private String LastName;
    private Date BirthDate;
    private int Age;
    private int DepartmentID;
    private int PositionID;
    private String Email;
    private String Number;

    public Employee() {
    }

    public Employee(int ID, String FirstName, String LastName, Date BirthDate, int Age, int DepartmentID, int PositionID, String Email, String Number) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.BirthDate = BirthDate;
        this.Age = Age;
        this.DepartmentID = DepartmentID;
        this.PositionID = PositionID;
        this.Email = Email;
        this.Number = Number;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date BirthDate) {
        this.BirthDate = BirthDate;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

    public int getPositionID() {
        return PositionID;
    }

    public void setPositionID(int PositionID) {
        this.PositionID = PositionID;
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
}
