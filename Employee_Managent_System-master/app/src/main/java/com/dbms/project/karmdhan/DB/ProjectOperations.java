package com.dbms.project.karmdhan.DB;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class ProjectOperations {
    private final SQLiteOpenHelper projectDbHelper;

    public ProjectOperations(Context context) {
        projectDbHelper = new KarmDhanDBHandler(context);
    }


}
