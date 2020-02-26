package com.example.parking_anywhere;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {
    double count = 0.0;
    long totalDuration = 0;
    Double lat = null;
    Double lon = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        if (user != null) {
            // Name, email address, and profile photo Url

            String userId = user.getUid();


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();


            ref.child("users").child(userId).child("history").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(dataSnapshot.getKey()+"mychildchanged",dataSnapshot.getChildrenCount() + "");
                    long numOfParkings = dataSnapshot.getChildrenCount();
                    long parkingSpent = numOfParkings*7;

                    TextView myHistoryTextView = (TextView) findViewById(R.id.history_view);
                    myHistoryTextView.setText(numOfParkings+"");

                    TextView totalSpentTextView = (TextView) findViewById(R.id.total_Spent);
                    totalSpentTextView.setText("$"+parkingSpent+"");

                    HashMap<String, Integer> parkingDict = new HashMap<String, Integer>();

                    for (DataSnapshot hitory : dataSnapshot.getChildren()){
                        count += 1;


                        long parkingDuration = hitory.child("parkingDuration").getValue(long.class);
                        totalDuration += parkingDuration;

                        String key = hitory.child("bayId").getValue(String.class);


                        if(parkingDict.containsKey(key)){
                            parkingDict.put(key,parkingDict.get(key)+1);
                        }else{
                            parkingDict.put(key,1);
                        }

                    }
                    int maxBayId;
                    if(parkingDict.values().size()==0){
                        maxBayId = -100;
                    }else{

                        maxBayId = Collections.max(parkingDict.values());
                    }
                    List<String> keys = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : parkingDict.entrySet()) {
                        if (entry.getValue()==maxBayId) {
                            keys.add(entry.getKey());
                        }
                    }
                    if(keys.size()>0){
                        String mostCommonBay = keys.get(0);

                        for (DataSnapshot hitory : dataSnapshot.getChildren()){

                            String key = hitory.child("bayId").getValue(String.class);
                            if(key.equals(mostCommonBay)){
                                lat = hitory.child("coordination").child("latitude").getValue(Double.class);
                                lon = hitory.child("coordination").child("longitude").getValue(Double.class);
                                Log.d("myCoord",lat+" "+lon);
                                break;
                            }
                        }


                    }
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    TextView myStreetTextView= (TextView) findViewById(R.id.favorite_street);
                    try {
                        if(lat!= null && lon != null){
                            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                            Log.d("myCoord","testing");
                            if (addresses != null) {
                                Address returnedAddress = addresses.get(0);
                                Log.d("myCoord",returnedAddress.getAddressLine(0)+"testing");
                                String[] parts = returnedAddress.getAddressLine(0).split(",");
                                String street = parts[0]; // 004
                                myStreetTextView.setText(street);
                            }
                            else {
                                Log.d("myCoord","noAddress");
                                myStreetTextView.setText("--");
                            }
                        }else{
                            myStreetTextView.setText("--");
                        }


                    }
                    catch (IOException e) {

                        myStreetTextView.setText("--");
                    }




                    double avgDuration = (totalDuration/count)/60000;
                    double avgDurationRoundUp = Math.round(avgDuration * 100);
                    avgDurationRoundUp = avgDurationRoundUp/100;
                    TextView myAvgDurationTextView= (TextView) findViewById(R.id.avg_Duration);
                    myAvgDurationTextView.setText(avgDurationRoundUp+"");


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            ref.child("users").child(userId).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TextView myTextView= (TextView) findViewById(R.id.myTextView);
                    String userName = dataSnapshot.getValue(String.class);
                    myTextView.setText(userName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

        }

        Button backButton = (Button)this.findViewById(R.id.back);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent in = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(in);
            }
        });

        Button logoutButton = (Button)this.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent in = new Intent(ProfileActivity.this,userProfileFirstActivity.class);
                startActivity(in);
            }
        });

    }


}

