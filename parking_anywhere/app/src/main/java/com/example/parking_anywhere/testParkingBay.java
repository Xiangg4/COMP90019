package com.example.parking_anywhere;

public class testParkingBay {
    private double lat;
    private double lng;

    public testParkingBay(double lat,double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }
}