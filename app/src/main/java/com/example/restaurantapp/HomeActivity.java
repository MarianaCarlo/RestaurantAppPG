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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button buttonProfile;
    private int uStatusSubs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_home);

        buttonProfile = findViewById(R.id.btnProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        //SLIDES OF IMAGES
        ImageSlider imageSlider = findViewById(R.id.sliderImages);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.burger, null));
        slideModels.add(new SlideModel(R.drawable.sandwiches, null));
        slideModels.add(new SlideModel(R.drawable.sushi, null));
        imageSlider.setImageList(slideModels    , ScaleTypes.CENTER_CROP);

        //OBTAIN DE NAME OF THE USER
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView welcome = findViewById(R.id.helloUser);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    //Toast.makeText(HomeActivity.this, "estado: " + userProfile.statusSubs, Toast.LENGTH_SHORT).show();
                    uStatusSubs = userProfile.statusSubs;
                    String fullName = userProfile.name;

                    Calendar calendar = Calendar.getInstance();
                    int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                    //int minutes = calendar.get(Calendar.MINUTE);
                    String welcomeRcm = "! Tenemos las siguientes recomendaciones para tí: ";

                    if (hour24hrs >= 6 && hour24hrs < 12 ){
                        welcome.setText("Buenos días, " + fullName + welcomeRcm);
                    } else if (hour24hrs >= 12 && hour24hrs <= 6) {
                        welcome.setText("Buenas tardes, " + fullName + welcomeRcm);
                    } else {
                        welcome.setText("Buenas noches, " + fullName + welcomeRcm);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Algo salio mal", Toast.LENGTH_SHORT).show();
            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        return true;

                    case R.id.ic_subscription:
                        if (uStatusSubs == 1){
                            //startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
                            startActivity(new Intent(getApplicationContext(), ActualSubscriptionActivity.class));
                            overridePendingTransition(0,0);
                        } else if (uStatusSubs == 2) {
                            startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
                            overridePendingTransition(0,0);
                        } else {
                            Toast.makeText(HomeActivity.this, "mal estado", Toast.LENGTH_SHORT).show();
                        }

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