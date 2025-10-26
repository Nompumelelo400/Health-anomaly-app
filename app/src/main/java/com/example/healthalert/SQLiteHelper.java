package com.example.healthalert;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthApp.db";
    private static final int DATABASE_VERSION = 1;

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role"; // doctor or patient
    public static final String COL_AGE = "age"; // only for patients
    public static final String COL_MEDICAL_AID = "medicalAid"; // only for patients

    // Patient Results table
    public static final String TABLE_RESULTS = "results";
    public static final String COL_RESULT_ID = "id";
    public static final String COL_HEART_RATE = "heartRate";
    public static final String COL_STEPS = "steps";
    public static final String COL_CALORIES = "calories";
    public static final String COL_SLEEP = "sleep";
    public static final String COL_USER_EMAIL = "userEmail"; // link result to patient

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + " TEXT," +
                COL_EMAIL + " TEXT UNIQUE," +
                COL_PASSWORD + " TEXT," +
                COL_ROLE + " TEXT," +
                COL_AGE + " INTEGER," +
                COL_MEDICAL_AID + " TEXT" +
                ")";
        db.execSQL(createUsers);

        // Patient results table
        String createResults = "CREATE TABLE " + TABLE_RESULTS + " (" +
                COL_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_EMAIL + " TEXT," +
                COL_HEART_RATE + " INTEGER," +
                COL_STEPS + " INTEGER," +
                COL_CALORIES + " INTEGER," +
                COL_SLEEP + " REAL," +
                "FOREIGN KEY(" + COL_USER_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + COL_EMAIL + ")" +
                ")";
        db.execSQL(createResults);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Add a new user
    public boolean addUser(String username, String email, String password, String role, Integer age, String medicalAid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_EMAIL, email);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_ROLE, role);
        if ("patient".equals(role)) {
            cv.put(COL_AGE, age);
            cv.put(COL_MEDICAL_AID, medicalAid);
        }
        long result = db.insert(TABLE_USERS, null, cv);
        return result != -1;
    }

    // Check login credentials
    public boolean checkUser(String email, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=? AND " + COL_PASSWORD + "=? AND " + COL_ROLE + "=?";
        String[] selectionArgs = {email, password, role};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Get user info by email
    public HashMap<String, String> getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USERNAME, COL_EMAIL, COL_ROLE, COL_AGE, COL_MEDICAL_AID};
        String selection = COL_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        HashMap<String, String> user = new HashMap<>();
        if (cursor.moveToFirst()) {
            user.put("username", cursor.getString(0));
            user.put("email", cursor.getString(1));
            user.put("role", cursor.getString(2));
            user.put("age", cursor.getString(3) != null ? cursor.getString(3) : "");
            user.put("medicalAid", cursor.getString(4) != null ? cursor.getString(4) : "");
        }
        cursor.close();
        return user;
    }

    // Return all patients (for doctor dashboard)
    public ArrayList<HashMap<String, String>> getAllPatients() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_USERNAME, COL_EMAIL},
                COL_ROLE + "=?",
                new String[]{"patient"},
                null, null, COL_USERNAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", cursor.getString(0));
                map.put("email", cursor.getString(1));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Add a patient result
    public boolean addPatientResult(String userEmail, int heartRate, int steps, int calories, float sleep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_EMAIL, userEmail);
        cv.put(COL_HEART_RATE, heartRate);
        cv.put(COL_STEPS, steps);
        cv.put(COL_CALORIES, calories);
        cv.put(COL_SLEEP, sleep);
        long result = db.insert(TABLE_RESULTS, null, cv);
        return result != -1;
    }

    // Get all results for a patient
    public Cursor getPatientResults(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USER_EMAIL + "=?";
        String[] selectionArgs = {userEmail};
        return db.query(TABLE_RESULTS, null, selection, selectionArgs, null, null, COL_RESULT_ID + " DESC");
    }

    // Get latest result for a patient
    public Cursor getLatestResult(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USER_EMAIL + "=?";
        String[] selectionArgs = {userEmail};
        return db.query(TABLE_RESULTS, null, selection, selectionArgs, null, null, COL_RESULT_ID + " DESC", "1");
    }
}
