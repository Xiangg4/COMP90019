package sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import org.junit.internal.matchers.CombinableMatcher;

import java.util.List;

import sqlite.model.ParkingBay;
import sqlite.model.ParkingSensor;
import sqlite.model.ParkingLocation;
import sqlite.model.ParkingSensorWithRestriction;

@Dao
public interface ParkingSensorWithRestrictionDAO {
    @Query("SELECT PARKING_SENSOR.*, PARKING_BAY.* FROM PARKING_SENSOR INNER JOIN PARKING_BAY ON PARKING_SENSOR.BayId = PARKING_BAY.BayId")
    LiveData<List<ParkingSensorWithRestriction>> getNormalBayWithSensor();

    @Query("SELECT PARKING_SENSOR.*, PARKING_BAY.* FROM PARKING_SENSOR INNER JOIN PARKING_BAY ON PARKING_SENSOR.BayId = PARKING_BAY.BayId WHERE PARKING_BAY.TypeDesc1 LIKE '%Disabled%'")
    LiveData<List<ParkingSensorWithRestriction>> getDisabledBayWithSensor();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT joinedTable.bayid AS iD, joinedTable.status AS status, " +
            "joinedTable.Lon AS lon, joinedTable.Lat AS lat, " +
            "joinedTable.FromDay1 AS fromDay1,joinedTable.FromDay2 AS fromDay2, " +
            "joinedTable.FromDay3 AS fromDay3 ,joinedTable.FromDay4 AS fromDay4, " +
            "joinedTable.FromDay5 AS fromDay5,joinedTable.FromDay6 AS fromDay6, " +
            "joinedTable.ToDay1 AS toDay1,joinedTable.ToDay2 AS toDay2, " +
            "joinedTable.ToDay3 AS toDay3,joinedTable.ToDay4 AS toDay4, " +
            "joinedTable.ToDay5 AS toDay5,joinedTable.ToDay6 AS toDay6," +
            "joinedTable.StartTime1 AS startTime1,joinedTable.StartTime2 AS startTime2," +
            "joinedTable.StartTime3 AS startTime3,joinedTable.StartTime4 AS startTime4," +
            "joinedTable.StartTime5 AS startTime5,joinedTable.StartTime6 AS startTime6," +
            "joinedTable.EndTime1 AS endTime1,joinedTable.EndTime2 AS endTime2," +
            "joinedTable.EndTime3 AS endTime3,joinedTable.EndTime4 AS endTime4," +
            "joinedTable.EndTime5 AS endTime5,joinedTable.EndTime6 AS endTime6, " +
            "joinedTable.TypeDesc1 AS typeDesc1, joinedTable.TypeDesc2 AS typeDesc2, "+
            "joinedTable.TypeDesc3 AS typeDesc3, joinedTable.TypeDesc4 AS typeDesc4,"+
            "joinedTable.TypeDesc5 AS typeDesc5, joinedTable.TypeDesc6 AS typeDesc6, "+
            "joinedTable.EffectiveOnPH1 AS effectiveOnPH1, joinedTable.EffectiveOnPH2 AS effectiveOnPH2, "+
            "joinedTable.EffectiveOnPH3 AS effectiveOnPH3, joinedTable.EffectiveOnPH4 AS effectiveOnPH4, "+
            "joinedTable.EffectiveOnPH5 AS effectiveOnPH5, joinedTable.EffectiveOnPH6 AS effectiveOnPH6, "+
            "joinedTable.occupation_freq AS occupationFreq "+
            "FROM " +
            "(SELECT * FROM PARKING_BAY LEFT JOIN parking_location ON  PARKING_BAY.BayId = PARKING_LOCATION.bayId " +
            "LEFT JOIN PARKING_SENSOR ON PARKING_SENSOR.bayid = PARKING_BAY.BayId)" +
            "AS joinedTable " +
            "WHERE joinedTable.status LIKE '%Unoccupied%' OR joinedTable.status is null " +
            "AND ((joinedTable.Duration1 IS NOT NULL AND joinedTable.Duration1 >= :duration ) "+
            "OR (joinedTable.Duration2 IS NOT NULL AND joinedTable.Duration2 >= :duration )" +
            "OR (joinedTable.Duration3 IS NOT NULL AND joinedTable.Duration3 >= :duration )" +
            "OR (joinedTable.Duration4 IS NOT NULL AND joinedTable.Duration4 >= :duration )" +
            "OR (joinedTable.Duration5 IS NOT NULL AND joinedTable.Duration5 >= :duration )" +
            "OR (joinedTable.Duration6 IS NOT NULL AND joinedTable.Duration6 >= :duration ))"
    )

    LiveData<List<filteredBays>> getFilteredBays(int duration);


    @Query("SELECT joinedTable.bayid AS iD, joinedTable.status AS status, " +
//            ":currentLat AS GPSlat,:currentLon AS GPSlon, "+
            "joinedTable.Lon AS lon, joinedTable.Lat AS lat, " +
            "joinedTable.FromDay1 AS fromDay1,joinedTable.FromDay2 AS fromDay2, " +
            "joinedTable.FromDay3 AS fromDay3 ,joinedTable.FromDay4 AS fromDay4, " +
            "joinedTable.FromDay5 AS fromDay5,joinedTable.FromDay6 AS fromDay6, " +
            "joinedTable.ToDay1 AS toDay1,joinedTable.ToDay2 AS toDay2, " +
            "joinedTable.ToDay3 AS toDay3,joinedTable.ToDay4 AS toDay4, " +
            "joinedTable.ToDay5 AS toDay5,joinedTable.ToDay6 AS toDay6," +
            "joinedTable.StartTime1 AS startTime1,joinedTable.StartTime2 AS startTime2," +
            "joinedTable.StartTime3 AS startTime3,joinedTable.StartTime4 AS startTime4," +
            "joinedTable.StartTime5 AS startTime5,joinedTable.StartTime6 AS startTime6," +
            "joinedTable.EndTime1 AS endTime1,joinedTable.EndTime2 AS endTime2," +
            "joinedTable.EndTime3 AS endTime3,joinedTable.EndTime4 AS endTime4," +
            "joinedTable.EndTime5 AS endTime5,joinedTable.EndTime6 AS endTime6, " +
            "joinedTable.TypeDesc1 AS typeDesc1, joinedTable.TypeDesc2 AS typeDesc2, "+
            "joinedTable.TypeDesc3 AS typeDesc3, joinedTable.TypeDesc4 AS typeDesc4,"+
            "joinedTable.TypeDesc5 AS typeDesc5, joinedTable.TypeDesc6 AS typeDesc6, "+
            "joinedTable.EffectiveOnPH1 AS effectiveOnPH1, joinedTable.EffectiveOnPH2 AS effectiveOnPH2, "+
            "joinedTable.EffectiveOnPH3 AS effectiveOnPH3, joinedTable.EffectiveOnPH4 AS effectiveOnPH4, "+
            "joinedTable.EffectiveOnPH5 AS effectiveOnPH5, joinedTable.EffectiveOnPH6 AS effectiveOnPH6, "+
            "joinedTable.occupation_freq AS occupationFreq "+
            "FROM " +
            "(SELECT * FROM PARKING_BAY LEFT JOIN parking_location ON  PARKING_BAY.BayId = PARKING_LOCATION.bayId " +
            "LEFT JOIN PARKING_SENSOR ON PARKING_SENSOR.bayid = PARKING_BAY.BayId " +
            "WHERE (PARKING_LOCATION.Lat <= :currentLat + 0.01 AND PARKING_LOCATION.Lat >= :currentLat - 0.01)" +
            "AND (PARKING_LOCATION.Lon <= :currentLon + 0.01 AND PARKING_LOCATION.Lon >= :currentLon - 0.01)" +
            "AND ((PARKING_BAY.Duration1 IS NOT NULL AND PARKING_BAY.Duration1 >= :duration ) "+
            "OR (PARKING_BAY.Duration2 IS NOT NULL AND PARKING_BAY.Duration2 >= :duration )" +
            "OR (PARKING_BAY.Duration3 IS NOT NULL AND PARKING_BAY.Duration3 >= :duration )" +
            "OR (PARKING_BAY.Duration4 IS NOT NULL AND PARKING_BAY.Duration4 >= :duration )" +
            "OR (PARKING_BAY.Duration5 IS NOT NULL AND PARKING_BAY.Duration5 >= :duration )" +
            "OR (PARKING_BAY.Duration6 IS NOT NULL AND PARKING_BAY.Duration6 >= :duration )))" +
            "AS joinedTable " +
            "WHERE joinedTable.status LIKE '%Unoccupied%' OR joinedTable.status is null")

