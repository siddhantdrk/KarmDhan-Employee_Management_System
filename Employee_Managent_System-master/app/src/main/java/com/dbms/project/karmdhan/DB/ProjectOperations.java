package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public ProjectOperations(Context context) {
        projectDbHelper = new KarmDhanDBHandler(context);
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
                project = new Project(projectNum, cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)), cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER)));
            }
        }
        return project;
    }
}
