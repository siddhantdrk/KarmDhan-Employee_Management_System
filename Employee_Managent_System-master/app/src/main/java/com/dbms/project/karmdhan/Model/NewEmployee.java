package com.dbms.project.karmdhan.Model;

public class NewEmployee {
    int employeeNumber;
    String employeeName;
    String employeeJobClass;

    public NewEmployee(int employeeNumber, String employeeName, String employeeJobClass) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeJobClass = employeeJobClass;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeJobClass() {
        return employeeJobClass;
    }

    public void setEmployeeJobClass(String employeeJobClass) {
        this.employeeJobClass = employeeJobClass;
    }
}
