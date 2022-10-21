package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantapp.fragments.AnnouncementsFragment;
import com.example.restaurantapp.fragments.FoodFragment;
import com.example.restaurantapp.fragments.HomeFragment;
import com.example.restaurantapp.fragments.SubscriptionFragment;
import com.example.restaurantapp.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    //private var home_fm = HomeFragment();

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SubscriptionFragment subscriptionFragment = new SubscriptionFragment();
    FoodFragment foodFragment = new FoodFragment();
    AnnouncementsFragment announcementsFragment = new AnnouncementsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                        return true;
                    case R.id.ic_subscription:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,subscriptionFragment).commit();
                        return true;
                    case R.id.ic_food:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,foodFragment).commit();
                        return true;
                    case R.id.ic_announcement:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,announcementsFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }
}