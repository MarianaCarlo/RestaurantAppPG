package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.restaurantapp.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class EditProfileDataActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private User userD;

    private EditText editName, editPassword, editAddress, editPhone, editBirthdate;
    private TextView editEmail;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_data);

        editName = findViewById(R.id.getName);
        editPassword = findViewById(R.id.getPassword);
        editAddress = findViewById(R.id.getAddress);
        editPhone = findViewById(R.id.getPhone);
        editBirthdate = findViewById(R.id.getBirthdate);
        editEmail = findViewById(R.id.getEmail);
        buttonUpdate = findViewById(R.id.btnUpdateProfileData);
    }
}