//    LiveData<List<filteredBays>> getNearbyBays(int duration, Double currentLat, Double currentLon);
    List<filteredBays> getNearbyBays(int duration, Double currentLat, Double currentLon);

     class filteredBays {
        public int iD;
        public String status;
        public double lon;
        public double lat;
        public String fromDay1;
        public String fromDay2;
        public String fromDay3;
        public String fromDay4;
        public String fromDay5;
        public String fromDay6;
        public String toDay1;
        public String toDay2;
        public String toDay3;
        public String toDay4;
        public String toDay5;
        public String toDay6;
        public String startTime1;
        public String startTime2;
        public String startTime3;
        public String startTime4;
        public String startTime5;
        public String startTime6;
        public String endTime1;
        public String endTime2;
        public String endTime3;
        public String endTime4;
        public String endTime5;
        public String endTime6;
        public String typeDesc1;
        public String typeDesc2;
        public String typeDesc3;
        public String typeDesc4;
        public String typeDesc5;
        public String typeDesc6;
        public Integer effectiveOnPH1;
        public Integer effectiveOnPH2;
        public Integer effectiveOnPH3;
        public Integer effectiveOnPH4;
        public Integer effectiveOnPH5;
        public Integer effectiveOnPH6;
        public Integer occupationFreq;

        @Ignore
        public boolean isDisabled;

        @Ignore
        public String combinedDescriptions;

        @Ignore
        public String splitDescriptions;

        @Ignore
        public String occupation;

         public void setIsDisabled(boolean disabled) {
             isDisabled = disabled;
         }

         public void setCombinedDescriptions(String descriptions) { combinedDescriptions = descriptions; }
         public void setSplitDescriptions(String descriptions) { splitDescriptions = descriptions; }

         public void setOccupation(String occ) { occupation = occ; }
     }


    //    @Query("SELECT PARKING_SENSOR.bayid AS iD, PARKING_SENSOR.status AS status, " +
