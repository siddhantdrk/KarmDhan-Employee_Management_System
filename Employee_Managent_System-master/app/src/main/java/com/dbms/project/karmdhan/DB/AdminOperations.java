package com.dbms.project.karmdhan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbms.project.karmdhan.Model.Admin;

public class AdminOperations {
    private final SQLiteOpenHelper adminDbHelper;

    public AdminOperations(Context context) {
        adminDbHelper = new KarmDhanDBHandler(context);
    }

    public boolean addAdmin(Admin admin) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KarmDhanDBHandler.COLUMN_ADMIN_ID, admin.getAdminId());
        contentValues.put(KarmDhanDBHandler.COLUMN_ADMIN_PASSWORD, admin.getAdminPassword());
        long result = database.insert(KarmDhanDBHandler.TABLE_ADMIN, null, contentValues);
        return result != -1;
    }

    public boolean checkAdminIDExist(String id) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + KarmDhanDBHandler.TABLE_ADMIN + " where " + KarmDhanDBHandler.COLUMN_ADMIN_ID + " = ?", new String[]{id});
        return cursor.getCount() > 0;
    }

    public boolean checkLoginCredentials(Admin admin) {
        SQLiteDatabase database = adminDbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + KarmDhanDBHandler.TABLE_ADMIN + " where " + KarmDhanDBHandler.COLUMN_ADMIN_ID + " = ? and " + KarmDhanDBHandler.COLUMN_ADMIN_PASSWORD + " = ?", new String[]{admin.getAdminId(), admin.getAdminPassword()});
        return cursor.getCount() > 0;
    }

}