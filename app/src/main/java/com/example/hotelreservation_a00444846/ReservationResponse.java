package com.example.hotelreservation_a00444846;

import com.google.gson.annotations.SerializedName;

public class ReservationResponse {

    @SerializedName("confirmation_number")
    private String reservationId;

    public ReservationResponse(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}
