package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbms.project.karmdhan.Model.Admin;
import com.dbms.project.karmdhan.Model.NewEmployee;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_ADMIN_ID;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_ADMIN_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_ADMIN;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_JOB_CLASS;

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

    public boolean addEmployee(NewEmployee newEmployee) throws SQLiteConstraintException {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        ContentValues employeeValues = new ContentValues();
        employeeValues.put(COLUMN_EMPLOYEE_NUMBER, newEmployee.getEmployeeNumber());
        employeeValues.put(COLUMN_EMPLOYEE_NAME, newEmployee.getEmployeeName());
        long result1 = database.insert(TABLE_EMPLOYEE, null, employeeValues);
        ContentValues jobClassValues = new ContentValues();
        jobClassValues.put(COLUMN_EMPLOYEE_NUMBER, newEmployee.getEmployeeNumber());
        jobClassValues.put(COLUMN_JOB_CLASS, newEmployee.getEmployeeJobClass());
        long result2 = database.insert(TABLE_JOB_CLASS, null, jobClassValues);
        ContentValues passwordValues = new ContentValues();
        passwordValues.put(COLUMN_EMPLOYEE_NUMBER, newEmployee.getEmployeeNumber());
        passwordValues.put(COLUMN_EMPLOYEE_PASSWORD, newEmployee.getEmployeePassword());
        long result3 = database.insert(TABLE_EMPLOYEE_PASSWORD, null, passwordValues);
        return result1 != -1 && result2 != -1 && result3 != -1;
    }

    public boolean checkIfEmployeeNumberExist(int empNum) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE + " where " + COLUMN_EMPLOYEE_NUMBER + " = " + empNum, null);
        return cursor.getCount() > 0;
    }

    public List<NewEmployee> getAllEmployees() {
        List<NewEmployee> employeeList = new ArrayList<>();
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_EMPLOYEE + " NATURAL JOIN " + TABLE_JOB_CLASS, null);
        Log.d("getAllEmployees", "" + cursor.getCount());
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    employeeList.add(new NewEmployee(cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)), cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_JOB_CLASS))));
                } while (cursor.moveToNext());
            }
        }
        Log.d("getAllEmployees", "" + employeeList.size());
        return employeeList;
    }
}