package com.example.restaurantapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.restaurantapp.ProfileActivity;
import com.example.restaurantapp.R;
import com.example.restaurantapp.models.User;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button buttonProfile;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    //int[] images = {R.drawable.sandwiches, R.drawable.sushi, R.drawable.burger};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View viewRoot = inflater.inflate(R.layout.fragment_home, container, false);
        buttonProfile = viewRoot.findViewById(R.id.btnProfile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //SLIDES OF IMAGES
        ImageSlider imageSlider = viewRoot.findViewById(R.id.sliderImages);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.burger, null));
        slideModels.add(new SlideModel(R.drawable.sandwiches, null));
        slideModels.add(new SlideModel(R.drawable.sushi, null));
        imageSlider.setImageList(slideModels    , ScaleTypes.CENTER_CROP);

        //OBTAIN DE NAME OF THE USER
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView welcome = viewRoot.findViewById(R.id.helloUser);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullName = userProfile.name;

                    Calendar calendar = Calendar.getInstance();
                    int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);

                    if (hour24hrs >= 6 && hour24hrs < 12 ){
                        welcome.setText("Buenos días, " + fullName + "! Son las " + hour24hrs + ":" + minutes);
                    } else if (hour24hrs >= 12 && hour24hrs <= 6) {
                        welcome.setText("Buenas tardes, " + fullName + "! Son las " + hour24hrs + ":" + minutes);
                    } else {
                        welcome.setText("Buenas noches, " + fullName + "! Son las " + hour24hrs + ":" + minutes);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeFragment.this.getActivity(), "Algo salio mal", Toast.LENGTH_SHORT).show();
            }
        });


        return viewRoot;
    }
}