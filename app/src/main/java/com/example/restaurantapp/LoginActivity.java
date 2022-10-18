package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmailLogin, editPasswordLogin;
    private Button buttonSendLogin;
    private ProgressBar progressBarLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmailLogin = findViewById(R.id.editEmailLogin);
        editPasswordLogin = findViewById(R.id.editPasswordLogin);
        buttonSendLogin = findViewById(R.id.btnSendLogin);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        mAuth = FirebaseAuth.getInstance();

        buttonSendLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmailLogin.getText().toString().trim();
                String password = editPasswordLogin.getText().toString().trim();

                if (email.isEmpty()) {
                    editEmailLogin.setError("Email is required!");
                    editEmailLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmailLogin.setError("Please enter a valid email");
                    editEmailLogin.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editPasswordLogin.setError("Password is required");
                    editPasswordLogin.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editPasswordLogin.setError("Min password length is 6 characters");
                    editPasswordLogin.requestFocus();
                    return;
                }

                progressBarLogin.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                //redirect to user home
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(LoginActivity.this, "Check your email to verify yout account", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}