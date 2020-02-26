package com.example.parking_anywhere;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RetroParkingBaySensor {
    //Give the field a custom name//
    @SerializedName("bay_id")
    private int bay_id;

    @SerializedName("st_marker_id")
    private String st_marker_id;
    @SerializedName("status")
    private String status;

    public static class Location {
        @Expose
        @SerializedName("latitude")
        private String latitude;
        @SerializedName("longitude")
        private String longitude;
    }

    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;


    public int getBay_id() {
        return bay_id;
    }

    public void setBay_id(int bay_id) {
        this.bay_id = bay_id;
    }

    public String getSt_marker_id() {
        return st_marker_id;
    }

    public void setSt_marker_id(String st_marker_id) {
        this.st_marker_id = st_marker_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
