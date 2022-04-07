package com.example.hotelreservation_a00444846;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HotelsListFragment extends Fragment implements ItemClickListener {

    View view;
    TextView headingTextView;
    ProgressBar progressBar;
    List<HotelListData> hotelListResponseData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headingTextView = view.findViewById(R.id.heading_text_view);
        progressBar = view.findViewById(R.id.progress_bar);

        String checkInDate = getArguments().getString("check_in_date");
        String checkOutDate = getArguments().getString("check_out_date");
        int numberOfGuests = getArguments().getInt("no_of_guest");

        //Set up the header
        headingTextView.setText("Welcome User, displaying hotel for " + numberOfGuests + " guests staying from " + checkInDate +
                " to " + checkOutDate);

        getHotelsListsData();
    }

    private void getHotelsListsData() {
        progressBar.setVisibility(View.VISIBLE);
        Api.getClient().getHotelsLists(new Callback<HotelList>() {
            @Override
            public void success(HotelList hotelList, Response response){
                hotelListResponseData = hotelList.getHotelListDataList();
                setupRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
//        Api.getClient().getHotelsLists(new Callback<List<HotelListData>>() {
//            @Override
//            public void success(List<HotelListData> hotelListResponseData, Response response) {
//                hotelListResponseData = hotelListResponseData;
//                setupRecyclerView();
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
//
//            }
//        });
    }

    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), hotelListResponseData);
        recyclerView.setAdapter(hotelListAdapter);

        //Bind the click listener
        hotelListAdapter.setClickListener(this);
    }

//    public ArrayList<HotelListData> initHotelListData() {
//        ArrayList<HotelListData> list = new ArrayList<>();
//
//        list.add(new HotelListData("Halifax Regional Hotel", 2000, true));
//        list.add(new HotelListData("Hotel Pearl", 500, false));
//        list.add(new HotelListData("Hotel Amano", 800, true));
//        list.add(new HotelListData("San Jones", 250, false));
//        list.add(new HotelListData("Halifax Regional Hotel", 2000, true));
//        list.add(new HotelListData("Hotel Pearl", 500, false));
//        list.add(new HotelListData("Hotel Amano", 800, true));
//        list.add(new HotelListData("San Jones", 250, false));
//
//        return list;
//    }

    @Override
    public void onClick(View view, int position) {
        HotelListData hotelListData = hotelListResponseData.get(position);

        String hotelName = hotelListData.getHotel_name();
        int price = hotelListData.getPrice();

        Bundle bundle = new Bundle();
        bundle.putString("hotel_name", hotelName);
        bundle.putInt("hotel_price", price);
        bundle.putString("check_in_date", getArguments().getString("check_in_date"));
        bundle.putString("check_out_date", getArguments().getString("check_out_date"));
        bundle.putInt("no_of_guest", getArguments().getInt("no_of_guest"));

        HotelGuestDetailsFragment hotelGuestDetailsFormFragment = new HotelGuestDetailsFragment();
        hotelGuestDetailsFormFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, hotelGuestDetailsFormFragment);
        fragmentTransaction.remove(HotelsListFragment.this);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
