package com.example.hotelreservation_a00444846;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelList {

    @SerializedName("hotels_list")
    private List<HotelListData> hotelListDataList;

    public List<HotelListData> getHotelListDataList() {
        return hotelListDataList;
    }
}
