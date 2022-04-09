package com.example.hotelreservation_a00444846;

import android.telecom.Call;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;


public interface ApiInterface {

    // API's endpoints
    @GET("/getListOfHotels")
    public void getHotelsLists(Callback<HotelList> callback);


    @POST("/reservationConfirmation")
    void reservationConfirmation(@Body ReservationData reservationData, Callback<ReservationResponse> callback);

}
