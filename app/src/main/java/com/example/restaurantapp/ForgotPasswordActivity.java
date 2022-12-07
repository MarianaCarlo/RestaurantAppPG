package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonResetPassword;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editForgotPasswordEmail);
        buttonResetPassword = findViewById(R.id.btnSendResetPassword);
        progressBar = findViewById(R.id.progressBarPassword);

        auth = FirebaseAuth.getInstance();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailToSend = editTextEmail.getText().toString().trim();

                if (emailToSend.isEmpty()) {
                    editTextEmail.setError("Email is required!");
                    editTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailToSend).matches()) {
                    editTextEmail.setError("Please enter a valid email");
                    editTextEmail.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(emailToSend).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(ForgotPasswordActivity.this, "Revista tu correo para resetear tu contraseña", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                            ImageView image = new ImageView(ForgotPasswordActivity.this);
                            image.setImageResource(R.drawable.ic_email_send);

                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                            builder.setMessage("Se ha enviado un correo electrónico para resetear tu contraseña!");
                            builder.setTitle("Revisa tu correo");
                            builder.setCancelable(false);
                            builder.setPositiveButton("IR A INICIO", (DialogInterface.OnClickListener) (dialog, which) -> {
                                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            });
                            builder.setView(image);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();


                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Hubo un problema! Prueba nuevamente", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}