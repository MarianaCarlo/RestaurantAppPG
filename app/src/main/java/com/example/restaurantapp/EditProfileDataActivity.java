package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileDataActivity extends AppCompatActivity {

    private FirebaseUser user;
    //private DatabaseReference reference;
    //private String userID;
    private User userD;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private String userID;

    private EditText editName, editPassword, editAddress, editPhone, editBirthdate;
    DatePickerDialog datePickerDialog;
    private TextView editEmail;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_data);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        editName = findViewById(R.id.getName);
        editPassword = findViewById(R.id.getPassword);
        editAddress = findViewById(R.id.getAddress);
        editPhone = findViewById(R.id.getPhone);
        editBirthdate = findViewById(R.id.getBirthdate);
        editEmail = findViewById(R.id.getEmail);
        buttonUpdate = findViewById(R.id.btnUpdateProfileData);

        editBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(EditProfileDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editBirthdate.setText(dayOfMonth + "/"
                                + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        //reference = firebaseDatabase.getReference("Users").child(userID);
        reference = FirebaseDatabase.getInstance().getReference("Users");
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
                    String birthdate = "20/11/1999";

                    editEmail.setText(email);
                    editName.setText(fullName);
                    editPassword.setText(password);
                    editAddress.setText(address);
                    editPhone.setText(String.valueOf(phone));
                    editBirthdate.setText(birthdate);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileDataActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editName.getText().toString().trim();
                String userEmail = editEmail.getText().toString().trim();
                String userPassword = editPassword.getText().toString().trim();
                String userAddress = editAddress.getText().toString().trim();
                int userPhone = Integer.parseInt(editPhone.getText().toString().trim());
                String userBirthdate = editBirthdate.getText().toString().trim();

                Map<String, Object> map = new HashMap<>();
                map.put("name", userName);
                map.put("email", userEmail);
                map.put("address", userAddress);
                map.put("cellphone", userPhone);

                reference.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //reference.updateChildren(map);
                        reference.child(userID).updateChildren(map);
                        Toast.makeText(EditProfileDataActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditProfileDataActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}