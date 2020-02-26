package view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import repositories.ParkingRepository;
import sqlite.dao.ParkingSensorWithRestrictionDAO;
import sqlite.model.ParkingBay;
import sqlite.model.ParkingLocation;
import sqlite.model.ParkingLocationWithRestriction;
import sqlite.model.ParkingSensor;
import sqlite.model.ParkingSensorWithRestriction;

public class ParkingBayViewModel extends ViewModel {
    private LiveData<List<ParkingBay>> parkingBayRestriction;
    private LiveData<List<ParkingSensor>> parkingSensor;
    private List<ParkingLocation> parkingLocation;

    private ParkingRepository parkingRepo;

    private Double currLat;
    private Double currLon;



    private MutableLiveData<List<String>> liveCombinedDescriptions = new MutableLiveData<>();
    private MutableLiveData<Map<String,List<String>>> liveSeparateSescriptions = new MutableLiveData<>();
    private LiveData<ParkingBay> parkingBay;

    private MutableLiveData<List<ParkingSensorWithRestrictionDAO.filteredBays>> filteredByDuration = new MutableLiveData();
    private MutableLiveData<ParkingSensorWithRestrictionDAO.filteredBays> bestBay = new MutableLiveData();



    @Inject
    public ParkingBayViewModel(ParkingRepository parkingRepo){
        this.parkingRepo = parkingRepo;
    }

    public void init(int bayId) {
        if (this.parkingLocation == null) {

            parkingLocation = parkingRepo.getParkingLocation(bayId);

        }
        if (this.parkingBayRestriction == null) {
            parkingBayRestriction = parkingRepo.getParkingBayRestriction(bayId);
        }
        if(this.parkingSensor == null){
            //Log.d("gettingTAG", "testing database");
            parkingSensor = parkingRepo.getParkingBaySensor(bayId);

            //Log.d("gettingTAG2", parkingSensor.toString());
        }
        if (this.parkingBay == null) {
            parkingBay = parkingRepo.getBay(bayId);
        }
        //if(this.parkingSensorDAOHours == null)
        //    parkingSensorDAOHours = parkingRepo.gethours(60);//hard code for testing

        return;

    }

