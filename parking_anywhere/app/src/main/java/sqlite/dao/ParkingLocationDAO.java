package sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sqlite.model.ParkingLocation;

@Dao
public interface ParkingLocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ParkingLocation... parkingLocation);
    @Update
    public void update(ParkingLocation... parkingLocation);
    @Delete
    public void delete(ParkingLocation parkingLocation);

    //Customized query can be written here
    @Query("SELECT * FROM PARKING_LOCATION")
    List<ParkingLocation> getAllLocationData();

    @Query("SELECT * FROM PARKING_LOCATION WHERE bayid = :parkingBayId LIMIT 1")
    ParkingLocation hasData(int parkingBayId);

}
