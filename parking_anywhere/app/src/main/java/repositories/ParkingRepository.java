package repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.parking_anywhere.GetData;
import com.example.parking_anywhere.RetroParkingBayRestriction;
import com.example.parking_anywhere.RetroParkingBaySensor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import sqlite.dao.ParkingBayDAO;
import sqlite.dao.ParkingLocationDAO;
import sqlite.dao.ParkingLocationWithRestrictionDAO;
import sqlite.dao.ParkingSensorDAO;
import sqlite.dao.ParkingSensorWithRestrictionDAO;
import sqlite.model.ParkingBay;
import sqlite.model.ParkingLocation;
import sqlite.model.ParkingLocationWithRestriction;
import sqlite.model.ParkingSensor;
import sqlite.model.ParkingSensorWithRestriction;

@Singleton
public class ParkingRepository {
    private static int FRESH_TIMEOUT_IN_MINUTES = 3;
    private final GetData webservice;
    private final ParkingBayDAO parkingBayDAO;
    private final ParkingSensorDAO parkingSensorDAO;
    private final ParkingLocationDAO parkingLocationDAO;
    private final ParkingLocationWithRestrictionDAO parkingLocationWithRestrictionDAO;
    private final ParkingSensorWithRestrictionDAO parkingSensorWithRestrictionDAO;

    private final Executor executor;

    //Create a handler for the RetrofitInstance interface//

    //GetData service = RetrofitClient.getRetrofitInstance().create(GetData.class);
    @Inject
    public ParkingRepository(GetData webservice, ParkingBayDAO parkingBayDAO, ParkingSensorDAO parkingSensorDAO, ParkingLocationDAO parkingLocationDAO, ParkingLocationWithRestrictionDAO parkingLocationWithRestrictionDAO, ParkingSensorWithRestrictionDAO parkingSensorWithRestrictionDAO, Executor executor) {
        this.webservice = webservice;
        this.parkingBayDAO = parkingBayDAO;
        this.parkingSensorDAO = parkingSensorDAO;
        this.parkingLocationDAO = parkingLocationDAO;
        this.parkingLocationWithRestrictionDAO = parkingLocationWithRestrictionDAO;
        this.parkingSensorWithRestrictionDAO = parkingSensorWithRestrictionDAO;
        this.executor = executor;
    }



    public LiveData<List<ParkingBay>> getParkingBayRestriction(int bayId) {
        boolean restrictionExists = (parkingBayDAO.hasData(bayId) != null);
        if(!restrictionExists) {
            Log.d("gettingTAG", "DATA fetched FROM NETWORK");

            //only fetch once

            webservice.getAllParkingBay()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bay -> loadDataList(bay), this::handleError1);

        }


        return parkingBayDAO.getAllData();

    }

    public LiveData<List<ParkingSensor>> getParkingBaySensor(int bayId) {
        refreshSensor(bayId);// try to refresh data if possible from Github Api

        return parkingSensorDAO.getAllLiveData();// return a LiveData directly from the database.


    }
    public LiveData<List<ParkingSensorWithRestrictionDAO.filteredBays>> getFilteredBays(int duration) {

        return parkingSensorWithRestrictionDAO.getFilteredBays(duration);// return a LiveData directly from the database.


    }

    public List<ParkingLocation> getParkingLocation(int bayId) {
        boolean restrictionExists = (parkingLocationDAO.hasData(bayId) != null);
        if(!restrictionExists) {
            //only fetch once
            loadLocationDataList();

        }
        return parkingLocationDAO.getAllLocationData();
    }


    public LiveData<List<ParkingLocationWithRestriction>> getNormalBayWithoutSensor() {
        return parkingLocationWithRestrictionDAO.getNormalBayWithoutSensor();
    }

    public LiveData<List<ParkingLocationWithRestriction>> getDisabledBayWithoutSensor() {
        return parkingLocationWithRestrictionDAO.getDisabledBayWithoutSensor();
    }

    public LiveData<List<ParkingSensorWithRestriction>> getNormalBayWithSensor() {
        return parkingSensorWithRestrictionDAO.getNormalBayWithSensor();
    }
    public LiveData<List<ParkingSensorWithRestriction>> getDisabledBayWithSensor() {
        return parkingSensorWithRestrictionDAO.getDisabledBayWithSensor();
    }

    public LiveData<ParkingBay> getBay(int bayID) {
        return parkingBayDAO.getBay(bayID);
    }

    public LiveData<String> getOccupationInfo(int bayID) {
        return parkingSensorDAO.isOccupied(bayID);
    }

    public List<ParkingSensorWithRestrictionDAO.filteredBays> getNearbyBays(int duration, Double lat, Double lon) {
        return parkingSensorWithRestrictionDAO.getNearbyBays(duration, lat, lon);
    }
