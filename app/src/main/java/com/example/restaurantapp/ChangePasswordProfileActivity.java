package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private EditText editOldPassword, editNewPassword, editNewPasswordConfirm;
    private Button sendUpdatePassword;
    private ProgressBar progressBarPassword;

    private FirebaseUser user;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_profile);

        progressBarPassword = findViewById(R.id.progressBarPassword);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        editOldPassword = findViewById(R.id.getOldPassword);
        editNewPassword = findViewById(R.id.getNewPassword);
        editNewPasswordConfirm = findViewById(R.id.getNewPasswordConfirm);

        sendUpdatePassword = findViewById(R.id.btnUpdatePassword);

        sendUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = editOldPassword.getText().toString().trim();
                String newPassword = editNewPassword.getText().toString().trim();
                String newPasswordConfirm = editNewPasswordConfirm.getText().toString().trim();

                if (oldPassword.isEmpty()) {
                    editOldPassword.setError("Este campo es requerido");
                    editOldPassword.requestFocus();
                    return;
                }
                if (newPassword.isEmpty()) {
                    editNewPassword.setError("Este campo es requerido");
                    editNewPassword.requestFocus();
                    return;
                }
                if (newPasswordConfirm.isEmpty()) {
                    editNewPasswordConfirm.setError("Este campo es requerido");
                    editNewPasswordConfirm.requestFocus();
                    return;
                }

                if (newPassword.length() < 8) {
                    editNewPassword.setError("Contraseña mínima de 8 caracteres");
                    editNewPassword.requestFocus();
                    return;
                }

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
                Matcher matcher = pattern.matcher(newPassword);
                if (!matcher.matches()) {
                    editNewPassword.setError("La contraseña debe contener 1 mayuscula, 1 número, 1 caracter especial");
                    editNewPassword.requestFocus();
                    return;
                }

                progressBarPassword.setVisibility(View.VISIBLE);

                if (newPassword.equals(newPasswordConfirm)) {
                    if (user != null && user.getEmail() != null){

                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail().toString(), oldPassword);
                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //Log.d(TAG, "User re-authenticated.");

                                        if (task.isSuccessful()) {
                                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressBarPassword.setVisibility(View.GONE);
                                                        //Log.d(TAG, "Password updated");
                                                        //Toast.makeText(ChangePasswordProfileActivity.this, "Correcto! Contraseña actualizada", Toast.LENGTH_SHORT).show();
                                                        ImageView image = new ImageView(ChangePasswordProfileActivity.this);
                                                        image.setImageResource(R.drawable.ic_password_changed);

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordProfileActivity.this);
                                                        builder.setMessage("Contraseña actualizada correctamente");
                                                        builder.setTitle("MENSAJE");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                                                            Intent intent = new Intent(ChangePasswordProfileActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                        });

                                                        builder.setView(image);
                                                        AlertDialog alertDialog = builder.create();
                                                        alertDialog.show();


                                                    } else {
                                                        //Log.d(TAG, "Error password not updated")
                                                        Toast.makeText(ChangePasswordProfileActivity.this, "Error! Contraseña no actualizada", Toast.LENGTH_SHORT).show();
                                                        progressBarPassword.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        } else {
                                            //Log.d(TAG, "Error auth failed")
                                            Toast.makeText(ChangePasswordProfileActivity.this, "Error auth failed!", Toast.LENGTH_SHORT).show();
                                            progressBarPassword.setVisibility(View.GONE);
                                        }

                                    }
                                });

                    } else {
                        Toast.makeText(ChangePasswordProfileActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordProfileActivity.this, "Revisa la contraseña, no son iguales", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //********************************   MENU   **************************
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_subscription:
                        startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_food:
                        startActivity(new Intent(getApplicationContext(), FoodActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_announcement:
                        startActivity(new Intent(getApplicationContext(), AnnouncementsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

}