package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etAge, etMedicalAid;
    RadioGroup rgRole;
    RadioButton rbDoctor, rbPatient;
    Button btnSignUp;
    TextView tvAlreadyHave;

    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // --- Bind views ---
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);
        etMedicalAid = findViewById(R.id.etMedicalAid);
        rgRole = findViewById(R.id.rgRole);
        rbDoctor = findViewById(R.id.rbDoctor);
        rbPatient = findViewById(R.id.rbPatient);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvAlreadyHave = findViewById(R.id.tvAlreadyHave);

        dbHelper = new SQLiteHelper(this);

        // --- Show/hide patient fields based on role ---
        rgRole.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPatient) {
                etAge.setVisibility(EditText.VISIBLE);
                etMedicalAid.setVisibility(EditText.VISIBLE);
            } else {
                etAge.setVisibility(EditText.GONE);
                etMedicalAid.setVisibility(EditText.GONE);
            }
        });

        // --- Handle Sign Up ---
        btnSignUp.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = rbDoctor.isChecked() ? "doctor" : "patient";
            String ageStr = etAge.getText().toString().trim();
            String medicalAid = etMedicalAid.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username, email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer age = null;
            if (role.equals("patient")) {
                if (ageStr.isEmpty() || medicalAid.isEmpty()) {
                    Toast.makeText(this, "Age and Medical Aid are required for patients", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Add user to SQLite
            boolean added = dbHelper.addUser(username, email, password, role, age, medicalAid);
            if (added) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, MainActivity.class)); // MainActivity is login
                finish();
            } else {
                Toast.makeText(this, "Account creation failed. Email may already exist.", Toast.LENGTH_LONG).show();
            }
        });

        // --- Already have an account? Go to login ---
        tvAlreadyHave.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        });
    }
}
