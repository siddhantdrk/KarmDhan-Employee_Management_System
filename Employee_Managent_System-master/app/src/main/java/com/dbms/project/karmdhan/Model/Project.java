package com.dbms.project.karmdhan.Model;

public class Project {
    int projectNumber;
    String projectName;
    int employeeNumber;

    public Project(int projectNumber, String projectName) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
    }

    public Project(int projectNumber, String projectName, int employeeNumber) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.employeeNumber = employeeNumber;
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

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
