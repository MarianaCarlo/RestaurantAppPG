package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileActivity extends AppCompatActivity {

    private Button buttonProfileData;
    private Button buttonProfileConsumePending;
    private Button buttonProfileMyPoints;
    private Button buttonProfileSettingApp;
    private Button buttonProfileSignOut;

    public static final String SHARED_PREFS = "sharedPrefs";

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        buttonProfileData = findViewById(R.id.btnProfileData);
        buttonProfileConsumePending = findViewById(R.id.btnProfileConsumePending);
        buttonProfileMyPoints = findViewById(R.id.btnProfileMyPoints);
        buttonProfileSettingApp = findViewById(R.id.btnProfileSettingApp);
        buttonProfileSignOut = findViewById(R.id.btnProfileSignOut);


        buttonProfileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileDataActivity.class);
                startActivity(intent);
            }
        });

        buttonProfileSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = new ImageView(ProfileActivity.this);
                image.setImageResource(R.drawable.ic_crying_emoji);

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("¿Estas seguro que deseas salir?");
                builder.setTitle("¡ALERTA!");
                builder.setCancelable(false);
                builder.setPositiveButton("SI", (DialogInterface.OnClickListener) (dialog, which) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("name", "");
                    editor.apply();

                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                });

                builder.setNegativeButton("NO", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });

                builder.setView(image);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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