    public LiveData<List<ParkingBay>> getParkingBayRestriction(){
        return this.parkingBayRestriction;
    }
    public LiveData<List<ParkingSensor>> getParkingSensor(){

        return this.parkingSensor;
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////Liuyi's code////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //input a duration in form of minutes, return a list of bays with iD, lon and lat
    public LiveData<List<ParkingSensorWithRestrictionDAO.filteredBays>> filterBaysByDuration(int selectedDuration) {

        return Transformations.switchMap(parkingRepo.getFilteredBays(selectedDuration), result ->
                filterBaysByDurationHelper(result));
    }

    public LiveData<List<ParkingSensorWithRestrictionDAO.filteredBays>> filterBaysByDurationHelper(List<ParkingSensorWithRestrictionDAO.filteredBays> tempDuration){


        tempDuration = getValidFilteredBays(tempDuration);

        MutableLiveData<List<ParkingSensorWithRestrictionDAO.filteredBays>> filteredBays = new MutableLiveData();
        for(ParkingSensorWithRestrictionDAO.filteredBays values : tempDuration) {
            Log.d("tempduration", String.valueOf(values.iD));
        }
        filteredByDuration.setValue(tempDuration);
//        return filteredByDuration;
        filteredBays.setValue(tempDuration);
        return filteredBays;
    }

    private List<ParkingSensorWithRestrictionDAO.filteredBays> getValidFilteredBays(List<ParkingSensorWithRestrictionDAO.filteredBays> tempDuration){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(now);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek == 0){
            dayOfWeek = 7;
        }
        SimpleDateFormat tempTime = new SimpleDateFormat("HH:mm:ss");
        String time = tempTime.format(now);

        int len = tempDuration.size();
        for(int i=0; i<len; i++){

            //选出符合的星期和时间的车位
            if(checkNull(tempDuration.get(i),1)) {
                if(checkTimeRange(tempDuration.get(i),1,dayOfWeek,time)){
                    continue;
                }
            }else{
                tempDuration.remove(i);
                len = len - 1;
                continue;
            }

            if(checkNull(tempDuration.get(i),2)) {
                if(checkTimeRange(tempDuration.get(i),2,dayOfWeek,time)){
                    continue;
                }
            }else{
                tempDuration.remove(i);
                len = len - 1;
                continue;
            }

            if(checkNull(tempDuration.get(i),3)) {
                if(checkTimeRange(tempDuration.get(i),3,dayOfWeek,time)){
                    continue;
                }
            }else{
                tempDuration.remove(i);
                len = len - 1;
                continue;
            }

            if(checkNull(tempDuration.get(i),4)) {
                if(checkTimeRange(tempDuration.get(i),4,dayOfWeek,time)){
                    continue;
                }
            }else{
                tempDuration.remove(i);
                len = len - 1;
                continue;
            }

            if(checkNull(tempDuration.get(i),5)) {
                if(checkTimeRange(tempDuration.get(i),5,dayOfWeek,time)){
                    continue;
                }
            }else{
                tempDuration.remove(i);
                len = len - 1;
                continue;
            }

            if(checkNull(tempDuration.get(i),6)) {
                if(checkTimeRange(tempDuration.get(i),6,dayOfWeek,time)){
                    continue;
                }
            }else{
                tempDuration.remove(i);
                len = len - 1;
                continue;
            }
//            setCombinedDescriptions(tempDuration.get(i));
        }
        for (int j = 0; j< tempDuration.size(); j++ ) {
            tempDuration.get(j).setCombinedDescriptions(getDescriptionsCombined(tempDuration.get(j)));
            tempDuration.get(j).setSplitDescriptions(getDescriptionsSplit(tempDuration.get(j)));
            tempDuration.get(j).setIsDisabled(isDisabled(tempDuration.get(j)));
            tempDuration.get(j).setOccupation(getOccupation(tempDuration.get(j).status));
            Log.d("viewdescriptions", String.valueOf(tempDuration.get(j).combinedDescriptions));
//            Log.d("viewdescriptions", String.valueOf(tempDuration.get(j).splitDescriptions));
            Log.d("viewdescriptions", String.valueOf(tempDuration.get(j).isDisabled));
            Log.d("viewdescriptions", String.valueOf(tempDuration.get(j).occupation));
        }
        return tempDuration;
    }

    //check null
    private boolean checkNull(ParkingSensorWithRestrictionDAO.filteredBays tempDuration, int index){
        Boolean notNull = false;
        if(index == 1) {
            notNull =
                    tempDuration.fromDay1 != null &
                            tempDuration.toDay1 != null &
                            tempDuration.startTime1 != null &
                            tempDuration.endTime1 != null;
        }
        if(index == 2) {
            notNull =
                    tempDuration.fromDay2 != null &
                            tempDuration.toDay2 != null &
                            tempDuration.startTime2 != null &
                            tempDuration.endTime2 != null;
        }
        if(index == 3) {
            notNull =
                    tempDuration.fromDay3 != null &
                            tempDuration.toDay3 != null &
                            tempDuration.startTime3 != null &
                            tempDuration.endTime3 != null;
        }
        if(index == 4) {
            notNull =
                    tempDuration.fromDay4 != null &
                            tempDuration.toDay4 != null &
                            tempDuration.startTime4 != null &
                            tempDuration.endTime4 != null;
        }
        if(index == 5) {
            notNull =
                    tempDuration.fromDay5 != null &
                            tempDuration.toDay5 != null &
                            tempDuration.startTime5 != null &
                            tempDuration.endTime5 != null;
        }
        if(index == 6) {
            notNull =
                    tempDuration.fromDay6 != null &
                            tempDuration.toDay6 != null &
                            tempDuration.startTime6 != null &
                            tempDuration.endTime6 != null;
        }
        return notNull;
    }


