package model.employee;

import models.BaseModel;

public class Employee extends BaseModel {

    private String employeeName;
    private String email;
    private java.sql.Date hireDate;
    private java.sql.Date terminationDate;
    private int departmentID;
    private int jobTitleID;
    private Integer managerID;


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.sql.Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(java.sql.Date hireDate) {
        this.hireDate = hireDate;
    }

    public java.sql.Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(java.sql.Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getJobTitleID() {
        return jobTitleID;
    }

    public void setJobTitleID(int jobTitleID) {
        this.jobTitleID = jobTitleID;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

}
