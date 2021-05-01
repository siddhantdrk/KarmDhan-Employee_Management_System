package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.Model.ProjectEmployee;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_CHARGE_PER_HOUR;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_HOURS_BILLED;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_EMPLOYEE;

public class ProjectOperations {
    private final SQLiteOpenHelper projectDbHelper;
    private EmployeeOperations employeeOperations;
    private Context context;

    public ProjectOperations(Context context) {
        projectDbHelper = new KarmDhanDBHandler(context);
        this.context = context;
    }

    public boolean addProject(Project project, ProjectEmployee projectEmployee) {
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PROJECT_NUMBER, project.getProjectNumber());
        contentValues.put(COLUMN_PROJECT_NAME, project.getProjectName());
        contentValues.put(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER, project.getProjectLeaderEmployeeNumber());
        long result = database.insert(TABLE_PROJECT, null, contentValues);
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COLUMN_PROJECT_NUMBER, projectEmployee.getProjectNumber());
        contentValues1.put(COLUMN_EMPLOYEE_NUMBER, projectEmployee.getEmployeeNumber());
        contentValues1.put(COLUMN_CHARGE_PER_HOUR, projectEmployee.getChargePerHour());
        contentValues1.put(COLUMN_HOURS_BILLED, projectEmployee.getHoursBilled());
        long result1 = database.insert(TABLE_PROJECT_EMPLOYEE, null, contentValues1);
        return result != -1 && result1 != -1;
    }

    public boolean checkIfProjectNumberExists(int projectNumber) {
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT + " where " + COLUMN_PROJECT_NUMBER + " = " + projectNumber, null);
        return cursor.getCount() > 0;
    }

    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    projectList.add(new Project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_NUMBER)), cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)), cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER))));
                } while (cursor.moveToNext());
            }
        }
        return projectList;
    }

    public Project getProjectByNumber(int projectNum) {
        Project project = null;
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT + " where " + COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(projectNum)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                employeeOperations = new EmployeeOperations(context);
                String projectLeaderName = employeeOperations.getEmployeeByNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER))).getEmployeeName();
                project = new Project(projectNum, cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)), cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER)), projectLeaderName);
            }
        }
        return project;
    }

    public boolean updateProjectDetails(Project project) {
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PROJECT_NAME, project.getProjectName());
        contentValues.put(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER, project.getProjectLeaderEmployeeNumber());
        long result = database.update(TABLE_PROJECT, contentValues, COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(project.getProjectNumber())});
        return result >= 0;
    }

    public List<Employee> getAllEmployeeForAProject(int projectNum) {
        List<Employee> employeeList = new ArrayList<>();
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT_EMPLOYEE + " where " + COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(projectNum)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    EmployeeOperations employeeOperations = new EmployeeOperations(context);
                    Employee employee = employeeOperations.getEmployeeByNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)));
                    employeeList.add(new Employee(employee.getEmployeeNumber(), employee.getEmployeeName(), employee.getEmployeeJobClass(), cursor.getDouble(cursor.getColumnIndex(COLUMN_CHARGE_PER_HOUR)), cursor.getDouble(cursor.getColumnIndex(COLUMN_HOURS_BILLED))));
                } while (cursor.moveToNext());
            }
        }
        return employeeList;
    }

    public boolean removeEmployeeFromProject(int projectNum, int employeeNum) {
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        long result = database.delete(TABLE_PROJECT_EMPLOYEE, COLUMN_PROJECT_NUMBER + " = ? and " + COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(projectNum), String.valueOf(employeeNum)});
        return result > 0;
    }

    public double getProjectCost(int projectNum) {
        Double projectCost = 0.0;
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT_EMPLOYEE + " where " + COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(projectNum)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    projectCost += cursor.getDouble(cursor.getColumnIndex(COLUMN_HOURS_BILLED)) * cursor.getDouble(cursor.getColumnIndex(COLUMN_CHARGE_PER_HOUR));
                } while (cursor.moveToNext());
            }
        }
        return projectCost;
    }

    public List<Project> EmployeeIsALeaderForProjects(int employeeNum) {
        List<Project> projectList = new ArrayList<>();
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT + " where " + COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employeeNum)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    projectList.add(new Project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_NUMBER)), cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)), cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER))));
                } while (cursor.moveToNext());
            }
        }
        return projectList;
    }
}
