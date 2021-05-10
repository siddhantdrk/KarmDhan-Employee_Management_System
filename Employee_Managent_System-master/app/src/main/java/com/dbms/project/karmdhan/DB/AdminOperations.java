package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbms.project.karmdhan.Model.Admin;
import com.dbms.project.karmdhan.Model.Employee;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_ADMIN_ID;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_ADMIN_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_CHARGE_PER_HOUR;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_HOURS_BILLED;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_ADMIN;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_EMPLOYEE;

public class AdminOperations {
    private final SQLiteOpenHelper adminDbHelper;

    public AdminOperations(Context context) {
        adminDbHelper = new KarmDhanDBHandler(context);
    }

    public boolean addAdmin(Admin admin) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ADMIN_ID, admin.getAdminId());
        contentValues.put(COLUMN_ADMIN_PASSWORD, admin.getAdminPassword());
        long result = database.insert(TABLE_ADMIN, null, contentValues);
        return result != -1;
    }

    public boolean checkAdminIDExist(int id) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_ADMIN + " where " + COLUMN_ADMIN_ID + " = " + id, null);
        return cursor.getCount() > 0;
    }

    public boolean checkAdminLoginCredentials(Admin admin) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_ADMIN + " where " + COLUMN_ADMIN_ID + " = ?" + " and " + COLUMN_ADMIN_PASSWORD + " = ?", new String[]{String.valueOf(admin.getAdminId()), admin.getAdminPassword()});
        return cursor.getCount() > 0;
    }

    public boolean updatePassword(int userId, String password) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ADMIN_PASSWORD, password);
        long result = database.update(TABLE_ADMIN, contentValues, COLUMN_ADMIN_ID + " = ?", new String[]{String.valueOf(userId)});
////      String strSQL = "UPDATE " + TABLE_ADMIN + " SET " + COLUMN_ADMIN_PASSWORD + " =" + "'" + password + "'" + " WHERE " + COLUMN_ADMIN_ID + " = " + "'" + userId + "'";
//        database.execSQL(strSQL);
        return result != -1;
    }

    public boolean addEmployee(Employee employee) throws SQLiteConstraintException {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        ContentValues employeeValues = new ContentValues();
        employeeValues.put(COLUMN_EMPLOYEE_NUMBER, employee.getEmployeeNumber());
        employeeValues.put(COLUMN_EMPLOYEE_NAME, employee.getEmployeeName());
        long result1 = database.insertOrThrow(TABLE_EMPLOYEE, null, employeeValues);
        ContentValues jobClassValues = new ContentValues();
        jobClassValues.put(COLUMN_EMPLOYEE_NUMBER, employee.getEmployeeNumber());
        jobClassValues.put(COLUMN_JOB_CLASS, employee.getEmployeeJobClass());
        long result2 = database.insert(TABLE_JOB_CLASS, null, jobClassValues);
        ContentValues passwordValues = new ContentValues();
        passwordValues.put(COLUMN_EMPLOYEE_NUMBER, employee.getEmployeeNumber());
        passwordValues.put(COLUMN_EMPLOYEE_PASSWORD, employee.getEmployeePassword());
        long result3 = database.insertOrThrow(TABLE_EMPLOYEE_PASSWORD, null, passwordValues);
        return result1 != -1 && result2 != -1 && result3 != -1;
    }

    public boolean checkIfEmployeeNumberExist(int empNum) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE + " where " + COLUMN_EMPLOYEE_NUMBER + " = " + empNum, null);
        return cursor.getCount() > 0;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE + " NATURAL JOIN " + TABLE_JOB_CLASS, null);
        Log.d("getAllEmployees", "" + cursor.getCount());
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    employeeList.add(new Employee(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)), cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_JOB_CLASS))));
                } while (cursor.moveToNext());
            }
        }
        Log.d("getAllEmployees", "" + employeeList.size());
        return employeeList;
    }

    public boolean removeEmployee(int employeeNum) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        int result1 = database.delete(TABLE_EMPLOYEE_PASSWORD, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employeeNum)});
        int result2 = database.delete(TABLE_EMPLOYEE, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employeeNum)});
        int result3 = database.delete(TABLE_PROJECT_EMPLOYEE, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employeeNum)});
        int result4 = database.delete(TABLE_JOB_CLASS, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employeeNum)});
        return result1 > 0 && result2 > 0 && result3 >= 0 && result4 > 0;
    }

    public boolean updateEmployeeDetails(Employee employee) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_EMPLOYEE_NAME, employee.getEmployeeName());
        int result1 = database.update(TABLE_EMPLOYEE, values1, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employee.getEmployeeNumber())});
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_JOB_CLASS, employee.getEmployeeJobClass());
        int result2 = database.update(TABLE_JOB_CLASS, values2, COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(employee.getEmployeeNumber())});
        return result1 >= 0 && result2 >= 0;
    }

    public boolean removeProject(int projectNum) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        int result1 = database.delete(TABLE_PROJECT, COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(projectNum)});
        int result2 = database.delete(TABLE_PROJECT_EMPLOYEE, COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(projectNum)});
        return result1 > 0 && result2 > 0;
    }

    public double getTotalCost() {
        Double totalCost = 0.0;
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT_EMPLOYEE, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    totalCost += cursor.getDouble(cursor.getColumnIndex(COLUMN_HOURS_BILLED)) * cursor.getDouble(cursor.getColumnIndex(COLUMN_CHARGE_PER_HOUR));
                } while (cursor.moveToNext());
            }
        }
        return totalCost;
    }
}