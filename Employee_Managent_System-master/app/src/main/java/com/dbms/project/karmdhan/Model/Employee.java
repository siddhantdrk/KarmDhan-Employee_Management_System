package com.dbms.project.karmdhan.Model;

import java.util.List;

public class Employee {
    private int employeeNumber;
    private String employeeName;
    private String employeeJobClass;
    private String employeePassword;
    private double chargePerHour;
    private double hoursBilled;
    private List<Project> employeeProjectList;

    public Employee(int employeeNumber, String employeeName, String employeeJobClass, double chargePerHour, double hoursBilled) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeJobClass = employeeJobClass;
        this.chargePerHour = chargePerHour;
        this.hoursBilled = hoursBilled;
    }

    public Employee(int employeeNumber, String employeeName, String employeeJobClass, double chargePerHour, double hoursBilled, List<Project> employeeProjectList) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeJobClass = employeeJobClass;
        this.chargePerHour = chargePerHour;
        this.hoursBilled = hoursBilled;
        this.employeeProjectList = employeeProjectList;
    }

    public Employee(int employeeNumber, String employeeName, String employeeJobClass, String employeePassword) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeJobClass = employeeJobClass;
        this.employeePassword = employeePassword;
    }

    public Employee(int employeeNumber, String employeePassword) {
        this.employeeNumber = employeeNumber;
        this.employeePassword = employeePassword;
    }

    public Employee(int employeeNumber, String employeeName, String employeeJobClass) {
        this.employeeNumber = employeeNumber;
        this.employeeName = employeeName;
        this.employeeJobClass = employeeJobClass;
    }



    public List<Project> getEmployeeProjectList() {
        return employeeProjectList;
    }

    public void setEmployeeProjectList(List<Project> employeeProjectList) {
        this.employeeProjectList = employeeProjectList;
    }

    public double getChargePerHour() {
        return chargePerHour;
    }

    public void setChargePerHour(double chargePerHour) {
        this.chargePerHour = chargePerHour;
    }

    public double getHoursBilled() {
        return hoursBilled;
    }

    public void setHoursBilled(double hoursBilled) {
        this.hoursBilled = hoursBilled;
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

    public String toString() {
        return this.employeeNumber + " : " + this.employeeName + "\n" + this.employeeJobClass;
    }
}
