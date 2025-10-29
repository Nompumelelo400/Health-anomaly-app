package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewResultsTestActivity extends AppCompatActivity {

    private LinearLayout layoutResults;
    private ArrayList<HashMap<String, String>> dummyResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results_test);

        layoutResults = findViewById(R.id.layoutResults);

        // Create dummy results
        dummyResults = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            HashMap<String, String> result = new HashMap<>();
            result.put("heartRate", 60 + i*5 + " bpm");
            result.put("bloodPressure", 110 + i*5  +" BP");
            result.put("oxygenLevels", 70 + i*2 + "%");
            result.put("sleep", 6 + i*0.5 + " h");
            dummyResults.add(result);
        }

        displayResults();
    }

    private void displayResults() {
        layoutResults.removeAllViews(); // Clear previous views

        for (int i = 0; i < dummyResults.size(); i++) {
            final int index = i;
            HashMap<String, String> result = dummyResults.get(i);

            // Create a container for each result
            LinearLayout resultLayout = new LinearLayout(this);
            resultLayout.setOrientation(LinearLayout.VERTICAL);
            resultLayout.setPadding(20, 20, 20, 20);
            resultLayout.setBackgroundColor(0xFFE0E0E0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 20);
            resultLayout.setLayoutParams(layoutParams);

            // Result text
            TextView tvResult = new TextView(this);
            tvResult.setText(
                    "Heart Rate: " + result.get("heartRate") + "\n" +
                            "Blood Pressure: " + result.get("bloodPressure") + "\n" +
                            "Oxygen Levels: " + result.get("oxygenLevels") + "\n" +
                            "Sleep: " + result.get("sleep")
            );
            tvResult.setTextSize(16);
            resultLayout.addView(tvResult);

            // Buttons container
            LinearLayout buttonsLayout = new LinearLayout(this);
            buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Edit button
            Button btnEdit = new Button(this);
            btnEdit.setText("Edit");
            btnEdit.setOnClickListener(v -> {
                Toast.makeText(ViewResultsTestActivity.this, "Edit clicked for result " + (index + 1), Toast.LENGTH_SHORT).show();
                // You can implement editing here later
            });

            // Delete button
            Button btnDelete = new Button(this);
            btnDelete.setText("Delete");
            btnDelete.setOnClickListener(v -> {
                dummyResults.remove(index);
                Toast.makeText(ViewResultsTestActivity.this, "Deleted result " + (index + 1), Toast.LENGTH_SHORT).show();
                displayResults(); // Refresh the view
            });

            buttonsLayout.addView(btnEdit);
            buttonsLayout.addView(btnDelete);

            resultLayout.addView(buttonsLayout);

            layoutResults.addView(resultLayout);
        }

        if (dummyResults.isEmpty()) {
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText("No results to display.");
            tvEmpty.setTextSize(16);
            layoutResults.addView(tvEmpty);
        }
    }
}
