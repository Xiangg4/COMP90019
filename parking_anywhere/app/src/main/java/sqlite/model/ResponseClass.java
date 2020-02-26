package sqlite.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.parking_anywhere.RetroParkingBaySensor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseClass {

    @SerializedName("sensor")
    @Expose
    private List<RetroParkingBaySensor> sensor = null;

    public List<RetroParkingBaySensor> getSensor() {
        return sensor;
    }

    public void setSensor(List<RetroParkingBaySensor> sensor) {
        this.sensor = sensor;
    }

}
