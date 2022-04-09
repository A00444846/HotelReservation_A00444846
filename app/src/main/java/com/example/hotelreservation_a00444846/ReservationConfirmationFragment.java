package com.example.hotelreservation_a00444846;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ReservationConfirmationFragment extends Fragment {

    View view;
    TextView reservationIdTextView;
    Button anotherBookButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reservation_confirmation_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reservationIdTextView = view.findViewById(R.id.reservation_id_text_view);
        anotherBookButton = view.findViewById(R.id.book_another_button);

        String reservationId = getArguments().getString("reservation_id");
        reservationIdTextView.setText("Please note your Reservation id: "+ reservationId);

        anotherBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotelSearchFragment hotelSearchFragment = new HotelSearchFragment();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_layout, hotelSearchFragment);
                fragmentTransaction.remove(ReservationConfirmationFragment.this);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
