package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Model.Admin;

import static com.dbms.project.karmdhan.DB.KarmDhanDBHandler.COLUMN_ADMIN_ID;
import static com.dbms.project.karmdhan.DB.KarmDhanDBHandler.COLUMN_ADMIN_PASSWORD;
import static com.dbms.project.karmdhan.DB.KarmDhanDBHandler.TABLE_ADMIN;

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

    public boolean checkAdminIDExist(String id) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_ADMIN + " where " + COLUMN_ADMIN_ID + " = ?", new String[]{id});
        return cursor.getCount() > 0;
    }

    public boolean checkLoginCredentials(Admin admin) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_ADMIN + " where " + COLUMN_ADMIN_ID + " = ? and " + COLUMN_ADMIN_PASSWORD + " = ?", new String[]{admin.getAdminId(), admin.getAdminPassword()});
        return cursor.getCount() > 0;
    }

    public void update(String userId,String password){
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        String strSQL = "UPDATE "+TABLE_ADMIN+" SET "+ COLUMN_ADMIN_PASSWORD +" ="+"'"+password+"'"+ " WHERE " + COLUMN_ADMIN_ID + " =" +"'"+ userId+"'";
        database.execSQL(strSQL);
    }

}