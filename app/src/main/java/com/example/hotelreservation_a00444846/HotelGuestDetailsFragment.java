package com.example.hotelreservation_a00444846;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HotelGuestDetailsFragment extends Fragment {

    View view;
    Button submitButton;
    ProgressBar progressBar;
    TextView hotelValueTextView, checkInValueTextView, checkOutValueTextView, priceValueTextView;

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

        progressBar = view.findViewById(R.id.progress_bar);
        hotelValueTextView = view.findViewById(R.id.hotel_name_value_text_view);
        checkInValueTextView = view.findViewById(R.id.check_in_value_text_view);
        checkOutValueTextView = view.findViewById(R.id.check_out_value_text_view);
        priceValueTextView = view.findViewById(R.id.price_value_text_view);

        hotelValueTextView.setText(hotelName);
        checkInValueTextView.setText(checkInDate);
        checkOutValueTextView.setText(checkOutDate);
        priceValueTextView.setText(String.valueOf(hotelPrice));

        setupRecyclerView(numberOfGuests);
        RecyclerView recyclerView = view.findViewById(R.id.guest_list_recyclerView);

        submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                List<GuestListData> guestList = new ArrayList<>();
                for (int i = 0; i < numberOfGuests; i++) {
                    View guestDetailsView = recyclerView.getChildAt(i);

                    String guestName = ((EditText) guestDetailsView.findViewById(R.id.guest_name_edit_text)).getText().toString();
                    if (guestName == null || guestName.trim().length() == 0) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Please insert name of Guest " + (i + 1), Toast.LENGTH_LONG).show();
                        return;
                    }

                    String gender = null;
                    try {
                        gender = ((RadioButton) guestDetailsView.findViewById(
                                ((RadioGroup) guestDetailsView.findViewById(R.id.gender_radio_group)).getCheckedRadioButtonId()
                        )).getText().toString();
                    } catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Please select any gender of Guest " + (i + 1), Toast.LENGTH_LONG).show();
                    }


                    if (gender == null || gender.trim().length() == 0) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Please select any gender of Guest " + (i + 1), Toast.LENGTH_LONG).show();
                        return;
                    }

                    GuestListData guestListData = new GuestListData(guestName, gender.toLowerCase(Locale.ROOT));
                    guestList.add(guestListData);
                }
                ReservationData reservationData = new ReservationData(hotelName, checkInDate, checkOutDate, guestList);

                Api.getClient().reservationConfirmation(reservationData, new Callback<ReservationResponse>() {
                    @Override
                    public void success(ReservationResponse reservationResponse, Response response) {
                        progressBar.setVisibility(View.GONE);
                        Bundle bundle = new Bundle();
                        bundle.putString("reservation_id", reservationResponse.getReservationId());

                        ReservationConfirmationFragment reservationConfirmationFragment = new ReservationConfirmationFragment();
                        reservationConfirmationFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_layout, reservationConfirmationFragment);
                        fragmentTransaction.remove(HotelGuestDetailsFragment.this);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void setupRecyclerView(int numberOfGuests) {
        RecyclerView recyclerView = view.findViewById(R.id.guest_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuestDetailsListAdapter guestDetailsListAdapter = new GuestDetailsListAdapter(getActivity(), numberOfGuests);
        recyclerView.setAdapter(guestDetailsListAdapter);
    }
}
