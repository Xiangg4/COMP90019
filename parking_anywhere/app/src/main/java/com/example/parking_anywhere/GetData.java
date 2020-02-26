package com.example.parking_anywhere;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import sqlite.model.ResponseClass;

public interface GetData {
    //Specify the request type and pass the relative URL//
    @GET("/resource/ntht-5rk7.json")
    Observable<List<RetroParkingBayRestriction>> getAllParkingBay();
    @GET("/resource/crvt-b4kt.geojson")
    Observable<List<RetroParkingLocationRestriction>> getAllParkingLocation();
    @GET("/resource/vh2v-4nfs.json")
    Observable<List<RetroParkingBaySensor>> getAllParkingSensor();
}
//public interface GetData {
//    //Specify the request type and pass the relative URL//
//    @GET("/resource/ntht-5rk7.json")
//    //Lanting:you can add another @get below for your api
////Wrap the response in a Call object with the type of the expected result//
//    Call<List<RetroParkingBayRestriction>> getAllParkingBay();
//    @GET("/resource/crvt-b4kt.geojson")
//    Call<List<RetroParkingLocationRestriction>> getAllParkingLocation();
//}

