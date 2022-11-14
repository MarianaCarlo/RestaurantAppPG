package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

public class NewSubscriptionActivity extends AppCompatActivity {

    String[] descriptionData = {"RegisterPayment", "ConfirmPayment", "ConfirmSubscription", "SaveSubscription"};
    Button buttonNextStep;
    //LayoutInflater inflater = getLayoutInflater();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subscription);

        StateProgressBar stateProgressBar = findViewById(R.id.progressBarNewSuscription);
        //stateProgressBar.setStateDescriptionData(descriptionData);
        buttonNextStep = findViewById(R.id.btnNextStep);

        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (stateProgressBar.getCurrentStateNumber()) {
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
                }
            }
        });

        /*stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                Toast.makeText(getApplicationContext(), "State clicked number is " + stateNumber, Toast.LENGTH_SHORT).show();
            }
        });*/


    }
}