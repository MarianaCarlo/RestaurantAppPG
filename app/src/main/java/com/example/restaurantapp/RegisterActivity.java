package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editEmail, editPassword, editName;
    private Button buttonRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        progressBar = findViewById(R.id.progressBar);

        buttonRegister = findViewById(R.id.btnSendRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    editEmail.setError("Mail is required!");
                    editEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmail.setError("Please provide valid email");
                    editEmail.requestFocus();
                    return;
                }

                if (name.isEmpty()) {
                    editName.setError("Full name is required!");
                    editName.requestFocus();
                    return;
                }



                if (password.isEmpty()) {
                    editPassword.setError("Password is required!");
                    editPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editPassword.setError("Min password length should be 6 charaters!");
                    editPassword.requestFocus();
                    return;
                }

            }
        });

    }
}