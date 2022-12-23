package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewSubscriptionStepTwoActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public TextView getCardName, getCardNumber, getCardMonth, getCardYear, getCardCVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.righttoleft, R.anim.lefttoright);
        setContentView(R.layout.activity_new_subscription_step_two);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getCardName = findViewById(R.id.getNameCard);
        getCardNumber = findViewById(R.id.getNumberCard);
        getCardMonth = findViewById(R.id.getMonthCard);
        getCardYear = findViewById(R.id.getYearCard);
        getCardCVV = findViewById(R.id.getCVVCard);

        Intent intent = getIntent();
        String strNameCard = intent.getStringExtra("message_name");
        String strNumberCard = intent.getStringExtra("message_number");
        String strMonthCard = intent.getStringExtra("message_month");
        String strYearCard = intent.getStringExtra("message_year");
        String strCVVCard = intent.getStringExtra("message_cvv");

        getCardName.setText(strNameCard);
        getCardNumber.setText(strNumberCard);
        getCardMonth.setText(strMonthCard);
        getCardYear.setText(strYearCard);
        getCardCVV.setText(strCVVCard);


        /*-------------------------------MENU NAVBAR----------------------*/
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