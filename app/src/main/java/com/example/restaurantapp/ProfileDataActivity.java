package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantapp.models.User;
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

    private Button buttonLogout;
    private ImageButton buttonEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);

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
    }
}