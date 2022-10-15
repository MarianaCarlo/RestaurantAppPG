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

                String adress = "null";
                String cellphone = "null";

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(email, name, adress, cellphone);

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