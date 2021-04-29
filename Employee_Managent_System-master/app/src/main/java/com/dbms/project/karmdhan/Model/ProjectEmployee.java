package com.dbms.project.karmdhan.Model;

public class ProjectEmployee {
    private int projectNumber;
    private int employeeNumber;
    private double chargePerHour;
    private double hoursBilled;

    public ProjectEmployee(int projectNumber, int employeeNumber, double chargePerHour, double hoursBilled) {
        this.projectNumber = projectNumber;
        this.employeeNumber = employeeNumber;
        this.chargePerHour = chargePerHour;
        this.hoursBilled = hoursBilled;
    }

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
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
}