    //check if in the time range
    private boolean checkTimeRange(ParkingSensorWithRestrictionDAO.filteredBays tempDuration, int index,int dayOfWeek,String time){
        Boolean inTimeRange = false;
        if(index == 1) {
            inTimeRange =
                    ((dayOfWeek <= Integer.parseInt(tempDuration.fromDay1)) &
                            (dayOfWeek >= Integer.parseInt(tempDuration.fromDay1))) &
                            (((compareTime(time, tempDuration.startTime1)) >= 0) &
                                    ((compareTime(time, tempDuration.endTime1)) <= 0));
        }
        if(index == 2) {
            inTimeRange =
                    ((dayOfWeek <= Integer.parseInt(tempDuration.fromDay2)) &
                            (dayOfWeek >= Integer.parseInt(tempDuration.fromDay2))) &
                            (((compareTime(time, tempDuration.startTime2)) >= 0) &
                                    ((compareTime(time, tempDuration.endTime2)) <= 0));

        }
        if(index == 3) {
            inTimeRange =
                    ((dayOfWeek <= Integer.parseInt(tempDuration.fromDay3)) &
                            (dayOfWeek >= Integer.parseInt(tempDuration.fromDay3))) &
                            (((compareTime(time, tempDuration.startTime3)) >= 0) &
                                    ((compareTime(time, tempDuration.endTime3)) <= 0));

        }
        if(index == 4) {
            inTimeRange =
                    ((dayOfWeek <= Integer.parseInt(tempDuration.fromDay4)) &
                            (dayOfWeek >= Integer.parseInt(tempDuration.fromDay4))) &
                            (((compareTime(time, tempDuration.startTime4)) >= 0) &
                                    ((compareTime(time, tempDuration.endTime4)) <= 0));

        }
        if(index == 5) {
            inTimeRange =
                    ((dayOfWeek <= Integer.parseInt(tempDuration.fromDay5)) &
                            (dayOfWeek >= Integer.parseInt(tempDuration.fromDay5))) &
                            (((compareTime(time, tempDuration.startTime5)) >= 0) &
                                    ((compareTime(time, tempDuration.endTime5)) <= 0));

        }
        if(index == 6) {
            inTimeRange =
                    ((dayOfWeek <= Integer.parseInt(tempDuration.fromDay6)) &
                            (dayOfWeek >= Integer.parseInt(tempDuration.fromDay6))) &
                            (((compareTime(time, tempDuration.startTime6)) >= 0) &
                                    ((compareTime(time, tempDuration.endTime6)) <= 0));

        }
        return inTimeRange;
    }


    //This function is for comparing two Strings that are in form of (HH:mm:ss)
    private int compareTime(String s1,String s2) {
        int beforeOrAfter = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date d1 = sdf.parse(s1);
            Date d2 = sdf.parse(s2);
            beforeOrAfter = d1.compareTo(d2);


        }catch (ParseException e){
            e.printStackTrace();
        }
        //before: <0 ; after: >0 ; equal: =0
        return beforeOrAfter;
    }


