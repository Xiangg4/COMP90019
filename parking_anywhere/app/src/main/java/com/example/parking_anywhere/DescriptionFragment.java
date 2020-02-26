package com.example.parking_anywhere;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import firebase.models.ParkingHistoryInfo;

import static android.content.ContentValues.TAG;


public class DescriptionFragment extends Fragment {
    private View descriptionView;
    private CardView cardView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String bayId;

    private LatLng location;
    private boolean clickedMarker;
    private Button markButton;
    private String historyNode;
    private Boolean isSameBayMarked;
    private boolean isDifferentBayMarked;
    private String markedBayId = "";
    private int count;
    HashMap<String,Boolean> bayMarkedMap = new HashMap<String, Boolean>();

    public DescriptionFragment(CardView cardView) {
        this.cardView = cardView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        descriptionView = inflater.inflate(R.layout.fragment_description, container, false);
//        ImageButton closeButton = descriptionView.findViewById(R.id.des_closeButton);
//        markButton = descriptionView.findViewById(R.id.mark_button);

        descriptionView = inflater.inflate(R.layout.description_new, container, false);
        ImageButton closeButton = descriptionView.findViewById(R.id.closeDesc);
        markButton = descriptionView.findViewById(R.id.marker_new);


        descriptionView.setVisibility(View.GONE);


        closeButton.setOnClickListener(new buttonListener());

        mDatabase = FirebaseDatabase.getInstance().getReference();



        clickedMarker = false;

        markButton.setOnClickListener(new buttonListener());
        return descriptionView;
    }

    public void showDesc(String Available, String price, String[] time) {
        Log.d(TAG, "hello: ");
//        TextView availableText = (TextView) descriptionView.findViewById(R.id.des_ava);
//        TextView priceText = (TextView) descriptionView.findViewById(R.id.des_price);
        TextView availableText = (TextView) descriptionView.findViewById(R.id.Ava);
        TextView priceText = (TextView) descriptionView.findViewById(R.id.Price);

        ArrayList<TextView> timeList = new ArrayList<TextView>();
//        TextView timeText1 = (TextView) descriptionView.findViewById(R.id.des_time1);
//        TextView timeText2 = (TextView) descriptionView.findViewById(R.id.des_time2);
//        TextView timeText3 = (TextView) descriptionView.findViewById(R.id.des_time3);
//        TextView timeText4 = (TextView) descriptionView.findViewById(R.id.des_time4);
//        TextView timeText5 = (TextView) descriptionView.findViewById(R.id.des_time5);
//        TextView timeText6 = (TextView) descriptionView.findViewById(R.id.des_time6);

        TextView timeText1 = (TextView) descriptionView.findViewById(R.id.Time1);
        TextView timeText2 = (TextView) descriptionView.findViewById(R.id.Time2);
        TextView timeText3 = (TextView) descriptionView.findViewById(R.id.Time3);
        TextView timeText4 = (TextView) descriptionView.findViewById(R.id.Time4);
        TextView timeText5 = (TextView) descriptionView.findViewById(R.id.Time5);
        TextView timeText6 = (TextView) descriptionView.findViewById(R.id.Time6);

        timeList.add(timeText1);
        timeList.add(timeText2);
        timeList.add(timeText3);
        timeList.add(timeText4);
        timeList.add(timeText5);
        timeList.add(timeText6);

        for (int i = 1; i < timeList.size(); i++) {
            timeList.get(i).setVisibility(View.GONE);
        }


        availableText.setText(Available);
        String textPrice = "Price: $7";
        priceText.setText(textPrice);

        for (int i = 0; i < time.length; i++) {
            timeList.get(i).setVisibility(View.VISIBLE);
//            timeList.get(i).setText(time[i]);

            String t = time[i];
            String[] tsp = t.split(" ");
            String[] tsp2 = tsp[1].split("\n");
            Log.d("myUI","Descriprion get time: "+tsp[0]);
            Log.d("myUI","Descriprion get time: "+tsp2[0]);

            String timeText = tsp[0]+":  "+tsp2[0]+" ("+tsp2[1]+")";
            timeList.get(i).setText(timeText);
        }


        descriptionView.setVisibility(View.VISIBLE);

        Log.d("myUI", "show description fragment");

    }

    public void closeDesc() {
        descriptionView.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        Log.d("myUI", "closeDesc called.");
    }

    public void setBayInfo(String Id, LatLng coordinates) {
        bayId = Id;
        location = coordinates;

    }

