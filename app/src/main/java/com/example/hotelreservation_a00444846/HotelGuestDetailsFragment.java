package com.example.hotelreservation_a00444846;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HotelGuestDetailsFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.guest_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String hotelName = getArguments().getString("hotel_name");
        int hotelPrice = getArguments().getInt("hotel_price");
        String checkInDate = getArguments().getString("check_in_date");
        String checkOutDate = getArguments().getString("check_out_date");
        int numberOfGuests = getArguments().getInt("no_of_guest");

        TextView hotelRecapTextView = view.findViewById(R.id.heading_text_view);
        hotelRecapTextView.setText(hotelName + "::" + hotelPrice + "::" + checkInDate + "::" + checkOutDate + "::" + numberOfGuests);

        setupRecyclerView(numberOfGuests);
    }

    private void setupRecyclerView(int numberOfGuests) {
        RecyclerView recyclerView = view.findViewById(R.id.guest_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuestDetailsListAdapter guestDetailsListAdapter = new GuestDetailsListAdapter(getActivity(), numberOfGuests);
        recyclerView.setAdapter(guestDetailsListAdapter);
    }
}
