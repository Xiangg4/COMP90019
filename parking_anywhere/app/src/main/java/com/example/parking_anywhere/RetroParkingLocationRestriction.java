package com.example.parking_anywhere;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RetroParkingLocationRestriction {
    //Give the field a custom name//
    @SerializedName("bayid")
    private int bayId;


    @SerializedName("lon")
    private double Lon;

    @SerializedName("lat")
    private double Lat;

    @SerializedName("roadsegmentid")
    private String RoadSegmentId;

    @SerializedName("markerid")
    private String MarkerId;


    @SerializedName("meterid")
    private String MeterId;

    @SerializedName("roadsegmentdescription")
    private String RoadSegmentDescription;

    @SerializedName("lastedit")
    private long LastEdit;


    public RetroParkingLocationRestriction(double lon, double lat, int bayid,
                                           String roadsegmentid, String markerid, String meterid,
                                           String roadsegmentdescription, long lastedit) {
        this.Lon = lon;
        this.Lat = lat;
        this.bayId = bayid;
        this.RoadSegmentId = roadsegmentid;
        this.MarkerId = markerid;
        this.MeterId = meterid;
        this.RoadSegmentDescription = roadsegmentdescription;
        this.LastEdit = lastedit;
    }

    public int getBayId() {
        return bayId;
    }

    public void setBayId(int bayid) {
        bayId = bayid;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public String getRoadSegmentId() {
        return RoadSegmentId;
    }

    public void setRoadSegmentId(String roadsegmentid) {
        RoadSegmentId = roadsegmentid;
    }

    public String getMarkerId() {
        return MarkerId;
    }

    public void setMarkerId(String markerid) {
        MarkerId = markerid;
    }

    public String getMeterId() {
        return MeterId;
    }

    public void setMeterId(String meterid) {
        MeterId = meterid;
    }

    public String getRoadSegmentDescription() {
        return RoadSegmentDescription;
    }

    public void setRoadSegmentDescription(String roadsegmentdescription) {
        RoadSegmentDescription = roadsegmentdescription;
    }

    public long getLastEdit() {
        return LastEdit;
    }

    public void setLastEdit(long lastedit) {
        LastEdit = lastedit;
    }

}
