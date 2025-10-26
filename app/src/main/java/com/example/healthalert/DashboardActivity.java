package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout btnDashboard, btnProfile, btnMessages;
    TextView heartRateText, stepsText, caloriesText, sleepText;
    SQLiteHelper db;
    String currentUserEmail; // We’ll pass this from login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // --- Initialize UI ---
        heartRateText = findViewById(R.id.textHeartRate);
        stepsText = findViewById(R.id.textSteps);
        caloriesText = findViewById(R.id.textCalories);
        sleepText = findViewById(R.id.textSleep);

        btnDashboard = findViewById(R.id.btn_dashboard);
        btnProfile = findViewById(R.id.btn_profile);
        btnMessages = findViewById(R.id.btn_messages);

        db = new SQLiteHelper(this);

        // --- Get current user email from intent ---
        currentUserEmail = getIntent().getStringExtra("email");
        if (currentUserEmail == null) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_LONG).show();
            finish();
        }

        // --- Load patient data ---
        loadPatientData(currentUserEmail);

        // --- Bottom Navbar ---
        btnDashboard.setOnClickListener(v -> {
            // Already on dashboard, do nothing
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            intent.putExtra("email", currentUserEmail);
            startActivity(intent);
            finish();
        });

        btnMessages.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MessagesActivity.class);
            intent.putExtra("email", currentUserEmail);
            startActivity(intent);
            finish();
        });
    }

    private void loadPatientData(String email) {
        // For now, we'll mock vitals data (later can store vitals in SQLite)
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM " + SQLiteHelper.TABLE_USERS + " WHERE email=? AND role=?",
                new String[]{email, "patient"}
        );

        if (cursor.moveToFirst()) {
            // Mock vitals; could later fetch from a "vitals" table
            heartRateText.setText("72 bpm");
            stepsText.setText("8,540 steps");
            caloriesText.setText("1,230 kcal");
            sleepText.setText("7h 45m");
        } else {
            Toast.makeText(this, "Patient data not found", Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }
}
