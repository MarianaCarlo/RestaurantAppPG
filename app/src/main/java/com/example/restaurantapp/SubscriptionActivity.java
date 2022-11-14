package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SubscriptionActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button buttonObtainNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_subscription);

        buttonObtainNew = findViewById(R.id.btnSendObtain);

        buttonObtainNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SubscriptionActivity.this, "steps", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubscriptionActivity.this, NewSubscriptionActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_subscription:
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