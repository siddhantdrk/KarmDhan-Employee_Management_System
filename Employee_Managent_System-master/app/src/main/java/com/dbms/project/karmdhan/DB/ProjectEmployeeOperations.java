package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Model.ProjectEmployee;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_CHARGE_PER_HOUR;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_EMPLOYEE_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_HOURS_BILLED;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NUMBER;
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
}
