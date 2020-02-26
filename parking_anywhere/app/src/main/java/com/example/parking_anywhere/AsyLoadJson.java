package com.example.parking_anywhere;

import android.util.Log;

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

import sqlite.dao.ParkingLocationDAO;
import sqlite.model.ParkingLocation;

public class AsyLoadJson {
    private ParkingLocationDAO parkingLocationDAO;

    public AsyLoadJson(ParkingLocationDAO parkingLocationDAO) {
        this.parkingLocationDAO = parkingLocationDAO;
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
