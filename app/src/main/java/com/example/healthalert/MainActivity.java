package com.example.healthalert;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    RadioButton rbDoctor, rbPatient;
    Button btnLogin;
    TextView tvSignUp;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Bind UI elements ---
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rbDoctor = findViewById(R.id.rbDoctor);
        rbPatient = findViewById(R.id.rbPatient);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        db = new SQLiteHelper(this);

        // --- Login Button Click ---
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = rbDoctor.isChecked() ? "doctor" : "patient";

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password required", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean valid = db.checkUser(email, password, role);
            if (valid) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                Intent intent;
                if (role.equals("patient")) {
                    intent = new Intent(MainActivity.this, PatientDashboardActivity.class);
                } else { // doctor
                    intent = new Intent(MainActivity.this, DoctorDashboardActivity.class);
                }

                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
            }
        });

        // --- Sign Up Click ---
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