//            "PARKING_LOCATION.Lon AS lon, PARKING_LOCATION.Lat AS lat, " +
//            "PARKING_BAY.FromDay1 AS fromDay1,PARKING_BAY.FromDay2 AS fromDay2, " +
//            "PARKING_BAY.FromDay3 AS fromDay3 ,PARKING_BAY.FromDay4 AS fromDay4, " +
//            "PARKING_BAY.FromDay5 AS fromDay5,PARKING_BAY.FromDay6 AS fromDay6, " +
//            "PARKING_BAY.ToDay1 AS toDay1,PARKING_BAY.ToDay2 AS toDay2, " +
//            "PARKING_BAY.ToDay3 AS toDay3,PARKING_BAY.ToDay4 AS toDay4, " +
//            "PARKING_BAY.ToDay5 AS toDay5,PARKING_BAY.ToDay6 AS toDay6," +
//            "PARKING_BAY.StartTime1 AS startTime1,PARKING_BAY.StartTime2 AS startTime2," +
//            "PARKING_BAY.StartTime3 AS startTime3,PARKING_BAY.StartTime4 AS startTime4," +
//            "PARKING_BAY.StartTime5 AS startTime5,PARKING_BAY.StartTime6 AS startTime6," +
//            "PARKING_BAY.EndTime1 AS endTime1,PARKING_BAY.EndTime2 AS endTime2," +
//            "PARKING_BAY.EndTime3 AS endTime3,PARKING_BAY.EndTime4 AS endTime4," +
//            "PARKING_BAY.EndTime5 AS endTime5,PARKING_BAY.EndTime6 AS endTime6, " +
//            "PARKING_BAY.TypeDesc1 AS typeDesc1, PARKING_BAY.TypeDesc2 AS typeDesc2, "+
//            "PARKING_BAY.TypeDesc3 AS typeDesc3, PARKING_BAY.TypeDesc4 AS typeDesc4,"+
//            "PARKING_BAY.TypeDesc5 AS typeDesc5, PARKING_BAY.TypeDesc6 AS typeDesc6, "+
//            "PARKING_BAY.EffectiveOnPH1 AS effectiveOnPH1, PARKING_BAY.EffectiveOnPH2 AS effectiveOnPH2, "+
//            "PARKING_BAY.EffectiveOnPH3 AS effectiveOnPH3, PARKING_BAY.EffectiveOnPH4 AS effectiveOnPH4, "+
//            "PARKING_BAY.EffectiveOnPH5 AS effectiveOnPH5, PARKING_BAY.EffectiveOnPH6 AS effectiveOnPH6, "+
//            "PARKING_SENSOR.status AS status "+
//            "FROM PARKING_SENSOR " +
//            "INNER JOIN PARKING_BAY, parking_location " +
//            "ON PARKING_SENSOR.bayid = PARKING_BAY.BayId AND PARKING_BAY.BayId = PARKING_LOCATION.bayId " +
//            "WHERE PARKING_SENSOR.status NOT LIKE '%Present%' " +
//            "AND ((PARKING_BAY.Duration1 IS NOT NULL AND PARKING_BAY.Duration1 >= :duration ) "+
//            "OR (PARKING_BAY.Duration2 IS NOT NULL AND PARKING_BAY.Duration2 >= :duration )" +
//            "OR (PARKING_BAY.Duration3 IS NOT NULL AND PARKING_BAY.Duration3 >= :duration )" +
//            "OR (PARKING_BAY.Duration4 IS NOT NULL AND PARKING_BAY.Duration4 >= :duration )" +
//            "OR (PARKING_BAY.Duration5 IS NOT NULL AND PARKING_BAY.Duration5 >= :duration )" +
//            "OR (PARKING_BAY.Duration6 IS NOT NULL AND PARKING_BAY.Duration6 >= :duration ))" )


