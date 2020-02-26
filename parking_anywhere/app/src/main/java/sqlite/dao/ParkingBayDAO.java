package sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sqlite.model.ParkingBay;


@Dao
public interface ParkingBayDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ParkingBay... parkingBay);
    @Update
    public void update(ParkingBay... parkingBay);
    @Delete
    public void delete(ParkingBay parkingBay);

    //Customized query can be written here
    @Query("SELECT * FROM PARKING_BAY")
    LiveData<List<ParkingBay>> getAllData();
    @Query("SELECT * FROM PARKING_BAY WHERE bayid = :parkingBayId LIMIT 1")
    ParkingBay hasData(int parkingBayId);

    @Query("SELECT * FROM PARKING_BAY WHERE bayid = :parkingBayId")
    LiveData<ParkingBay> getBay(int parkingBayId);
}
