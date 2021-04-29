package com.dbms.project.karmdhan.Model;

import java.util.List;

public class Project {
    private int projectNumber;
    private String projectName;
    private int projectLeaderEmployeeNumber;
    private List<Employee> projectEmployeeList;

    public Project(int projectNumber, String projectName, int projectLeaderEmployeeNumber) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeaderEmployeeNumber = projectLeaderEmployeeNumber;
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
}
