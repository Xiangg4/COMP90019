package sqlite.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


@Entity(tableName = "PARKING_SENSOR")
public class ParkingSensor {
    @PrimaryKey
    @ColumnInfo(name = "bayid")
    @SerializedName("bayid")
    private int bay_id;

    @ColumnInfo(name = "st_marker_id")
    @SerializedName("st_marker_id")
    private String st_marker_id;
    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status;
    @ColumnInfo(name = "location")
    @SerializedName("location")
    private String location;
    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    private String lat;
    @ColumnInfo(name = "lon")
    @SerializedName("lon")
    private String lon;

    private Date lastRefresh;

    private Integer occupation_freq;

    public Integer getOccupation_freq() {
        return occupation_freq;
    }

    public void setOccupation_freq(Integer occ_freq) {
        this.occupation_freq = occ_freq;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
