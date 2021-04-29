package com.dbms.project.karmdhan.DB;

public class KarmDhanDBSchema {

    //Tables
    public static final String TABLE_ADMIN = "Admin";
    public static final String TABLE_EMPLOYEE_PASSWORD = "EmployeePassword";
    public static final String TABLE_PROJECT = "Project";
    public static final String TABLE_PROJECT_EMPLOYEE = "ProjectEmployee";
    public static final String TABLE_EMPLOYEE = "Employee";
    public static final String TABLE_JOB_CLASS = "JobClass";


    //Attributes
    public static final String COLUMN_ADMIN_ID = "adminID";
    public static final String COLUMN_ADMIN_PASSWORD = "adminPassword";
    public static final String COLUMN_PROJECT_NUMBER = "projectNumber";
    public static final String COLUMN_PROJECT_NAME = "projectName";
    public static final String COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER = "projectLeaderEmployeeNumber";
    public static final String COLUMN_EMPLOYEE_NUMBER = "employeeNumber";
    public static final String COLUMN_EMPLOYEE_PASSWORD = "employeePassword";
    public static final String COLUMN_EMPLOYEE_NAME = "employeeName";
    public static final String COLUMN_CHARGE_PER_HOUR = "chargePerHour";
    public static final String COLUMN_HOURS_BILLED = "hoursBilled";
    public static final String COLUMN_JOB_CLASS = "jobClass";


    //Admin Table
    public static final String TABLE_ADMIN_CREATE = "CREATE TABLE " + TABLE_ADMIN + " (" + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_ADMIN_PASSWORD + " TEXT " + ")";

    //Project Table
    public static final String TABLE_PROJECT_CREATE = "CREATE TABLE " + TABLE_PROJECT + " (" + COLUMN_PROJECT_NUMBER + " INTEGER PRIMARY KEY, " +
            COLUMN_PROJECT_NAME + " TEXT, " + COLUMN_PROJECT_LEADER_EMPLOYEE_NUMBER + " INTEGER " + ")";

    //ProjectEmployee Table
    public static final String TABLE_PROJECT_EMPLOYEE_CREATE = "CREATE TABLE " + TABLE_PROJECT_EMPLOYEE + " (" + COLUMN_PROJECT_NUMBER +
            " INTEGER, " + COLUMN_EMPLOYEE_NUMBER + " INTEGER, " + COLUMN_CHARGE_PER_HOUR + " DECIMAL(16,8), " + COLUMN_HOURS_BILLED +
            " DECIMAL(16,8), " + "CONSTRAINT CompKey_PRJ_EMP_NUM " + "PRIMARY KEY (" + COLUMN_PROJECT_NUMBER + ", " + COLUMN_EMPLOYEE_NUMBER + ")" + ")";

    //Employee Table
    public static final String TABLE_EMPLOYEE_CREATE = "CREATE TABLE " + TABLE_EMPLOYEE + " (" + COLUMN_EMPLOYEE_NUMBER + " INTEGER PRIMARY KEY, "
            + COLUMN_EMPLOYEE_NAME + " TEXT " + ")";

    //JobClass Table
    public static final String TABLE_JOB_CLASS_CREATE = "CREATE TABLE " + TABLE_JOB_CLASS + " (" + COLUMN_EMPLOYEE_NUMBER + " INTEGER PRIMARY KEY, "
            + COLUMN_JOB_CLASS + " TEXT " + ")";

    //EmployeePassword Table
    public static final String TABLE_EMPLOYEE_PASSWORD_CREATE = "CREATE TABLE " + TABLE_EMPLOYEE_PASSWORD + " (" + COLUMN_EMPLOYEE_NUMBER + " INTEGER PRIMARY KEY, "
            + COLUMN_EMPLOYEE_PASSWORD + " TEXT " + ")";
}
