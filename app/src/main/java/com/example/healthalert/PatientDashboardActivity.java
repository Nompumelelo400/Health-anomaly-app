package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PatientDashboardActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnAddResults, btnViewResults, btnProfile;
    String userEmail;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        db = new SQLiteHelper(this);

        // Get logged-in email from intent
        userEmail = getIntent().getStringExtra("email");

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAddResults = findViewById(R.id.btnAddResults);
        btnViewResults = findViewById(R.id.btnViewResults);
        btnProfile = findViewById(R.id.btnProfile);

        // Set welcome message
        tvWelcome.setText("Welcome, " + db.getUserByEmail(userEmail).get("username"));

        btnAddResults.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDashboardActivity.this, AddResultsTestActivity.class);
            intent.putExtra("email", userEmail);
            startActivity(intent);
        });

        btnViewResults.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDashboardActivity.this, ViewResultsTestActivity.class);
            intent.putExtra("email", userEmail);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDashboardActivity.this, ProfileActivity.class);
            intent.putExtra("email", userEmail);
            startActivity(intent);
        });
    }
}