//     @Query(
//            "SELECT PARKING_LOCATION.Lon AS lon, PARKING_LOCATION.Lat AS lat, " +
//            "PARKING_BAY.FromDay1 AS fromDay1,PARKING_BAY.FromDay2 AS fromDay2, " +
//            "PARKING_BAY.FromDay3 AS fromDay3 ,PARKING_BAY.FromDay4 AS fromDay4, " +
//            "PARKING_BAY.FromDay5 AS fromDay5,PARKING_BAY.FromDay6 AS fromDay6, " +
//            "PARKING_BAY.ToDay1 AS toDay1,PARKING_BAY.ToDay2 AS toDay2, " +
//            "PARKING_BAY.ToDay3 AS toDay3,PARKING_BAY.ToDay4 AS toDay4, " +
//            "PARKING_BAY.ToDay5 AS toDay5,PARKING_BAY.ToDay6 AS toDay6," +
//            "PARKING_BAY.StartTime1 AS startTime1,PARKING_BAY.StartTime2 AS startTime2," +
//            "PARKING_BAY.StartTime3 AS startTime3,PARKING_BAY.StartTime4 AS startTime4," +
//            "PARKING_BAY.StartTime5 AS startTime5,PARKING_BAY.StartTime6 AS startTime6," +
//            "PARKING_BAY.EndTime1 AS endTime1,PARKING_BAY.EndTime2 AS endTime2," +
//            "PARKING_BAY.EndTime3 AS endTime3,PARKING_BAY.EndTime4 AS endTime4," +
//            "PARKING_BAY.EndTime5 AS endTime5,PARKING_BAY.EndTime6 AS endTime6, " +
//            "PARKING_BAY.TypeDesc1 AS typeDesc1, PARKING_BAY.TypeDesc2 AS typeDesc2, "+
//            "PARKING_BAY.TypeDesc3 AS typeDesc3, PARKING_BAY.TypeDesc4 AS typeDesc4,"+
//            "PARKING_BAY.TypeDesc5 AS typeDesc5, PARKING_BAY.TypeDesc6 AS typeDesc6, "+
//            "PARKING_BAY.EffectiveOnPH1 AS effectiveOnPH1, PARKING_BAY.EffectiveOnPH2 AS effectiveOnPH2, "+
//            "PARKING_BAY.EffectiveOnPH3 AS effectiveOnPH3, PARKING_BAY.EffectiveOnPH4 AS effectiveOnPH4, "+
//            "PARKING_BAY.EffectiveOnPH5 AS effectiveOnPH5, PARKING_BAY.EffectiveOnPH6 AS effectiveOnPH6, "+
//            "PARKING_SENSOR.status AS status "+
//            "FROM PARKING_SENSOR " +
//            "INNER JOIN PARKING_BAY, parking_location " +
//            "ON PARKING_SENSOR.bayid = PARKING_BAY.BayId AND PARKING_BAY.BayId = PARKING_LOCATION.bayId " +
//            "WHERE PARKING_SENSOR.status NOT LIKE '%Present%' " +
//            "AND ((PARKING_BAY.Duration1 IS NOT NULL AND PARKING_BAY.Duration1 >= :duration ) "+
//            "OR (PARKING_BAY.Duration2 IS NOT NULL AND PARKING_BAY.Duration2 >= :duration )" +
//            "OR (PARKING_BAY.Duration3 IS NOT NULL AND PARKING_BAY.Duration3 >= :duration )" +
//            "OR (PARKING_BAY.Duration4 IS NOT NULL AND PARKING_BAY.Duration4 >= :duration )" +
//            "OR (PARKING_BAY.Duration5 IS NOT NULL AND PARKING_BAY.Duration5 >= :duration )" +
//            "OR (PARKING_BAY.Duration6 IS NOT NULL AND PARKING_BAY.Duration6 >= :duration ))" )

