package sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import sqlite.model.ParkingLocation;
import sqlite.model.ParkingLocationWithRestriction;

@Dao
public interface ParkingLocationWithRestrictionDAO {
    @Query("SELECT nosensor.*, PARKING_BAY.* FROM (SELECT * FROM PARKING_LOCATION WHERE PARKING_LOCATION.BayId NOT IN (SELECT PARKING_SENSOR.bayid FROM PARKING_SENSOR)) AS nosensor INNER JOIN PARKING_BAY ON nosensor.bayid = PARKING_BAY.BayId WHERE PARKING_BAY.TypeDesc1 NOT LIKE '%Disabled%'")
    LiveData<List<ParkingLocationWithRestriction>> getNormalBayWithoutSensor();

    @Query("Select nosensor.*, PARKING_BAY.* FROM (SELECT * FROM PARKING_LOCATION WHERE PARKING_LOCATION.BayId NOT IN (SELECT PARKING_SENSOR.bayid FROM PARKING_SENSOR)) AS nosensor INNER JOIN PARKING_BAY ON nosensor.bayid = PARKING_BAY.BayId WHERE PARKING_BAY.TypeDesc1 LIKE '%Disabled%'")
    LiveData<List<ParkingLocationWithRestriction>> getDisabledBayWithoutSensor();
}
