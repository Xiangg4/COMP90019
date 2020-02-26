package com.example.parking_anywhere;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import sqlite.model.ParkingSensor;


public class BlankFragment extends Fragment {

    private GoogleMap mm;
    private Location userGPS;
    private Marker gpsMarker;
    private SupportMapFragment mapFragment;



    public BlankFragment() {
        // Required empty public constructor


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mm = mMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                if(userGPS!=null){
                    LatLng usergps = new LatLng(userGPS.getLatitude(),userGPS.getLongitude());
                    //display on map
                    gpsMarker = mm.addMarker(new MarkerOptions().position(usergps).title("user current position"));
                    mm.moveCamera(CameraUpdateFactory.newLatLng(usergps));
                    Log.d("myGPS","placeGPS at onCreatedView:"+usergps.toString());
                }

            }

        });


        return rootView;
    }


    public void placeGPS(Location location) {
        userGPS = location;
        Log.d("myGPS","placeGPS called");
        if(mm==null){
            Log.d("myGPS","placeGPS: google map is null");
        }
        if (mm != null && location!=null) {
//            //user current GPS
//            double[] gps = new double[2];
//            gps[0] = -37.8;
//            gps[1] = 144.9;
//            LatLng userGps = new LatLng(gps[0], gps[1]);
//
//            //display on map
//            mm.moveCamera(CameraUpdateFactory.newLatLng(userGps));
//            mm.addMarker(new MarkerOptions().position(userGps).title("user current position"));
            LatLng usergps = new LatLng(userGPS.getLatitude(),userGPS.getLongitude());
            //display on map
            gpsMarker = mm.addMarker(new MarkerOptions().position(usergps).title("user current position"));
            mm.moveCamera(CameraUpdateFactory.newLatLng(usergps));
            Log.d("myGPS","placeGPS:"+usergps.toString());
        }
    }

    public void removeGPS(){
        if(mm != null && gpsMarker!=null){
            gpsMarker.remove();
            Log.d("myGPS","gps marker removed in blankFragment");
        }


    }



    public void placeMarker(List<ParkingSensor> pos) {
        Log.d("myMap","placeMarker called");
        if (mm != null) {
            //display on map
            if (!pos.isEmpty()) {
                for (int i =0; i<pos.size();i++){
                    //Object temp = pos.get(i);
                    Log.d("locations", "tedsting");
                    //testParkingBay t = (testParkingBay) temp
                    double lat = Double.parseDouble(pos.get(i).getLat());
                    double lon = Double.parseDouble(pos.get(i).getLon());
                    LatLng parking = new LatLng(lat,lon);
                    Log.d("locations", parking+" ");

                    mm.addMarker(new MarkerOptions().position(parking).title(pos.get(i).getBay_id()+" "));
                }

            }
        }
    }



}


