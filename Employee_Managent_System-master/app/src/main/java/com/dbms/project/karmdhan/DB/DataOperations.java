package com.dbms.project.karmdhan.DB;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Authentication.PasswordAuthentication;
import com.dbms.project.karmdhan.Model.Employee;

import java.util.ArrayList;
import java.util.List;


public class DataOperations {
    private final SQLiteOpenHelper myDbHelper;
    private PasswordAuthentication passwordAuthentication;
    private final Context context;
    private AdminOperations adminOperations;
    private List<Employee> employeeList;

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
            adminOperations.addEmployee(employeeList.get(i));
        }
    }


}
