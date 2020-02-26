package sqlite.model;

import androidx.room.Embedded;

public class ParkingSensorWithRestriction {
    @Embedded
    ParkingBay parkingBay;
    @Embedded
    ParkingSensor parkingSensor;

    public ParkingSensor getParkingSensor(){return parkingSensor;}
    public void setParkingSensor(ParkingSensor sensor){parkingSensor = sensor;}

    public ParkingBay getParkingBay(){return parkingBay;}
    public void setParkingBay(ParkingBay bay){parkingBay = bay;}

}