//    LiveData<List<filteredBays>> getStaticBays(int duration);
//
//     class staticBays {
//        public double lon;
//        public double lat;
//        public String fromDay1;
//        public String fromDay2;
//        public String fromDay3;
//        public String fromDay4;
//        public String fromDay5;
//        public String fromDay6;
//        public String toDay1;
//        public String toDay2;
//        public String toDay3;
//        public String toDay4;
//        public String toDay5;
//        public String toDay6;
//        public String startTime1;
//        public String startTime2;
//        public String startTime3;
//        public String startTime4;
//        public String startTime5;
//        public String startTime6;
//        public String endTime1;
//        public String endTime2;
//        public String endTime3;
//        public String endTime4;
//        public String endTime5;
//        public String endTime6;
//        public String typeDesc1;
//        public String typeDesc2;
//        public String typeDesc3;
//        public String typeDesc4;
//        public String typeDesc5;
//        public String typeDesc6;
//        public Integer effectiveOnPH1;
//        public Integer effectiveOnPH2;
//        public Integer effectiveOnPH3;
//        public Integer effectiveOnPH4;
//        public Integer effectiveOnPH5;
//        public Integer effectiveOnPH6;
//
//        @Ignore
//        public boolean isDisabled;
//
//        @Ignore
//        public List<String> combinedDescriptions;
//
//         public void setIsDisabled(boolean disabled) {
//             isDisabled = disabled;
//         }
//
//         public void setCombinedDescriptions(List<String> descriptions) { combinedDescriptions = descriptions; }
//
//     }
}