//    public LiveData<List<ParkingSensorWithRestrictionDAO.filteredBays>> getNearbyBays(int duration, Double lat, Double lon) {
//        return parkingSensorWithRestrictionDAO.getNearbyBays(duration, lat, lon);
//    }


    private void refreshSensor(final int bayId) {

        executor.execute(() -> {
            boolean sensorExists = (parkingSensorDAO.hasData(bayId, getMaxRefreshTime(new Date())) != null);
            if(!sensorExists) {
                Log.e("TAG", "DATA REFRESHED FROM NETWORK");

                webservice.getAllParkingSensor()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(sensor -> loadSensorDataList(sensor), this::handleError2);

            }


        });

    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }



    //写两个handleError是为了测试到底是哪个observable出了问题
    private void handleError1(Throwable t) {
//        Log.e("myTag",t.toString());

    }
    private void handleError2(Throwable t) {


    }


    private void loadDataList(List<RetroParkingBayRestriction> retroParkingBayRestrictionResList) {

//Store parkingBayList to SQLite using room
        //This part can be done in AsyncTask in the future
        for(int i = 0; i< retroParkingBayRestrictionResList.size() ; i++){
            RetroParkingBayRestriction data = retroParkingBayRestrictionResList.get(i);
            Integer bayId = data.getBayId();
            Integer deviceId = data.getDeviceId();
            String description1 = data.getDescription1();
            String description2 = data.getDescription2();
            String description3 = data.getDescription3();
            String description4 = data.getDescription4();
            String description5 = data.getDescription5();
            String description6 = data.getDescription6();
            Integer disabilityExt1 = data.getDisabilityExt1();
            Integer disabilityExt2 = data.getDisabilityExt2();
            Integer disabilityExt3 = data.getDisabilityExt3();
            Integer disabilityExt4 = data.getDisabilityExt4();
            Integer disabilityExt5 = data.getDisabilityExt5();
            Integer disabilityExt6 = data.getDisabilityExt6();
            Integer duration1 = data.getDuration1();
            Integer duration2 = data.getDuration2();
            Integer duration3 = data.getDuration3();
            Integer duration4 = data.getDuration4();
            Integer duration5 = data.getDuration5();
            Integer duration6 = data.getDuration6();
            Integer effectiveonph1 = data.getEffectiveOnPH1();
            Integer effectiveonph2 = data.getEffectiveOnPH2();
            Integer effectiveonph3 = data.getEffectiveOnPH3();
            Integer effectiveonph4 = data.getEffectiveOnPH4();
            Integer effectiveonph5 = data.getEffectiveOnPH5();
            Integer effectiveonph6 = data.getEffectiveOnPH6();
            String endtime1 = data.getEndTime1();
            String endtime2 = data.getEndTime2();
            String endtime3 = data.getEndTime3();
            String endtime4 = data.getEndTime4();
            String endtime5 = data.getEndTime5();
            String endtime6 = data.getEndTime6();
            String examption1 = data.getExemption1();
            String examption2 = data.getExemption2();
            String examption3 = data.getExemption3();
            String examption4 = data.getExemption4();
            String examption5 = data.getExemption5();
            String examption6 = data.getExemption6();
            String fromday1 = data.getFromDay1();
            String fromday2 = data.getFromDay2();
            String fromday3 = data.getFromDay3();
            String fromday4 = data.getFromDay4();
            String fromday5 = data.getFromDay5();
            String fromday6 = data.getFromDay6();
            String startTime1 = data.getStartTime1();
            String startTime2 = data.getStartTime2();
            String startTime3 = data.getStartTime3();
            String startTime4 = data.getStartTime4();
            String startTime5 = data.getStartTime5();
            String startTime6 = data.getStartTime6();
            String toDay1 = data.getToDay1();
            String toDay2 = data.getToDay2();
            String toDay3 = data.getToDay3();
            String toDay4 = data.getToDay4();
            String toDay5 = data.getToDay5();
            String toDay6 = data.getToDay6();
            String typeDesc1 = data.getTypeDesc1();
            String typeDesc2 = data.getTypeDesc2();
            String typeDesc3 = data.getTypeDesc3();
            String typeDesc4 = data.getTypeDesc4();
            String typeDesc5 = data.getTypeDesc5();
            String typeDesc6 = data.getTypeDesc6();

//            Log.e("myTag",bayId.toString());
            ParkingBay pbInstance = new ParkingBay();
            pbInstance.setBayId(bayId);
            pbInstance.setDeviceId(deviceId);
            pbInstance.setDescription1(description1);
            pbInstance.setDescription2(description2);
            pbInstance.setDescription3(description3);
            pbInstance.setDescription4(description4);
            pbInstance.setDescription5(description5);
            pbInstance.setDescription6(description6);
            pbInstance.setDisabilityExt1(disabilityExt1);
            pbInstance.setDisabilityExt2(disabilityExt2);
            pbInstance.setDisabilityExt3(disabilityExt3);
            pbInstance.setDisabilityExt4(disabilityExt4);
            pbInstance.setDisabilityExt5(disabilityExt5);
            pbInstance.setDisabilityExt6(disabilityExt6);
            pbInstance.setDuration1(duration1);
            pbInstance.setDuration2(duration2);
            pbInstance.setDuration3(duration3);
            pbInstance.setDuration4(duration4);
            pbInstance.setDuration5(duration5);
            pbInstance.setDuration6(duration6);
            pbInstance.setEffectiveOnPH1(effectiveonph1);
            pbInstance.setEffectiveOnPH2(effectiveonph2);
            pbInstance.setEffectiveOnPH3(effectiveonph3);
            pbInstance.setEffectiveOnPH4(effectiveonph4);
            pbInstance.setEffectiveOnPH5(effectiveonph5);
            pbInstance.setEffectiveOnPH6(effectiveonph6);
            pbInstance.setEndTime1(endtime1);
            pbInstance.setEndTime2(endtime2);
            pbInstance.setEndTime3(endtime3);
            pbInstance.setEndTime4(endtime4);
            pbInstance.setEndTime5(endtime5);
            pbInstance.setEndTime6(endtime6);
            pbInstance.setExemption1(examption1);
            pbInstance.setExemption2(examption2);
            pbInstance.setExemption3(examption3);
            pbInstance.setExemption4(examption4);
            pbInstance.setExemption5(examption5);
            pbInstance.setExemption6(examption6);
            //Log.d("fromday",fromday1+"");
            pbInstance.setFromDay1(fromday1);
            pbInstance.setFromDay2(fromday2);
            pbInstance.setFromDay3(fromday3);
            pbInstance.setFromDay4(fromday4);
            pbInstance.setFromDay5(fromday5);
            pbInstance.setFromDay6(fromday6);
            pbInstance.setStartTime1(startTime1);
            pbInstance.setStartTime2(startTime2);
            pbInstance.setStartTime3(startTime3);
            pbInstance.setStartTime4(startTime4);
            pbInstance.setStartTime5(startTime5);
            pbInstance.setStartTime6(startTime6);
            pbInstance.setToDay1(toDay1);
            pbInstance.setToDay2(toDay2);
            pbInstance.setToDay3(toDay3);
            pbInstance.setToDay4(toDay4);
            pbInstance.setToDay5(toDay5);
            pbInstance.setToDay6(toDay6);
            pbInstance.setTypeDesc1(typeDesc1);
            pbInstance.setTypeDesc2(typeDesc2);
            pbInstance.setTypeDesc3(typeDesc3);
            pbInstance.setTypeDesc4(typeDesc4);
            pbInstance.setTypeDesc5(typeDesc5);
            pbInstance.setTypeDesc6(typeDesc6);
            parkingBayDAO.insert(pbInstance);



        }

    }


    //    private void loadLocationDataList(List<RetroParkingLocationRestriction> retroParkingLocationRestrictionResList) {
