package com.dbms.project.karmdhan.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KarmDhanDBHandler extends SQLiteOpenHelper {

    public static final String TABLE_ADMIN = "admins";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ADMIN_ID = "adminID";
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_ID = "empID";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_HIRE_DATE = "hiredata";
    public static final String COLUMN_DEPT = "dept";
    public static final String COLUMN_ADMIN_PASSWORD = "adminPassword";
    private static final String DATABASE_NAME = "karmdhan.db";
    private static final String TABLE_EMPLOYEE_CREATE = "CREATE TABLE " + TABLE_EMPLOYEES + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME
            + " TEXT, " + COLUMN_GENDER + " TEXT, " + COLUMN_HIRE_DATE + " NUMERIC, " + COLUMN_DEPT + " TEXT " + ")";

    private static final String TABLE_ADMIN_CREATE = "CREATE TABLE " + TABLE_ADMIN + " (" + COLUMN_ADMIN_ID + " TEXT PRIMARY KEY, " +
            COLUMN_ADMIN_PASSWORD + " TEXT " + ")";

    public KarmDhanDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_EMPLOYEE_CREATE);
        sqLiteDatabase.execSQL(TABLE_ADMIN_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        sqLiteDatabase.execSQL(TABLE_EMPLOYEE_CREATE);
        sqLiteDatabase.execSQL(TABLE_ADMIN_CREATE);
    }
}
