package com.example.hotelreservation_a00444846;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiInterface {

    // API's endpoints
    @GET("/getListOfHotels")
    public void getHotelsLists(Callback<HotelList> callback);

    @POST("/")
    public void fsdjkfdf();
    
}
