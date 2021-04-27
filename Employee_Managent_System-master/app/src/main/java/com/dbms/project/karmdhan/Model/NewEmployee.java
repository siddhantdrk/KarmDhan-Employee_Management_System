package com.dbms.project.karmdhan.Model;

public class NewEmployee {
    int employeeNumber;
    String employeeName;
    String employeeJobClass;
    String employeePassword;


    public NewEmployee(int employeeNumber, String employeeName, String employeeJobClass, String employeePassword) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeJobClass = employeeJobClass;
        this.employeePassword = employeePassword;
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

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
}
