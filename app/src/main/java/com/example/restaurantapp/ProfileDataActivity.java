package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantapp.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class ProfileDataActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    BottomNavigationView bottomNavigationView;

    private Button buttonLogout;
    private ImageButton buttonEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        buttonLogout = findViewById(R.id.sendLogout);
        buttonEditProfile = findViewById(R.id.editProfile);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileDataActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView emailTextView = findViewById(R.id.getEmail);
        final TextView nameTextView = findViewById(R.id.getName);
        final TextView passwordTextView = findViewById(R.id.getPassword);
        final TextView addressTextView = findViewById(R.id.getAddress);
        final TextView cellphoneTextView = findViewById(R.id.getPhone);
        final TextView birthdateTextView = findViewById(R.id.getBirthdate);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String email = userProfile.email;
                    String fullName = userProfile.name;
                    String password = "passwordDefault";
                    String address = userProfile.address;
                    int phone = userProfile.cellphone;
                    String birthdate = userProfile.birthdate;

                    emailTextView.setText(email);
                    nameTextView.setText(fullName);
                    passwordTextView.setText(password);
                    addressTextView.setText(address);
                    cellphoneTextView.setText(String.valueOf(phone));
                    birthdateTextView.setText(birthdate);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileDataActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileDataActivity.this, EditProfileDataActivity.class);
                startActivity(intent);
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