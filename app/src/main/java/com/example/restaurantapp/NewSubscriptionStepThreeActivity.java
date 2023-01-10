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

import com.example.restaurantapp.models.PaymentCard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewSubscriptionStepThreeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button confirmSubs, noConfirmSubs;

    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference referenceCards;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subscription_step_three);
        this.overridePendingTransition(R.anim.righttoleft, R.anim.lefttoright);

        confirmSubs = findViewById(R.id.confirmSubscription);
        noConfirmSubs = findViewById(R.id.noConfirmSubscription);

        Intent intent = getIntent();
        String strNameCard = intent.getStringExtra("message_name");
        String strNumberCard = intent.getStringExtra("message_number");
        String strMonthCard = intent.getStringExtra("message_month");
        String strYearCard = intent.getStringExtra("message_year");
        String strCVVCard = intent.getStringExtra("message_cvv");

        //OBTAIN THE USER
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //TABLE PF USERCARDS
        referenceCards = FirebaseDatabase.getInstance().getReference("UserCards");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_subscription);

        noConfirmSubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(NewSubscriptionStepThreeActivity.this, NewSubscriptionStepTwoActivity.class);
                startActivity(intentBack);
            }
        });


        confirmSubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uuid = UUID.randomUUID().toString().replace("-", "");
                PaymentCard paymentCard = new PaymentCard(strNameCard, strNumberCard, strMonthCard+"/"+strYearCard, Integer.parseInt(strCVVCard), userID);
                referenceCards.child(uuid).setValue(paymentCard);

                Intent intent = new Intent(NewSubscriptionStepThreeActivity.this, NewSubscriptionStepFourActivity.class);
                startActivity(intent);
            }
        });


        /*---------------------------------------MENU NAVBAR--------------------------------------*/
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