//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////Lanting's code//////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//


    public ParkingSensorWithRestrictionDAO.filteredBays getRecommendation(int duration, Double lat, Double lon) {
        this.currLat = lat;
        this.currLon = lon;
        Log.d("curr", String.valueOf(currLat));
        Log.d("curr", String.valueOf(currLon));
        return getRecommendationHelper(parkingRepo.getNearbyBays(duration, lat, lon));
    }

    public ParkingSensorWithRestrictionDAO.filteredBays getRecommendationHelper(List<ParkingSensorWithRestrictionDAO.filteredBays> bays){
        //filter valid time
        List<ParkingSensorWithRestrictionDAO.filteredBays> filteredBays = getValidFilteredBays(bays);
        Log.d("nearbybays", String.valueOf(filteredBays.size()));
        return getBestBay(filteredBays);
    }


    private ParkingSensorWithRestrictionDAO.filteredBays getBestBay(List<ParkingSensorWithRestrictionDAO.filteredBays> bays){
        double bestValue = Double.POSITIVE_INFINITY;
        ParkingSensorWithRestrictionDAO.filteredBays bestBay = new ParkingSensorWithRestrictionDAO.filteredBays();
        int totalBays = bays.size();
        double average_freq = 0.0;
        for(int i=0; i<bays.size(); i++) {
            if(bays.get(i).occupationFreq != null){
                average_freq += bays.get(i).occupationFreq;
            }
        }
        average_freq = average_freq/totalBays;
        for(int i=0; i<bays.size(); i++){
            double combinedValue = 0.0;
            double occValue =0.0;
            ParkingSensorWithRestrictionDAO.filteredBays bay = bays.get(i);
            if(bay.lat!=0.0){
                double dist = getDistanceByCoord(bay.lat, bay.lon, currLat,currLon);
                Log.d("dist", String.valueOf(bay.iD));
                Log.d("dist", String.valueOf(dist));
                if(bay.occupationFreq != null){
                    occValue = bay.occupationFreq/totalBays;
                }
                else{
                    occValue = average_freq/totalBays;
                }
                combinedValue = occValue + dist;
                if( combinedValue>=0 && combinedValue < bestValue){
                    bestValue = dist;
                    bestBay = bay;
                }
            }
        }
        return bestBay;
    }


    private Double getDistanceByCoord(double lat1, double lon1, double lat2, double lon2){
        final int R = 6371; // Radius of the earth
        if(lat1<=90 && lat1>=-90 && lat2<=90 && lat2>=-90 && lon1<=180 && lon1>=-180 && lon2<=180 && lon2>=-180) {
            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c;
            distance = Math.pow(distance, 2);
            return Math.sqrt(distance);
        }
        else{
            return -1.0; //invalid coordinate
        }
    }

    public LiveData<List<ParkingLocationWithRestriction>> getNormalBayWithoutSensor(){
        return parkingRepo.getNormalBayWithoutSensor();
    }

    public LiveData<List<ParkingLocationWithRestriction>> getDisabledBayWithoutSensor(){
        return parkingRepo.getDisabledBayWithoutSensor();
    }

    public LiveData<List<ParkingSensorWithRestriction>> getNormalBayWithSensor(){
        return parkingRepo.getNormalBayWithSensor();
    }

    public LiveData<List<ParkingSensorWithRestriction>> getDisabledBayWithSensor(){
        return parkingRepo.getDisabledBayWithSensor();
    }


    private boolean isDisabled(ParkingSensorWithRestrictionDAO.filteredBays bay){
        if((bay.typeDesc1 != null && bay.typeDesc1.contains("Disabled")) ||
                (bay.typeDesc2 != null && bay.typeDesc2.contains("Disabled"))||
                (bay.typeDesc3 != null && bay.typeDesc3.contains("Disabled"))||
                (bay.typeDesc4 != null && bay.typeDesc4.contains("Disabled"))||
                (bay.typeDesc5 != null && bay.typeDesc5.contains("Disabled"))||
                (bay.typeDesc6 != null && bay.typeDesc6.contains("Disabled"))) {
            return true;
        }
        else{
            return false;
        }
    }

    private String getOccupation(String isOccupied){
        String occupationInfo = "Occupied";
        if(isOccupied == null){
            occupationInfo = "Unknown Occupation Status";
        }
        else if(isOccupied.contains("Unoccupied")){
            occupationInfo = "Available";
        }
        return occupationInfo;
    }

    private String getDescriptionsSplit(ParkingSensorWithRestrictionDAO.filteredBays bay){
        //get the descriptions that do not include"tow away" or "no stopping"
        List<Integer> validIndices = getValidDescriptionIndices(bay);
        Log.d("description", String.valueOf(validIndices));
        List<String> descriptionList = new ArrayList<>();
        //get the valid descriptions as a list
        if(validIndices.contains(1)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay1,bay.toDay1);
            String times = getStartAndEndTime(bay.startTime1,bay.endTime1);
            String typeDescription = bay.typeDesc1;

            // add a "*" to indicate it is not applicable to public holidays
            if(bay.effectiveOnPH1 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + "\n" + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(2)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay2,bay.toDay2);
            String times = getStartAndEndTime(bay.startTime2,bay.endTime2);
            String typeDescription = bay.typeDesc2;
            if(bay.effectiveOnPH2 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + "\n" + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(3)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay3,bay.toDay3);
            String times = getStartAndEndTime(bay.startTime3,bay.endTime3);
            String typeDescription = bay.typeDesc3;
            if(bay.effectiveOnPH3 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + "\n" + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(4)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay4,bay.toDay4);
            String times = getStartAndEndTime(bay.startTime4,bay.endTime4);
            String typeDescription = bay.typeDesc4;
            if(bay.effectiveOnPH4 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + "\n" + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(5)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay5,bay.toDay5);
            String times = getStartAndEndTime(bay.startTime5,bay.endTime5);
            String typeDescription = bay.typeDesc5;
            if(bay.effectiveOnPH5 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + "\n" + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(6)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay6,bay.toDay6);
            String times = getStartAndEndTime(bay.startTime6,bay.endTime6);
            String typeDescription = bay.typeDesc6;
            if(bay.effectiveOnPH6 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + "\n" + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        String descriptions = "";
        for(int i =0; i<descriptionList.size(); i++){
            descriptions.concat(descriptionList.get(i));
            if(i<descriptionList.size()-1){
                descriptions.concat(",");
            }
        }
        return descriptions;
    }

    /** Returning the descriptions as a whole **/
    private String getDescriptionsCombined(ParkingSensorWithRestrictionDAO.filteredBays bay){
        //get the descriptions that do not include"tow away" or "no stopping"
        List<Integer> validIndices = getValidDescriptionIndices(bay);
        Log.d("description", String.valueOf(validIndices));
        List<String> descriptionList = new ArrayList<>();
        //get the valid descriptions as a list
        if(validIndices.contains(1)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay1,bay.toDay1);
            String times = getStartAndEndTime(bay.startTime1,bay.endTime1);
            String typeDescription = bay.typeDesc1;

            // add a "*" to indicate it is not applicable to public holidays
            if(bay.effectiveOnPH1 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + " " + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(2)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay2,bay.toDay2);
            String times = getStartAndEndTime(bay.startTime2,bay.endTime2);
            String typeDescription = bay.typeDesc2;
            if(bay.effectiveOnPH2 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + " " + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(3)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay3,bay.toDay3);
            String times = getStartAndEndTime(bay.startTime3,bay.endTime3);
            String typeDescription = bay.typeDesc3;
            if(bay.effectiveOnPH3 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + " " + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(4)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay4,bay.toDay4);
            String times = getStartAndEndTime(bay.startTime4,bay.endTime4);
            String typeDescription = bay.typeDesc4;
            if(bay.effectiveOnPH4 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + " " + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(5)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay5,bay.toDay5);
            String times = getStartAndEndTime(bay.startTime5,bay.endTime5);
            String typeDescription = bay.typeDesc5;
            if(bay.effectiveOnPH5 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + " " + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        if(validIndices.contains(6)){
            String effectiveOnPublicHolidays = "";
            String days = getFromANDToDay(bay.fromDay6,bay.toDay6);
            String times = getStartAndEndTime(bay.startTime6,bay.endTime6);
            String typeDescription = bay.typeDesc6;
            if(bay.effectiveOnPH6 == 1){
                effectiveOnPublicHolidays = "*";
            }
            descriptionList.add(days + " " + times + "\n" + typeDescription+ effectiveOnPublicHolidays + "\n");
        }
        String descriptions = "";
        for(int i =0; i<descriptionList.size(); i++){
            descriptions += descriptionList.get(i);
            if(i<descriptionList.size()-1){
                descriptions += ",";
            }
        }
        return descriptions;
    }



    private List<String> getDaysDescriptions(ParkingSensorWithRestrictionDAO.filteredBays bay, List<Integer> validIndices){
        //get the descriptions that do not include"tow away" or "no stopping"
        List<String> daysList = new ArrayList<>();
        //get the valid descriptions as a list
        if(validIndices.contains(1)){
            String days = getFromANDToDay(bay.fromDay1,bay.toDay1);
            daysList.add(days);
        }
        if(validIndices.contains(2)){
            String days = getFromANDToDay(bay.fromDay2,bay.toDay2);
            daysList.add(days);
        }
        if(validIndices.contains(3)){
            String days = getFromANDToDay(bay.fromDay3,bay.toDay3);
            daysList.add(days);
        }
        if(validIndices.contains(4)){
            String days = getFromANDToDay(bay.fromDay4,bay.toDay4);
            daysList.add(days);
        }
        if(validIndices.contains(5)){
            String days = getFromANDToDay(bay.fromDay5,bay.toDay5);
            daysList.add(days);
        }
        if(validIndices.contains(6)){
            String days = getFromANDToDay(bay.fromDay6,bay.toDay6);
            daysList.add(days);
        }
        return daysList;
    }


    private List<String> getTimesDescriptions(ParkingSensorWithRestrictionDAO.filteredBays bay, List<Integer> validIndices){
        //get the descriptions that do not include"tow away" or "no stopping"
        List<String> timesList = new ArrayList<>();
        //get the valid descriptions as a list
        if(validIndices.contains(1)){
            String timesDesc = getStartAndEndTime(bay.startTime1,bay.endTime1);
            timesList.add(timesDesc);
        }
        if(validIndices.contains(2)){
            String timesDesc = getStartAndEndTime(bay.startTime2,bay.endTime2);
            timesList.add(timesDesc);
        }
        if(validIndices.contains(3)){
            String timesDesc = getStartAndEndTime(bay.startTime3,bay.endTime3);
            timesList.add(timesDesc);
        }
        if(validIndices.contains(4)){
            String timesDesc = getStartAndEndTime(bay.startTime4,bay.endTime4);
            timesList.add(timesDesc);
        }
        if(validIndices.contains(5)){
            String timesDesc = getStartAndEndTime(bay.startTime5,bay.endTime5);
            timesList.add(timesDesc);
        }
        if(validIndices.contains(6)){
            String timesDesc = getStartAndEndTime(bay.startTime6,bay.endTime6);
            timesList.add(timesDesc);
        }
        return timesList;
    }

    private List<String> getTypeDescriptions(ParkingSensorWithRestrictionDAO.filteredBays bay, List<Integer> validIndices){
        //get the descriptions that do not include"tow away" or "no stopping"
        List<String> typeDescList = new ArrayList<>();
        //get the valid descriptions as a list
        if(validIndices.contains(1)){
            String types = bay.typeDesc1;
            typeDescList.add(types);
        }
        if(validIndices.contains(2)){
            String types = bay.typeDesc2;
            typeDescList.add(types);
        }
        if(validIndices.contains(3)){
            String types = bay.typeDesc3;
            typeDescList.add(types);
        }
        if(validIndices.contains(4)){
            String types = bay.typeDesc4;
            typeDescList.add(types);
        }
        if(validIndices.contains(5)){
            String types = bay.typeDesc5;
            typeDescList.add(types);
        }
        if(validIndices.contains(6)){
            String types = bay.typeDesc6;
            typeDescList.add(types);
        }

        return typeDescList;
    }


    private List<String> getHolidayDescriptions(ParkingSensorWithRestrictionDAO.filteredBays bay, List<Integer> validIndices) {
        //get the descriptions that do not include"tow away" or "no stopping"
        List<String> holidayList = new ArrayList<>();
        //get the valid descriptions as a list
        if (validIndices.contains(1)) {
            if(bay.effectiveOnPH1==1){
                holidayList.add("*");
            }
            else{
                holidayList.add("");
            }
        }
        if (validIndices.contains(2)) {
            if(bay.effectiveOnPH2==1){
                holidayList.add("*");
            }
            else{
                holidayList.add("");
            }
        }
        if (validIndices.contains(3)) {
            if(bay.effectiveOnPH3==1){
                holidayList.add("*");
            }
            else{
                holidayList.add("");
            }
        }
        if (validIndices.contains(4)) {
            if(bay.effectiveOnPH4==1){
                holidayList.add("*");
            }
            else{
                holidayList.add("");
            }
        }
        if (validIndices.contains(5)) {
            if(bay.effectiveOnPH5==1){
                holidayList.add("*");
            }
            else{
                holidayList.add("");
            }
        }
        if (validIndices.contains(6)) {
            if(bay.effectiveOnPH6==1){
                holidayList.add("*");
            }
            else{
                holidayList.add("");
            }
        }
        return holidayList;
    }


    /** Return the from and to day in a more readable format **/
    public String getFromANDToDay(String fromDayNum, String toDayNum){
        if (fromDayNum!= null && toDayNum != null){
            String fromDayName = getDayName(fromDayNum);
            String toDayName = getDayName(toDayNum);
            if (fromDayName != null && toDayName != null){
                if (!fromDayNum.equals(toDayNum)){
                    return fromDayName + "-" + toDayName;
                }
                else{ //same from and to day
                    return fromDayName;
                }
            }
        }
        return "No Day Info.";
    }


    private String getDayName(String dayNum){
        if (dayNum != null){
            if(dayNum.equals("1")) {
                return "Mon";
            }
            else if(dayNum.equals("2")){
                return  "Tue";
            }
            else if(dayNum.equals("3")){
                return  "Wed";
            }
            else if(dayNum.equals("4")){
                return  "Thur";
            }
            else if(dayNum.equals("5")){
                return  "Fri";
            }
            else if(dayNum.equals("6")){
                return  "Sat";
            }
            else if(dayNum.equals("0")){
                return  "Sun";
            }
        }
        return null;
    }

    /** Return the start and end time in a more readable format **/
    public String getStartAndEndTime(String startTime, String endTime){
        if (startTime != null && endTime != null) {
            // only keep the hour and the minute
            String startFormatted = startTime.substring(0, 5);
            String endFormatted = endTime.substring(0, 5);
            return startFormatted + "-" + endFormatted;
        }
        return "No Time Interval Info.";
    }



    /**Exclude descriptions with Clearway (Tow away) or S/ (No Stopping)**/
    private List<Integer> getValidDescriptionIndices(ParkingSensorWithRestrictionDAO.filteredBays bay){
        List<Integer> validIndices = new ArrayList<>();
        if (bay.typeDesc1 != null && !bay.typeDesc1.contains("Clearway") && !bay.typeDesc1.contains("Stopping")){
            validIndices.add(1);
        }
        if (bay.typeDesc2 != null && !bay.typeDesc2.contains("Clearway") && !bay.typeDesc2.contains("Stopping")){
            validIndices.add(2);
        }
        if (bay.typeDesc3 != null && !bay.typeDesc3.contains("Clearway") && !bay.typeDesc3.contains("Stopping")){
            validIndices.add(3);
        }
        if (bay.typeDesc4 != null && !bay.typeDesc4.contains("Clearway") && !bay.typeDesc4.contains("Stopping")){
            validIndices.add(4);
        }
        if (bay.typeDesc5 != null && !bay.typeDesc5.contains("Clearway") && !bay.typeDesc5.contains("Stopping")){
            validIndices.add(5);
        }
        if (bay.typeDesc6 != null && !bay.typeDesc6.contains("Clearway") && !bay.typeDesc6.contains("Stopping")){
            validIndices.add(6);
        }
        return validIndices;
    }



}
