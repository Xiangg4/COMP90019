package firebase.models;

import com.google.android.gms.maps.model.LatLng;

public class ParkingHistoryInfo {
    public long startTime;
    public String bayId;
    public LatLng coordination;
    public long endTime;
    public long parkingDuration;
    //public boolean isMarked;

    public ParkingHistoryInfo() {
    }
}
