package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MessagesActivity extends AppCompatActivity {

    LinearLayout btnDashboard, btnProfile, btnMessages;
    ListView lvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        lvMessages = findViewById(R.id.lvMessages);

        // Example messages
        String[] messages = {"Your appointment is confirmed.", "Lab results are available.", "Time for your check-up."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, messages);
        lvMessages.setAdapter(adapter);

        // --- Bottom Navbar ---
        btnDashboard = findViewById(R.id.btn_dashboard);
        btnProfile = findViewById(R.id.btn_profile);
        btnMessages = findViewById(R.id.btn_messages);

        btnDashboard.setOnClickListener(v -> {
            startActivity(new Intent(MessagesActivity.this, DashboardActivity.class));
            finish();
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(MessagesActivity.this, ProfileActivity.class));
            finish();
        });

        // Messages is current page
        btnMessages.setOnClickListener(v -> {});
    }
}
