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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmailLogin, editPasswordLogin;
    private Button buttonSendLogin;
    private ProgressBar progressBarLogin;
    private TextView sendForgotPassword;

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
        sendForgotPassword = findViewById(R.id.sendForgotPassword);

        buttonSendLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmailLogin.getText().toString().trim();
                String password = editPasswordLogin.getText().toString().trim();

                if (email.isEmpty()) {
                    editEmailLogin.setError("¡El correo es requerido!");
                    editEmailLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmailLogin.setError("¡Por favor ingresa un correo válido!");
                    editEmailLogin.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editPasswordLogin.setError("¡La contraseña es requerida!");
                    editPasswordLogin.requestFocus();
                    return;
                }

                if (password.length() < 8) {
                    editPasswordLogin.setError("Contraseña mínima de 8 caracteres");
                    editPasswordLogin.requestFocus();
                    return;
                }

                progressBarLogin.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            /*if (user.isEmailVerified()){
                                //redirect to user home
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(LoginActivity.this, "Check your email to verify yout account", Toast.LENGTH_SHORT).show();
                            }*/


                        } else {
                            Toast.makeText(LoginActivity.this, "Error al ingresar! Por favor verifica tus datos", Toast.LENGTH_SHORT).show();
                            progressBarLogin.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        sendForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}