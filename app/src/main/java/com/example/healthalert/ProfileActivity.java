package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView tvUsername, tvEmail, tvRole, tvAge, tvMedicalAid;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvRole = findViewById(R.id.tvRole);
        tvAge = findViewById(R.id.tvAge);
        tvMedicalAid = findViewById(R.id.tvMedicalAid);
        btnBack = findViewById(R.id.btnBack);

        // Set fake user data
        tvUsername.setText("John Doe");
        tvEmail.setText("john.doe@example.com");
        tvRole.setText("Patient");
        tvAge.setText("29");
        tvMedicalAid.setText("HealthCare Plus");

        // Back button listener
        btnBack.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Going back", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