    public void showNotice() {
        String message = "Please Login First";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Notice")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("myUI", "start loginActivity from description fragment.");
                        userProfileFirstActivity profileFirstActivity = new userProfileFirstActivity();
                        Intent intent1 = new Intent(getContext(), profileFirstActivity.getClass());
                        startActivity(intent1);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("myUI", "cancel button clicked from description fragment.");
                    }
                })
                .show();

    }
    public void showBayNotice(){
        String message = "Please unmark the last marked bay(ID:"+markedBayId+") first";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Notice")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDesc();
                    }
                })
                .show();
    }

    class buttonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closeDesc:
//                    descriptionView.setVisibility(View.GONE);
//                    cardView.setVisibility(View.GONE);
                    Log.d("myUI", "close button clicked");

                    closeDesc();
                    break;

                case R.id.marker_new:
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    //checkCurrentMarked(currentUser,bayId,location);

                    isSameBayMarked = bayMarkedMap.get(bayId);
                    if (isSameBayMarked==null){
                        isSameBayMarked = false;

                    }

                    if(clickedMarker) {


                        markButton.setText("Mark This Parking Bay");


                        Log.d("buttonTest", "setText");
                        clickedMarker = false;

                        if(mAuth.getCurrentUser() != null){

                            if(isSameBayMarked){
                                unMarkParkingBay(currentUser,bayId,location);
                                isSameBayMarked = false;
                            }else{
                                //Tell User go back to marked bay
                                showBayNotice();

                                markButton.setText("UNMARK");
                                clickedMarker = true;

                            }

                            //Implement Quit UI Here
                            closeDesc();
                        } else {
                            //Tell User to login[UI]
                            showNotice();
                        }

                    } else {
                        if (markButton.getText().equals("Mark This Parking Bay")) {
                            Log.d("buttonTest", markButton.getText().toString());
                            markButton.setText("UNMARK");
                        }

                        clickedMarker = true;
                        Log.d("myUI", "mark this parking bay button clicked");


                        if(mAuth.getCurrentUser() != null){


                            markParkingBay(currentUser, bayId, location);
                            //Implement Quit UI Here
                            closeDesc();
                        } else {
                            //Tell User to login[UI]
                            showNotice();
                        }
                    }


                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }

//        private void checkCurrentMarked(FirebaseUser currentUser, String bayId, LatLng location){
//            String userId = currentUser.getUid();
//            DatabaseReference historyRef = mDatabase.child("users").child(userId).child("history");
//
//
//            historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot hitory : dataSnapshot.getChildren()){
//                        //String historyKey = hitory.getKey();
//                        String key = hitory.child("bayId").getValue(String.class);
//                        Double lat = hitory.child("coordination").child("latitude").getValue(Double.class);
//                        Double lon = hitory.child("coordination").child("longitude").getValue(Double.class);
//                        Boolean oneNodeMarked = hitory.child("isMarked").getValue(Boolean.class);
//                        if(oneNodeMarked){
//                            LatLng singleCoord = new LatLng(lat,lon);
//
//                            if(bayId.equals(key)&&singleCoord.equals(location)){
//
//                                isSameBayMarked = true;
//
//                            }else{
//                                isDifferentBayMarked = true;
//                            }
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
        private void unMarkParkingBay(FirebaseUser currentUser, String bayId, LatLng location){
            String userId = currentUser.getUid();
            bayMarkedMap.remove(bayId);
//            DatabaseReference isMarkedRef = mDatabase.child("users").child(userId).child("history").child(historyNode).child("isMarked");
//            isMarkedRef.setValue(false);
            DatabaseReference historyRef = mDatabase.child("users").child(userId).child("history").child(historyNode).child("endTime");
            Date date = new Date();
            long time = date.getTime();
            historyRef.setValue(time + "");
            DatabaseReference startTimeRef = mDatabase.child("users").child(userId).child("history").child(historyNode).child("startTime");
            startTimeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long startTime = dataSnapshot.getValue(long.class);
                    long parkingDuration = time - startTime;
                    DatabaseReference durationRef = mDatabase.child("users").child(userId).child("history").child(historyNode).child("parkingDuration");
                    durationRef.setValue(parkingDuration);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        private void markParkingBay(FirebaseUser currentUser, String bayId, LatLng location) {

            markedBayId = bayId;



            bayMarkedMap.put(bayId,true);

            String userId = currentUser.getUid();
            ParkingHistoryInfo history = new ParkingHistoryInfo();

            //Need a method to get bayID
            history.bayId = bayId;


            Date date = new Date();
            long time = date.getTime();
            history.startTime = time;
            history.coordination = location;
            //history.isMarked = true;

            DatabaseReference historyRef = mDatabase.child("users").child(userId).child("history");
            Log.d("myUI", historyRef.getKey());
            DatabaseReference newHistoryRef = historyRef.push();
            historyNode = newHistoryRef.getKey();
            newHistoryRef.setValue(history);


        }
    }

}