////Store parkingBayList to SQLite using room
//        //This part can be done in AsyncTask in the future
//        for(int i = 0; i< retroParkingLocationRestrictionResList.size() ; i++){
//            RetroParkingLocationRestriction data = retroParkingLocationRestrictionResList.get(i);
//            Integer bay_id = data.getBayId();
//            String locations = data.getLocations();
//            Integer roadsegmentid = data.getRoadSegmentId();
//            String marker_id = data.getMarkerId();
//            String meter_id = data.getMeterId();
//            String road_segment_description = data.getRoadSegmentDescription();
//            Long lastedit = data.getLastEdit();
//
//
//
//            Log.d("myTag",bay_id.toString());
//            ParkingLocation pbInstance = new ParkingLocation();
//            pbInstance.setBayId(bay_id);
//            pbInstance.setLocations(locations);
//            pbInstance.setRoadSegmentId(roadsegmentid);
//            pbInstance.setMarkerId(marker_id);
//            pbInstance.setMeterId(meter_id);
//            pbInstance.setRoadSegmentDescription(road_segment_description);
//            pbInstance.setLastEdit(lastedit);
//
//
//            parkingLocationDAO.insert(pbInstance);
//
//        }
//
//    }
    private void loadSensorDataList(List<RetroParkingBaySensor> response) {

        List<RetroParkingBaySensor> retroParkingSensorResList = response;

        //ResponseClass rest = response.blockingSingle();
        //Log.d("myTag2",retroParkingSensorResList.size()+"testing size");
        for(int i = 0; i< retroParkingSensorResList.size() ; i++){
            RetroParkingBaySensor data = retroParkingSensorResList.get(i);
            Integer bay_id = data.getBay_id();
            String st_marker_id = data.getSt_marker_id();
            String status = data.getStatus();
            String lat = data.getLat();
            String lon = data.getLon();
            Date lastRefresh = new Date();

            //Log.d("myTag2",bay_id.toString());
            ParkingSensor pbInstance = new ParkingSensor();
            pbInstance.setBay_id(bay_id);
            pbInstance.setSt_marker_id(st_marker_id);
            pbInstance.setStatus(status);
            pbInstance.setLat(lat);
            pbInstance.setLon(lon);
            pbInstance.setLastRefresh(lastRefresh);
            parkingSensorDAO.insert(pbInstance);

        }
    }
    public void loadLocationDataList() {
//Store parkingBayList to SQLite using room
        //This part can be done in AsyncTask in the future
//        for(int i = 0; i< retroParkingLocationRestrictionResList.size() ; i++){
        ArrayList<Object> entries = readLocalFile("locations.geojson");
        for (int i =0; i< entries.size(); i++) {
            ArrayList<Object> entry = (ArrayList<Object>) entries.get(i);
            Integer bay_id = (Integer) entry.get(0);
//            String locations = "(1,2)";
            String roadsegmentid = (String) entry.get(1);
            String marker_id = (String) entry.get(2);
            String meter_id = (String) entry.get(3);
            String road_segment_description = (String) entry.get(4);
            Long lastedit = (Long) entry.get(5);
            Double lon = (Double) entry.get(6);
            Double lat = (Double) entry.get(7);

//            Log.d("myTag", bay_id.toString());
            ParkingLocation pbInstance = new ParkingLocation();
            pbInstance.setBayId(bay_id);
            pbInstance.setLon(lon);
            pbInstance.setLat(lat);
            pbInstance.setRoadSegmentId(roadsegmentid);
            pbInstance.setMarkerId(marker_id);
            pbInstance.setMeterId(meter_id);
            pbInstance.setRoadSegmentDescription(road_segment_description);
            pbInstance.setLastEdit(lastedit);
            parkingLocationDAO.insert(pbInstance);
            Log.d("testingLoadJson","processing");
        }
        Log.d("testingLoadJson","done");
    }

    private ArrayList<Object> readLocalFile(String fileName){
        //JSON parser object to parse read file
        ArrayList<Object> entries = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("res/raw/locations.geojson"))
        {
            //Read JSON file
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray featureList = (JSONArray) obj.get("features");
            for(int i =0; i<featureList.size(); i++){
                ArrayList<Object> entry = parseFeatureObject((JSONObject) featureList.get(i));
                entries.add(entry);
            }
        } catch (FileNotFoundException e) {
            Log.e("FileReading","FileNotFound");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entries;
    }
    private static ArrayList<Object> parseFeatureObject(JSONObject features)
    {
        ArrayList<Object> entry = new ArrayList<>();
        JSONObject properties = (JSONObject) features.get("properties");
        JSONObject geometry = (JSONObject) features.get("geometry");
        Integer bay_id = null;
        String roadsegmentid = null;
        String marker_id = null;
        String meter_id = null;
        String road_segment_description = null;
        Long lastedit = null;
        JSONArray coordinates = null;

        //check if the entry has bay id and coordinates
        try {
            bay_id = Integer.parseInt((String) properties.get("bay_id"));
            coordinates = (JSONArray) geometry.get("coordinates");
        }catch (Exception e){
            System.out.println("Cannot load bayid or coordinates, entry abandoned.");
        }

        // only store the entry with bay id and coordinates
        if(bay_id != null && coordinates != null){
            roadsegmentid = (String) properties.get("rd_seg_id");
            marker_id = (String) properties.get("marker_id");
            meter_id = (String) properties.get("meter_id");
            road_segment_description = (String) properties.get("rd_seg_dsc");
            lastedit = Long.parseLong((String) properties.get("last_edit"));

            // Strip away the unnecessary brackets
            coordinates = (JSONArray) coordinates.get(0);
            coordinates = (JSONArray) coordinates.get(0);
            Double lon = 0d;
            Double lat = 0d;
            //get the center of the polygon as the point coordinates
            for (int j = 0; j < coordinates.size(); j++) {
                JSONArray point = (JSONArray) coordinates.get(j);
                lon += (double) point.get(0);
                lat += (double) point.get(1);

            }
            lon = lon / coordinates.size();
            lat = lat / coordinates.size();

            //store the result in arraylist
            entry.add(bay_id);
            entry.add(roadsegmentid);
            entry.add(marker_id);
            entry.add(meter_id);
            entry.add(road_segment_description);
            entry.add(lastedit);
            entry.add(lon);
            entry.add(lat);}
        return entry;
    }





}
