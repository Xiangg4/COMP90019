package com.example.parking_anywhere;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import fragments.mapFragement;


public class UIListen extends AppCompatActivity {
    private ImageButton profileButton;
    private Button recommendButton;
    private RadioButton button1; //1_4p
    private RadioButton button2; //1_2p
    private RadioButton button3; //1p
    private RadioButton button4; //2p
    private RadioButton button5; //4p
    private DescriptionFragment description;
    private MainActivity main;
    private RadioGroup radioGroup;
    private CardView cardView;
    private FirebaseAuth auth;


    public UIListen(ImageButton pb1, Button recommendButton,RadioButton rb1, RadioButton rb2, RadioButton rb3, RadioButton rb4, RadioButton rb5, RadioGroup radioGroup, DescriptionFragment dv, MainActivity main, CardView cardView){
        this.profileButton = pb1;
        this.recommendButton = recommendButton;
        this.button1 = rb1;
        this.button2 = rb2;
        this.button3 = rb3;
        this.button4 = rb4;
        this.button5 = rb5;
        this.radioGroup = radioGroup;
        this.description = dv;
        this.main = main;
        this.cardView = cardView;

        this.profileButton.setOnClickListener(new UIOnClickListener());
        this.recommendButton.setOnClickListener(new UIOnClickListener());
        this.button1.setOnClickListener(new UIOnClickListener());
        this.button2.setOnClickListener(new UIOnClickListener());
        this.button3.setOnClickListener(new UIOnClickListener());
        this.button4.setOnClickListener(new UIOnClickListener());
        this.button5.setOnClickListener(new UIOnClickListener());

    }

    public void userProfile(){
        //need to verify user login
       auth = FirebaseAuth.getInstance();


        if(auth.getCurrentUser() != null){
            ProfileActivity p = new ProfileActivity();
            main.startOtherActivity(p);
        }else{
            userProfileFirstActivity first = new userProfileFirstActivity();
            main.startOtherActivity(first);
        }

    }


    class UIOnClickListener implements View.OnClickListener{
        private mapFragement mapfragement = main.getFragement();
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.recommend_button:
                    Log.d("highlight","highlight button clicked is listened from UIListen.");
                    //mapfragement.setDescription();
                    mapfragement.highlight(main.getDescription());

                    break;
                case R.id.profileButton:
                    //start Profile activity
                    Log.d("myUI","image button clicked is listened from UIListen.");

                    userProfile();
                    Log.d("myUI","start profile activity.");
                    break;

                case R.id.button_1_4P:
                    //show parking position >= 1/4P
                    Log.d("myUI","radio button 1 clicked.");
                    //call function in map to show;

                    mapfragement.clearMarkers();
                    mapfragement.addClusterMarkerItem(15);

//                    cardView.setVisibility(View.VISIBLE);
//                    String Available = "Available";
//                    String price = "$7";
//                    ArrayList<String> time = new ArrayList<String>();
//                    time.add("Mon-Fir 9:00-23:00");
//                    time.add("Sat-Sun 10:00-18:00");
//                    description.showDesc(Available, price, time);

                    break;
                case R.id.button_1_2P:

                    mapfragement.clearMarkers();
                    mapfragement.addClusterMarkerItem(30);

                    //show parking position >= 1/2P
                    Log.d("myUI","radio button 2 clicked.");
                    break;
                case R.id.button_1P:

                    mapfragement.clearMarkers();
                    mapfragement.addClusterMarkerItem(60);

                    //show parking position >= 1P
                    Log.d("myUI","radio button 3 clicked.");
                    break;
                case R.id.button_2P:

                    mapfragement.clearMarkers();
                    mapfragement.addClusterMarkerItem(120);

                    //show parking position >= 2P
                    Log.d("myUI","radio button 4 clicked.");
                    break;
                case R.id.button_4P:

                    mapfragement.clearMarkers();
                    mapfragement.addClusterMarkerItem(240);

                    //show parking position >= 4P
                    Log.d("myUI","radio button 5 clicked.");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());

            }

        }
    }



}




