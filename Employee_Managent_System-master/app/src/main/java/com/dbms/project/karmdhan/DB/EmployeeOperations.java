package com.dbms.project.karmdhan.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_CHARGE_PER_HOUR;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_HOURS_BILLED;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_EMPLOYEE;

public class EmployeeOperations {

    private final SQLiteOpenHelper employeeDbHelper;


    public EmployeeOperations(Context context) {
        employeeDbHelper = new KarmDhanDBHandler(context);
    }


    public boolean checkIfEmployeeNumberExist(int empNum) {
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE_PASSWORD + " where " + COLUMN_EMPLOYEE_NUMBER + " = " + empNum, null);
        return cursor.getCount() > 0;
    }

    public boolean checkEmployeeLoginCredentials(Employee employee) {
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE_PASSWORD + " where " + COLUMN_EMPLOYEE_NUMBER + " = ?" + " and " + COLUMN_EMPLOYEE_PASSWORD + " = ?", new String[]{String.valueOf(employee.getEmployeeNumber()), employee.getEmployeePassword()});
        return cursor.getCount() > 0;
    }

    public Employee getEmployeeByNumber(int empNum) {
        Employee employee = null;
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE + " NATURAL JOIN " + TABLE_JOB_CLASS + " where " + COLUMN_EMPLOYEE_NUMBER + " = " + empNum, null);
        if (cursor != null && cursor.moveToFirst()) {
            employee = new Employee(empNum, cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_JOB_CLASS)));
        }
        return employee;
    }

    public boolean updatePassword(int userId, String password) {
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMPLOYEE_PASSWORD, password);
        long result = database.update(TABLE_EMPLOYEE_PASSWORD, contentValues, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(userId)});
        return result != -1;
    }

    public List<Project> getAllProjects(int employeeNum) {
        List<Project> projectList = new ArrayList<>();
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        Cursor cursor1 = database.rawQuery("select * from " + TABLE_PROJECT_EMPLOYEE + " NATURAL JOIN " + TABLE_PROJECT + " where " + COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employeeNum)});
        if (cursor1 != null) {
            if (cursor1.moveToFirst()) {
                do {
                    String projectLeaderName = getEmployeeByNumber(cursor1.getInt(cursor1.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER))).getEmployeeName();
                    projectList.add(new Project(cursor1.getInt(cursor1.getColumnIndex(COLUMN_PROJECT_NUMBER)), cursor1.getString(cursor1.getColumnIndex(COLUMN_PROJECT_NAME)), cursor1.getInt(cursor1.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER)), cursor1.getDouble(cursor1.getColumnIndex(COLUMN_CHARGE_PER_HOUR)), cursor1.getDouble(cursor1.getColumnIndex(COLUMN_HOURS_BILLED)), projectLeaderName));

                } while (cursor1.moveToNext());
            }
        }
        return projectList;
    }
}