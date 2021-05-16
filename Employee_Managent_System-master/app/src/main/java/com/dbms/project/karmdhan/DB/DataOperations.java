package com.dbms.project.karmdhan.DB;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbms.project.karmdhan.Authentication.PasswordAuthentication;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.Model.ProjectEmployee;

import java.util.ArrayList;
import java.util.List;


public class DataOperations {
    private final SQLiteOpenHelper myDbHelper;
    private PasswordAuthentication passwordAuthentication;
    private final Context context;
    private AdminOperations adminOperations;
    private ProjectOperations projectOperations;
    private ProjectEmployeeOperations projectEmployeeOperations;

    private List<Employee> employeeList;
    private List<Project> projectList;
    private List<ProjectEmployee> projectEmployeeList;
    public DataOperations(Context context) {
        this.context=context;
        myDbHelper = new KarmDhanDBHandler(context);
    }

    public void addEmployeeTableData() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(103, "John E.Arbough", "Electrical Engineer", "11111111"));
        employeeList.add(new Employee(101, "John G.News", "Database Designer", "22222222"));
        employeeList.add(new Employee(105, "Alice K.Johnson", "Database Designer", "33333333"));
        employeeList.add(new Employee(106, "William Smithfield", "Programmer", "44444444"));
        employeeList.add(new Employee(102, "David H.Senior", "System Analyst", "55555555"));
        employeeList.add(new Employee(114, "Annelise Jones", "Application Designer", "66666666"));
        employeeList.add(new Employee(118, "James J.Frommer", "General Support", "99999999"));
        employeeList.add(new Employee(104, "Anne K.Ramoras", "Systems Analyst", "77777777"));
        employeeList.add(new Employee(112, "Darlene M.Smithon", "DSS Analysts", "88888888"));
        employeeList.add(new Employee(113, "Delbert K.Joenbrood", "Application Designer", "12121212"));
        employeeList.add(new Employee(111, "Geoff B.Wabash", "Clerical Support", "13131313"));
        employeeList.add(new Employee(107, "Maria D.Alonzo", "Programmer", "14141414"));
        employeeList.add(new Employee(115, "Travis B.Bawangi", "Systems Analyst", "15151515"));
        employeeList.add(new Employee(108, "Ralph B.Washington", "Systems Analyst", "16161616"));


        addEmployeeData(employeeList);
    }

    public void addEmployeeData(List<Employee> employeeList) {
        adminOperations = new AdminOperations(context);
        for (int i = 0; i < employeeList.size(); i++) {
            try {
                adminOperations.addEmployee(employeeList.get(i));
            } catch (SQLiteConstraintException sqLiteConstraintException) {
                Log.e("addEmployeeData", sqLiteConstraintException.getMessage());
            }
        }
    }

    public void addProjectTableData(){
        projectList = new ArrayList<Project>();
        projectList.add(new Project(15, "Evergreen", 105));
        projectList.add(new Project(18, "Amber Wave", 104));
        projectList.add(new Project(22, "KarmDhan", 113));
        projectList.add(new Project(25, "CVMaker", 101));

        projectEmployeeList = new ArrayList<ProjectEmployee>();
        projectEmployeeList.add(new ProjectEmployee(15, 103, 84.50, 23.8));
        projectEmployeeList.add(new ProjectEmployee(15, 101, 105.00, 19.4));
        projectEmployeeList.add(new ProjectEmployee(15, 105, 105.50, 35.7));
        projectEmployeeList.add(new ProjectEmployee(15, 106, 35.75, 12.6));
        projectEmployeeList.add(new ProjectEmployee(15, 102, 96.75, 23.8));
        projectEmployeeList.add(new ProjectEmployee(18, 114, 48.50, 24.6));
        projectEmployeeList.add(new ProjectEmployee(18, 118, 18.36, 45.3));
        projectEmployeeList.add(new ProjectEmployee(18, 104, 96.75, 32.4));
        projectEmployeeList.add(new ProjectEmployee(18,112,45.95,44.0));
        projectEmployeeList.add(new ProjectEmployee(22,105,105.00,64.7));
        projectEmployeeList.add(new ProjectEmployee(22,104,96.75,48.4));
        projectEmployeeList.add(new ProjectEmployee(22,113,48.10,23.6));
        projectEmployeeList.add(new ProjectEmployee(22,111,26.87,22.0));
        projectEmployeeList.add(new ProjectEmployee(22,106,35.75,12.8));
        projectEmployeeList.add(new ProjectEmployee(25,107,35.75,24.6));
        projectEmployeeList.add(new ProjectEmployee(25,115,96.75,45.8));
        projectEmployeeList.add(new ProjectEmployee(25,101,105.00,56.3));
        projectEmployeeList.add(new ProjectEmployee(25,114,48.10,33.1));
        projectEmployeeList.add(new ProjectEmployee(25,108,96.75,23.6));
        projectEmployeeList.add(new ProjectEmployee(25,118,18.36,30.5));
        projectEmployeeList.add(new ProjectEmployee(25,112,45.95,41.4));

        addProjectData(projectList,projectEmployeeList);
    }


    public void addProjectData(List<Project> projectList,List<ProjectEmployee> projectEmployeeList){
        projectOperations = new ProjectOperations(context);
        for (int i = 0, j = 0; i < projectEmployeeList.size(); i++, j++) {
            if (j >= projectList.size()) {
                j = 0;
            }
            projectOperations.addProject(projectList.get(j), projectEmployeeList.get(i));
        }
    }

}
