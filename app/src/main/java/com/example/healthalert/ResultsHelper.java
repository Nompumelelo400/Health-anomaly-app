package com.example.healthalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ResultsHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthApp.db"; // same DB
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_RESULTS = "results";
    public static final String COL_ID = "id";
    public static final String COL_USER_EMAIL = "userEmail";
    public static final String COL_HEART_RATE = "heartRate";
    public static final String COL_BLOOD_PRESSURE = "steps";
    public static final String COL_OXYGEN_LEVELS = "calories";
    public static final String COL_SLEEP = "sleep";

    public ResultsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createResults = "CREATE TABLE IF NOT EXISTS " + TABLE_RESULTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_EMAIL + " TEXT," +
                COL_HEART_RATE + " INTEGER," +
                COL_BLOOD_PRESSURE + " INTEGER," +
                COL_OXYGEN_LEVELS + " INTEGER," +
                COL_SLEEP + " REAL" +
                ")";
        db.execSQL(createResults);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        onCreate(db);
    }

    // Add a patient result
    public boolean addPatientResult(String userEmail, int heartRate, int bloodPressure, int oxygenLevels, float sleep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_EMAIL, userEmail);
        cv.put(COL_HEART_RATE, heartRate);
        cv.put(COL_BLOOD_PRESSURE, bloodPressure);
        cv.put(COL_OXYGEN_LEVELS, oxygenLevels);
        cv.put(COL_SLEEP, sleep);
        long result = db.insert(TABLE_RESULTS, null, cv);
        return result != -1;
    }

    // Get all results for a patient
    public Cursor getPatientResults(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USER_EMAIL + "=?";
        String[] selectionArgs = {userEmail};
        return db.query(TABLE_RESULTS, null, selection, selectionArgs, null, null, COL_ID + " DESC");
    }
}
