package com.dbms.project.karmdhan.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbms.project.karmdhan.Authentication.PasswordAuthentication;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.NewEmployee;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_PASSWORD;

public class EmployeeOperations {

    public static final String TAG = "EMP_MNGMNT_SYS";

    private final SQLiteOpenHelper employeeDbHelper;
    private PasswordAuthentication passwordAuthentication;
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
        employeeDbHelper = new KarmDhanDBHandler(context);
    }

    public void open() {
        Log.i(TAG, "Database Opened ");
        database = employeeDbHelper.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database Closed ");
        employeeDbHelper.close();
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

    public boolean checkIfEmployeeNumberExist(int empNum) {
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE_PASSWORD + " where " + COLUMN_EMPLOYEE_NUMBER + " = " + empNum, null);
        return cursor.getCount() > 0;
    }

    public boolean checkEmployeeLoginCredentials(NewEmployee employee) {
        passwordAuthentication = new PasswordAuthentication();
        SQLiteDatabase database = employeeDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE_PASSWORD + " where " + COLUMN_EMPLOYEE_NUMBER + " = ?" + " and " + COLUMN_EMPLOYEE_PASSWORD + " = ?", new String[]{String.valueOf(employee.getEmployeeNumber()), employee.getEmployeePassword()});
        return cursor.getCount() > 0;
    }

}