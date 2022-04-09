package com.example.hotelreservation_a00444846;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationData {

    @SerializedName("hotel_name")
    String hotelName;
    @SerializedName("checkin")
    String checkInDate;
    @SerializedName("checkout")
    String checkOutDate;
    @SerializedName("guests_list")
    List<GuestListData> guestListData;

    public ReservationData(String hotelName, String checkInDate, String checkOutDate, List<GuestListData> guestListData) {
        this.hotelName = hotelName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestListData = guestListData;
    }
}
