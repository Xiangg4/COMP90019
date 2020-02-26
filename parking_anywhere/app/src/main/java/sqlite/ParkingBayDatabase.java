package sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import sqlite.dao.ParkingBayDAO;
import sqlite.dao.ParkingLocationDAO;
import sqlite.dao.ParkingLocationWithRestrictionDAO;
import sqlite.dao.ParkingSensorDAO;
import sqlite.dao.ParkingSensorWithRestrictionDAO;
import sqlite.model.DateConverter;
import sqlite.model.ParkingBay;
import sqlite.model.ParkingLocation;
import sqlite.model.ParkingSensor;

@Database(entities = {ParkingBay.class, ParkingLocation.class, ParkingSensor.class}, version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class ParkingBayDatabase extends RoomDatabase {
    //This class is used for creating RoomDB
    private static final String DB_NAME = "parking_db";
    private static volatile ParkingBayDatabase INSTANCE;
    public abstract ParkingBayDAO getParkingBayDAO();
    public abstract ParkingLocationDAO getParkingLocationDAO();
    public abstract ParkingSensorDAO getParkingSensorDAO();
    public abstract ParkingLocationWithRestrictionDAO getParkingLocationWithRestrictionDAO();
    public abstract ParkingSensorWithRestrictionDAO getParkingSensorWithRestrictionDAO();

    public static synchronized ParkingBayDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ParkingBayDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
