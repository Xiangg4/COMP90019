package sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import sqlite.model.ParkingSensor;



@Dao
public interface ParkingSensorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ParkingSensor... parkingSensor);
    @Update
    public void update(ParkingSensor... parkingSensor);
    @Delete
    public void delete(ParkingSensor parkingSensor);

    //Customized query can be written here

    @Query("SELECT * FROM PARKING_SENSOR")
    List<ParkingSensor> getAllData();
    @Query("SELECT * FROM PARKING_SENSOR")
    LiveData<List<ParkingSensor>> getAllLiveData();
    @Query("SELECT * FROM PARKING_SENSOR WHERE bayid = :parkingBayId AND lastRefresh > :lastRefreshMax LIMIT 1")
    ParkingSensor hasData(int parkingBayId, Date lastRefreshMax);
    @Query("SELECT status FROM PARKING_SENSOR WHERE bayid = :parkingBayId")
    LiveData<String> isOccupied (int parkingBayId);
}




