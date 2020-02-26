package sqlite.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "PARKING_LOCATION")
public class ParkingLocation {
    @PrimaryKey
    @ColumnInfo(name = "bayId")
    @SerializedName("bayId")
    private int bayId;

    @ColumnInfo(name = "Lon")
    @SerializedName("Lon")
    private double Lon;

    @ColumnInfo(name = "Lat")
    @SerializedName("Lat")
    private Double Lat;

    @ColumnInfo(name = "RoadSegmentId")
    @SerializedName("roadsegmentid")
    private String RoadSegmentId;

    @ColumnInfo(name = "markerid")
    @SerializedName("markerid")
    private String MarkerId;

    @ColumnInfo(name = "meterid")
    @SerializedName("meterid")
    private String MeterId;

    @ColumnInfo(name = "roadsegmentdescription")
    @SerializedName("roadsegmentdescription")
    private String RoadSegmentDescription;

    @ColumnInfo(name = "lastedit")
    @SerializedName("lastedit")
    private long LastEdit;


    public int getBayId() {
        return bayId;
    }

    public void setBayId(int bayid) {
        bayId = bayid;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public Double getLat() {
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
