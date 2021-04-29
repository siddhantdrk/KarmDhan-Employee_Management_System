package com.dbms.project.karmdhan.Model;

public class Project {
    int projectNumber;
    String projectName;
    String projectLeader;

    public Project(int projectNumber, String projectName, String projectLeader) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.projectLeader = projectLeader;
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
