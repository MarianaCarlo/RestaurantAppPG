package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    editEmail.setError("¡El correo es requerido!");
                    editEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmail.setError("¡Por favor ingresa un correo válido!");
                    editEmail.requestFocus();
                    return;
                }

                if (name.isEmpty()) {
                    editName.setError("Nombre de usuario es requerido!");
                    editName.requestFocus();
                    return;
                }



                if (password.isEmpty()) {
                    editPassword.setError("¡La contraseña es requerida!");
                    editPassword.requestFocus();
                    return;
                }

                if (password.length() < 8) {
                    editPassword.setError("Contraseña mínima de 8 caracteres!");
                    editPassword.requestFocus();
                    return;
                }

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
                Matcher matcher = pattern.matcher(password);
                if (!matcher.matches()) {
                    editPassword.setError("La contraseña debe contener 1 mayuscula, 1 número, 1 caracter especial");
                    editPassword.requestFocus();
                    return;
                }

                String adress = "null";
                String cellphone = "null";

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(name, email, adress, cellphone);

                                    FirebaseDatabase.getInstance().getReference("Users").
                                            child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                                //redirect to login
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to register, ry again!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

            }
        });

    }
}