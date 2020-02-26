package com.example.parking_anywhere;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import fragments.mapFragement;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, GPSCallback {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private mapFragement fragment;
    private static int USER_LOGIN = 567;
    DescriptionFragment description;
    CardView cardView;

    private GPSManager gpsManager = null;
    private double speed = 0.0;
    Boolean isGPSEnabled=false;
    LocationManager locationManager;
    double currentSpeed,kmphSpeed;
    Handler mHandler = new Handler();
    private boolean speedAskFlag = false;


    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            getCurrentSpeed();
            //update for each 1s
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Switch to welcome page
        if(PreferenceManager.shouldShowSlider()){
            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_main);
        Places.initialize(this, getString(R.string.google_maps_key));



        this.configureDagger();
        this.showMapFragment();
        this.startUIListen();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        mHandler.postDelayed(r, 100);

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    // ---

    private void showMapFragment(){

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
            startActivity(intent);
        }
        else {
            cardView = findViewById(R.id.main_card_view);

            cardView.setVisibility(View.GONE);

            View descriptionView = (View) findViewById(R.id.description_view);
            ImageButton des_close = (ImageButton)findViewById(R.id.des_closeButton);
            description = new DescriptionFragment(cardView);

            getSupportFragmentManager().beginTransaction().add(R.id.description_container, description, null).commit();

            fragment = new mapFragement();

            fragment.setCardView(cardView);

            fragment.setDescription(description);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, null).commit();
        }
        initSearch(fragment);


    }
    public void initSearch(mapFragement fragment){
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d("mySearch","Place: " + place.getName() + ", " + place.getId()+", "+place.getLatLng());
                LatLng placeCoord = place.getLatLng();
                fragment.moveMapCamera(placeCoord);


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.d("mySearch", "An error occurred: " + status);
            }
        });
    }

    public mapFragement getFragement()
    {
        return this.fragment;
    }

    public DescriptionFragment getDescription() {
        return description;
    }

    public void getCurrentSpeed(){
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        gpsManager = new GPSManager(MainActivity.this);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled) {
            gpsManager.startListening(MainActivity.this);
            gpsManager.setGPSCallback(this);
        } else {
            gpsManager.showSettingsAlert();
        }
    }
    @Override
    public void onGPSUpdate(Location location) {
        speed = location.getSpeed();
        currentSpeed = round(speed,3, BigDecimal.ROUND_HALF_UP);
        kmphSpeed = round((currentSpeed*3.6),3,BigDecimal.ROUND_HALF_UP);
        if (speedAskFlag == false)
        {
            if (kmphSpeed >= 20)
            {
                //asking window
                showNotice(kmphSpeed);
                speedAskFlag = true;
            }
        }
    }

    public void showNotice(double speed){
        String message = "PAccording to Australia Law, drivers cannot use phone when they are driving. You current speed is "+speed+" km/h.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notice")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("myUI","drive notice-ok clicked.");
                        speedAskFlag = true;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("myUI","drive notice-cancel clicked.close app.");
                        System.exit(0);

                    }
                })
                .show();

    }

    public static double round(double unrounded, int precision, int roundingMode) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }


    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void startUIListen(){
        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton);
        Button recommendButton=(Button)findViewById(R.id.recommend_button);
        RadioButton button1 = (RadioButton) findViewById(R.id.button_1_4P);
        RadioButton button2 = (RadioButton) findViewById(R.id.button_1_2P);
        RadioButton button3 = (RadioButton) findViewById(R.id.button_1P);
        RadioButton button4 = (RadioButton) findViewById(R.id.button_2P);
        RadioButton button5 = (RadioButton) findViewById(R.id.button_4P);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

//        Button signin_button = (Button)findViewById(R.id.signin_button);
//        Button login_button = (Button)findViewById(R.id.login_button);





        UIListen uiListen = new UIListen(profileButton,recommendButton,button1,button2,button3,button4,button5,radioGroup,description,MainActivity.this,cardView);
        Log.d("myUI","start UI listener in main activity.");

    }

    public void startOtherActivity(Activity a1){
        Intent intent1 = new Intent(MainActivity.this,a1.getClass());
        startActivity(intent1);
    }

    public void onDestroy() {
        super.onDestroy();
//        Log.d("on destroy called", "destroy");
        PreferenceManager.saveFirstTimeLaunch(true);
    }





}