package com.example.healthalert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class AddResultsTestActivity extends AppCompatActivity {

    // Shared list for all results
    public static ArrayList<String> resultsList = new ArrayList<>();

    private EditText etHeartRate, etSteps, etCalories, etSleep;
    private Button btnSave, btnViewResults, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_results_test);

        // Initialize views
        etHeartRate = findViewById(R.id.etHeartRate);
        etSteps = findViewById(R.id.etSteps);
        etCalories = findViewById(R.id.etCalories);
        etSleep = findViewById(R.id.etSleep);
        btnSave = findViewById(R.id.btnSave);
        btnViewResults = findViewById(R.id.btnViewResults);
        btnBack = findViewById(R.id.btnBack);

        // Save button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResults();
            }
        });

        // View Results button
        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddResultsTestActivity.this, ViewResultsTestActivity.class);
                startActivity(intent);
            }
        });

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Go back to the previous screen
            }
        });
    }

    private void saveResults() {
        String heartRateStr = etHeartRate.getText().toString().trim();
        String stepsStr = etSteps.getText().toString().trim();
        String caloriesStr = etCalories.getText().toString().trim();
        String sleepStr = etSleep.getText().toString().trim();

        if (heartRateStr.isEmpty() || stepsStr.isEmpty() || caloriesStr.isEmpty() || sleepStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int heartRate = Integer.parseInt(heartRateStr);
        int steps = Integer.parseInt(stepsStr);
        int calories = Integer.parseInt(caloriesStr);
        double sleep = Double.parseDouble(sleepStr);

        String resultSummary = "Heart Rate: " + heartRate + " bpm\n"
                + "Steps: " + steps + "\n"
                + "Calories: " + calories + " kcal\n"
                + "Sleep: " + sleep + " hrs";

        resultsList.add(resultSummary);

        // Generate health estimation
        String estimation;
        if (heartRate < 60 || sleep < 5) {
            estimation = "⚠️ Your vitals suggest you might be fatigued or stressed.";
        } else if (heartRate > 100) {
            estimation = "🚨 Your heart rate is high. Consider taking a rest or seeking medical advice.";
        } else if (steps > 8000 && sleep >= 7) {
            estimation = "✅ You’re doing great! Keep maintaining this healthy routine.";
        } else {
            estimation = "🙂 You’re on track! Try to improve your sleep and activity balance.";
        }

        // Show popup dialog with results and estimation
        new AlertDialog.Builder(this)
                .setTitle("Result Saved")
                .setMessage(resultSummary + "\n\nEstimation:\n" + estimation)
                .setPositiveButton("OK", null)
                .show();

        // Clear inputs
        etHeartRate.setText("");
        etSteps.setText("");
        etCalories.setText("");
        etSleep.setText("");
    }
}
