package com.dbms.project.karmdhan.Model;

import java.util.List;

public class Project {
    private int projectNumber;
    private String projectName;
    private String projectLeader;
    private List<Employee> projectEmployeeList;

    public Project(int projectNumber, String projectName, String projectLeader) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeader = projectLeader;
    }

    public Project(int projectNumber, String projectName, String projectLeader, List<Employee> projectEmployeeList) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeader = projectLeader;
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

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }
}
