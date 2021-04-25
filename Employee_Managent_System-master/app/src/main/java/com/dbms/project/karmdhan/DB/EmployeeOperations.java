package com.dbms.project.karmdhan.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbms.project.karmdhan.Model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeOperations {

    public static final String TAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            KarmDhanDBHandler.COLUMN_ID,
            KarmDhanDBHandler.COLUMN_FIRST_NAME,
            KarmDhanDBHandler.COLUMN_LAST_NAME,
            KarmDhanDBHandler.COLUMN_GENDER,
            KarmDhanDBHandler.COLUMN_HIRE_DATE,
            KarmDhanDBHandler.COLUMN_DEPT
    };

    public EmployeeOperations(Context context) {
        dbHandler = new KarmDhanDBHandler(context);
    }

    public void open() {
        Log.i(TAG, "Database Opened ");
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database Closed ");
        dbHandler.close();
    }

    //inserting the employee
    public Employee addEmployee(Employee employee) {
        ContentValues values = new ContentValues();
        values.put(KarmDhanDBHandler.COLUMN_FIRST_NAME, employee.getFirstName());
        values.put(KarmDhanDBHandler.COLUMN_LAST_NAME, employee.getLastName());
        values.put(KarmDhanDBHandler.COLUMN_GENDER, employee.getGender());
        values.put(KarmDhanDBHandler.COLUMN_HIRE_DATE, employee.getHireDate());
        values.put(KarmDhanDBHandler.COLUMN_DEPT, employee.getDept());

        long insertId = database.insert(KarmDhanDBHandler.TABLE_EMPLOYEES, null, values);
        employee.setEmpId(insertId);
        return employee;
    }

    //getting the employee
    public Employee getEmployee(long id) {
        Employee e;
        open();
        Log.d(TAG, "getEmployee: " + id);
        Cursor cursor = database.query(KarmDhanDBHandler.TABLE_EMPLOYEES, allColumns,
                KarmDhanDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            Log.d(TAG, "getEmployee: " + cursor);
            e = new Employee(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
        } catch (CursorIndexOutOfBoundsException a) {
            e = null;
        }
        close();
        return e;
    }

    //fetching all the employees
    public List<Employee> getAllEmployees() {
        open();
        Cursor cursor = database.query(KarmDhanDBHandler.TABLE_EMPLOYEES, allColumns,
                null, null, null, null, null);
        List<Employee> employees = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Employee employee = new Employee();
                employee.setEmpId(cursor.getLong(cursor.getColumnIndex(KarmDhanDBHandler.COLUMN_ID)));
                employee.setFirstName(cursor.getString(cursor.getColumnIndex(KarmDhanDBHandler.COLUMN_FIRST_NAME)));
                employee.setLastName(cursor.getString(cursor.getColumnIndex(KarmDhanDBHandler.COLUMN_LAST_NAME)));
                employee.setGender(cursor.getString(cursor.getColumnIndex(KarmDhanDBHandler.COLUMN_GENDER)));
                employee.setHireDate(cursor.getString(cursor.getColumnIndex(KarmDhanDBHandler.COLUMN_HIRE_DATE)));
                employee.setDept(cursor.getString(cursor.getColumnIndex(KarmDhanDBHandler.COLUMN_DEPT)));
                employees.add(employee);
            }
        }
        close();
        return employees;
    }

    //Updating the Employee
    public int updateEmployee(Employee employee) {
        open();
        ContentValues values = new ContentValues();
        values.put(KarmDhanDBHandler.COLUMN_FIRST_NAME, employee.getFirstName());
        values.put(KarmDhanDBHandler.COLUMN_LAST_NAME, employee.getLastName());
        values.put(KarmDhanDBHandler.COLUMN_GENDER, employee.getGender());
        values.put(KarmDhanDBHandler.COLUMN_HIRE_DATE, employee.getHireDate());
        values.put(KarmDhanDBHandler.COLUMN_DEPT, employee.getDept());

        //Updating Row
        return database.update(KarmDhanDBHandler.TABLE_EMPLOYEES, values,
                KarmDhanDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(employee.getEmpId())});
    }

    //Deleting Employee
    public void removeEmployee(Employee employee) {
        open();
        database.delete(KarmDhanDBHandler.TABLE_EMPLOYEES,
                KarmDhanDBHandler.COLUMN_ID + "=" + employee.getEmpId(), null);
        close();
    }

}