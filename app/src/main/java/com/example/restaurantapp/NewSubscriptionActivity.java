package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

public class NewSubscriptionActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button buttonNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subscription);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_subscription);

        CardForm cardForm = findViewById(R.id.cardForm);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .actionLabel("Purchase")
                .setup(NewSubscriptionActivity.this);
        cardForm.setCardNumberIcon(0);
        cardForm.setCardholderNameIcon(0);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        StateProgressBar stateProgressBar = findViewById(R.id.progressBarNewSuscription);
        //stateProgressBar.setStateDescriptionData(descriptionData);
        buttonNextStep = findViewById(R.id.btnNextStep);

        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*switch (stateProgressBar.getCurrentStateNumber()) {
                    case 1:
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        stateProgressBar.enableAnimationToCurrentState(true);
                        break;
                    case 2:
                        
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        stateProgressBar.enableAnimationToCurrentState(true);
                        break;
                    case 3:
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        stateProgressBar.enableAnimationToCurrentState(true);
                        break;
                    case 4:
                        stateProgressBar.setAllStatesCompleted(true);
                        stateProgressBar.enableAnimationToCurrentState(true);
                        break;
                }*/
                String cardNumber = cardForm.getCardNumber().toString();
                String cardName = cardForm.getCardholderName().toString();
                String cardMonth = cardForm.getExpirationMonth().toString();
                String cardYear = cardForm.getExpirationYear().toString();
                String cardCVV = cardForm.getCvv().toString();

                Intent intent = new Intent(NewSubscriptionActivity.this, NewSubscriptionStepTwoActivity.class);
                intent.putExtra("message_name", cardName);
                intent.putExtra("message_number", cardNumber);
                intent.putExtra("message_month", cardMonth);
                intent.putExtra("message_year", cardYear);
                intent.putExtra("message_cvv", cardCVV);
                startActivity(intent);
            }
        });

        /*stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                Toast.makeText(getApplicationContext(), "State clicked number is " + stateNumber, Toast.LENGTH_SHORT).show();
            }
        });*/

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