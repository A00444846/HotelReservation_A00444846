package com.example.hotelreservation_a00444846;

public class HotelListData {

    String hotel_name;
    int price;
    Boolean availability;

    public HotelListData(String hotel_name, int price, Boolean availability) {
        this.hotel_name = hotel_name;
        this.price = price;
        this.availability = availability;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

}
