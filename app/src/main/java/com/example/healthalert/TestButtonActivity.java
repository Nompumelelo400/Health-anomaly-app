package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class TestButtonActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_button);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Toast.makeText(TestButtonActivity.this, "Back button clicked!", Toast.LENGTH_SHORT).show();
            finish(); // closes this activity and goes back
        });
    }
}
