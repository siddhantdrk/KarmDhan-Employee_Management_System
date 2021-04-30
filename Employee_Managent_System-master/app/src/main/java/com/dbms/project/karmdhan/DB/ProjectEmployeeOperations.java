package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.ProjectEmployee;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_CHARGE_PER_HOUR;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_HOURS_BILLED;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_EMPLOYEE;

public class ProjectEmployeeOperations {
    private final SQLiteOpenHelper projectEmployeeDbHelper;

    public ProjectEmployeeOperations(Context context) {
        projectEmployeeDbHelper = new KarmDhanDBHandler(context);
    }

    public boolean addProjectEmployee(ProjectEmployee projectEmployee) {
        SQLiteDatabase database = projectEmployeeDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PROJECT_NUMBER, projectEmployee.getProjectNumber());
        contentValues.put(COLUMN_EMPLOYEE_NUMBER, projectEmployee.getEmployeeNumber());
        contentValues.put(COLUMN_CHARGE_PER_HOUR, projectEmployee.getChargePerHour());
        contentValues.put(COLUMN_HOURS_BILLED, projectEmployee.getHoursBilled());
        long result = database.insert(TABLE_PROJECT_EMPLOYEE, null, contentValues);
        return result != -1;
    }

    public List<Employee> getAllRemainingEmployees(int projectNum) {
        List<Integer> projectEmployeeNumberList = new ArrayList<>();
        List<Employee> remainingEmployeeList = new ArrayList<>();
        SQLiteDatabase database = projectEmployeeDbHelper.getWritableDatabase();
        Cursor cursorAllEmployee = database.rawQuery("select * from " + TABLE_EMPLOYEE + " NATURAL JOIN " + TABLE_JOB_CLASS, null);
        Cursor cursorProjectEmployee = database.rawQuery("select * from " + TABLE_PROJECT_EMPLOYEE + " where " + COLUMN_PROJECT_NUMBER + " = ?", new String[]{String.valueOf(projectNum)});
        if (cursorAllEmployee != null && cursorProjectEmployee != null) {
            if (cursorProjectEmployee.moveToFirst()) {
                do {
                    projectEmployeeNumberList.add(cursorProjectEmployee.getInt(cursorProjectEmployee.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)));
                } while (cursorProjectEmployee.moveToNext());
            }
            if (cursorAllEmployee.moveToFirst()) {
                do {
                    if (!projectEmployeeNumberList.contains(cursorAllEmployee.getInt(cursorAllEmployee.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)))) {
                        remainingEmployeeList.add(new Employee(cursorAllEmployee.getInt(cursorAllEmployee.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)), cursorAllEmployee.getString(cursorAllEmployee.getColumnIndex(COLUMN_EMPLOYEE_NAME)), cursorAllEmployee.getString(cursorAllEmployee.getColumnIndex(COLUMN_JOB_CLASS))));
                    }
                } while (cursorAllEmployee.moveToNext());
            }
        }
        return remainingEmployeeList;
    }

    public ProjectEmployee getProjectEmployeeByProjectAndEmployeeNum(int projectNum, int employeeNum) {
        SQLiteDatabase database = projectEmployeeDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT_EMPLOYEE + " where " + COLUMN_PROJECT_NUMBER + " = ? and " + COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(projectNum), String.valueOf(employeeNum)});
        Log.d("projectNum", "" + cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_NUMBER)));
        Log.d("employeeNum", "" + cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)));
        Log.d("chargePerHour", "" + cursor.getColumnIndex(COLUMN_CHARGE_PER_HOUR));
        Log.d("chargePerHour", "" + cursor.getDouble(cursor.getColumnIndex(COLUMN_CHARGE_PER_HOUR)));
        Log.d("hoursBilled", "" + cursor.getColumnIndex(COLUMN_HOURS_BILLED));
        Log.d("hoursBilled", "" + cursor.getDouble(cursor.getColumnIndex(COLUMN_HOURS_BILLED)));
        try {
            return new ProjectEmployee(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_NUMBER)), cursor.getInt(cursor.getColumnIndex(COLUMN_EMPLOYEE_NUMBER)), cursor.getDouble(cursor.getColumnIndex(COLUMN_CHARGE_PER_HOUR)), cursor.getDouble(cursor.getColumnIndex(COLUMN_HOURS_BILLED)));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProjectEmployee(ProjectEmployee projectEmployee) {
        SQLiteDatabase database = projectEmployeeDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHARGE_PER_HOUR, projectEmployee.getChargePerHour());
        values.put(COLUMN_HOURS_BILLED, projectEmployee.getHoursBilled());
        long result = database.update(TABLE_PROJECT_EMPLOYEE, values, COLUMN_PROJECT_NUMBER + " = ? and " + COLUMN_EMPLOYEE_NUMBER + " = ?", new String[]{String.valueOf(projectEmployee.getProjectNumber()), String.valueOf(projectEmployee.getEmployeeNumber())});
        return result >= 0;
    }
}
