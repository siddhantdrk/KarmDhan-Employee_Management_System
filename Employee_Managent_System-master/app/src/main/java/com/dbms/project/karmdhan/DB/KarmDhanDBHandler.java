package com.dbms.project.karmdhan.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_ADMIN;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_ADMIN_CREATE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_CREATE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_EMPLOYEE_PASSWORD_CREATE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_JOB_CLASS;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_JOB_CLASS_CREATE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_CREATE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_EMPLOYEE;
import static com.dbms.project.karmdhan.DB.KarmDhanDBSchema.TABLE_PROJECT_EMPLOYEE_CREATE;

public class KarmDhanDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "karmdhan.db";

    public KarmDhanDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_ADMIN_CREATE);
        sqLiteDatabase.execSQL(TABLE_PROJECT_CREATE);
        sqLiteDatabase.execSQL(TABLE_PROJECT_EMPLOYEE_CREATE);
        sqLiteDatabase.execSQL(TABLE_EMPLOYEE_CREATE);
        sqLiteDatabase.execSQL(TABLE_EMPLOYEE_PASSWORD_CREATE);
        sqLiteDatabase.execSQL(TABLE_JOB_CLASS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_EMPLOYEE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_PASSWORD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_CLASS);
        sqLiteDatabase.execSQL(TABLE_ADMIN_CREATE);
        sqLiteDatabase.execSQL(TABLE_PROJECT_CREATE);
        sqLiteDatabase.execSQL(TABLE_PROJECT_EMPLOYEE_CREATE);
        sqLiteDatabase.execSQL(TABLE_EMPLOYEE_CREATE);
        sqLiteDatabase.execSQL(TABLE_EMPLOYEE_PASSWORD_CREATE);
        sqLiteDatabase.execSQL(TABLE_JOB_CLASS_CREATE);
    }
}
