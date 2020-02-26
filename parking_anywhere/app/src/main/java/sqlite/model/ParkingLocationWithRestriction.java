package sqlite.model;

import androidx.room.Embedded;

public class ParkingLocationWithRestriction {
    @Embedded
    ParkingBay parkingBay;
    @Embedded
    ParkingLocation parkingLocation;

    public ParkingLocation getParkingLocation(){return parkingLocation;}
    public void setParkingLocation(ParkingLocation location){parkingLocation = location;}

    public ParkingBay getParkingBay(){return parkingBay;}
    public void setParkingBay(ParkingBay bay){parkingBay = bay;}

}
