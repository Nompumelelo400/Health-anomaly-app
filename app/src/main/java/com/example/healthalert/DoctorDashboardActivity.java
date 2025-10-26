package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDashboardActivity extends AppCompatActivity {

    LinearLayout btnDashboard, btnProfile, btnMessages;
    ListView lvPatients;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        db = new SQLiteHelper(this);

        // --- Bind bottom navbar ---
        btnDashboard = findViewById(R.id.btn_dashboard);
        btnProfile = findViewById(R.id.btn_profile);
        btnMessages = findViewById(R.id.btn_messages);

        // --- Bind ListView ---
        lvPatients = findViewById(R.id.lvPatients);

        // --- Load patients ---
        loadPatients();

        // --- Bottom navbar clicks ---
        btnDashboard.setOnClickListener(v -> {}); // Current page

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(DoctorDashboardActivity.this, ProfileActivity.class));
            finish();
        });

        btnMessages.setOnClickListener(v -> {
            startActivity(new Intent(DoctorDashboardActivity.this, MessagesActivity.class));
            finish();
        });
    }

    private void loadPatients() {
        ArrayList<HashMap<String, String>> patientList = db.getAllPatients(); // We'll add this method in SQLiteHelper
        if (patientList.isEmpty()) {
            Toast.makeText(this, "No patients found", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                patientList,
                android.R.layout.simple_list_item_2,
                new String[]{"username", "email"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        lvPatients.setAdapter(adapter);
    }
}
