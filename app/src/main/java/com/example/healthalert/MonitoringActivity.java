package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MonitoringActivity extends AppCompatActivity {

    TextView tvHeartRate, tvBloodPressure, tvOxygen, tvTemperature;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring); // ✅ matches the renamed XML

        tvHeartRate = findViewById(R.id.tvHeartRate);
        tvBloodPressure = findViewById(R.id.tvBloodPressure);
        tvOxygen = findViewById(R.id.tvOxygen);
        tvTemperature = findViewById(R.id.tvTemperature);
        btnBack = findViewById(R.id.btnBack);

        // Dummy values for now
        tvHeartRate.setText("Heart Rate: 75 bpm");
        tvBloodPressure.setText("Blood Pressure: 120 / 80 mmHg");
        tvOxygen.setText("Oxygen Saturation: 98 %");
        tvTemperature.setText("Temperature: 36.8 °C");

        btnBack.setOnClickListener(v -> finish());
    }
}
