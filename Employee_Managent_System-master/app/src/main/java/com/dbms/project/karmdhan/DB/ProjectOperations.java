package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Model.Project;

import java.util.ArrayList;
import java.util.List;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_LEADER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NAME;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.COLUMN_PROJECT_NUMBER;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT;

public class ProjectOperations {
    private final SQLiteOpenHelper projectDbHelper;

    public ProjectOperations(Context context) {
        projectDbHelper = new KarmDhanDBHandler(context);
    }

    public boolean addProject(Project project) {
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PROJECT_NUMBER, project.getProjectNumber());
        contentValues.put(COLUMN_PROJECT_NAME, project.getProjectName());
        contentValues.put(COLUMN_PROJECT_LEADER, project.getProjectLeader());
        long result = database.insert(TABLE_PROJECT, null, contentValues);
        return result != -1;
    }

    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        SQLiteDatabase database = projectDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_PROJECT, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    projectList.add(new Project(cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_NUMBER)), cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME)), cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_LEADER))));
                } while (cursor.moveToNext());
            }
        }
        return projectList;
    }
}
