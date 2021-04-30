package com.dbms.project.karmdhan.Model;

import java.util.List;

public class Project {
    private int projectNumber;
    private String projectName;
    private int projectLeaderEmployeeNumber;
    private double chargePerHour, hoursBilled;
    private String projectLeaderName;
    private List<Employee> projectEmployeeList;

    public Project(int projectNumber, String projectName, int projectLeaderEmployeeNumber) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeaderEmployeeNumber = projectLeaderEmployeeNumber;
    }

    public Project(int projectNumber, String projectName, int projectLeaderEmployeeNumber, String projectLeaderName) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeaderEmployeeNumber = projectLeaderEmployeeNumber;
        this.projectLeaderName = projectLeaderName;
    }

    public Project(int projectNumber, String projectName, int projectLeaderEmployeeNumber, double chargePerHour, double hoursBilled, String projectLeaderName) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeaderEmployeeNumber = projectLeaderEmployeeNumber;
        this.chargePerHour = chargePerHour;
        this.hoursBilled = hoursBilled;
        this.projectLeaderName = projectLeaderName;
    }

    public Project(int projectNumber, String projectName, int projectLeaderEmployeeNumber, List<Employee> projectEmployeeList) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeaderEmployeeNumber = projectLeaderEmployeeNumber;
        this.projectEmployeeList = projectEmployeeList;
    }

    public List<Employee> getProjectEmployeeList() {
        return projectEmployeeList;
    }

    public void setProjectEmployeeList(List<Employee> projectEmployeeList) {
        this.projectEmployeeList = projectEmployeeList;
    }

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectLeaderEmployeeNumber() {
        return projectLeaderEmployeeNumber;
    }

    public void setProjectLeaderEmployeeNumber(int projectLeaderEmployeeNumber) {
        this.projectLeaderEmployeeNumber = projectLeaderEmployeeNumber;
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

    public String getProjectLeaderName() {
        return projectLeaderName;
    }

    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }
}
