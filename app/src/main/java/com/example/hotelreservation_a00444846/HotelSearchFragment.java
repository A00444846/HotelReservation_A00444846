package com.example.hotelreservation_a00444846;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HotelSearchFragment extends Fragment {

    View view;
    TextView titleTextView;
    DatePicker checkInDatePicker, checkOutDatePicker;
    EditText noOfGuestEditText;
    Button searchButton, saveButton, restoreButton;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String checkInDate = "checkInDate";
    public static final String checkOutDate = "checkOutDate";
    public static final String guestsCount = "guestsCount";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_search_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        titleTextView = view.findViewById(R.id.title_text_view);
        titleTextView.setText(R.string.welcome_text);

        checkInDatePicker = view.findViewById(R.id.check_in_date_picker);
        checkOutDatePicker = view.findViewById(R.id.check_out_date_picker);
        noOfGuestEditText = view.findViewById(R.id.no_of_guest_edit_text);


        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkInDate = getDateFromDatePicker(checkInDatePicker);
                String checkOutDate = getDateFromDatePicker(checkOutDatePicker);
                int noOfGuest;

                try {
                    if(dateFormat.parse(checkInDate).compareTo(dateFormat.parse(checkOutDate)) > 0){
                        Toast.makeText(getActivity(), "Check-Out Date can not be before Check-In Date", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (ParseException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    noOfGuest = Integer.parseInt(noOfGuestEditText.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please enter valid Guest Number", Toast.LENGTH_LONG).show();
                    noOfGuestEditText.setText("");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("check_in_date", checkInDate);
                bundle.putString("check_out_date", checkOutDate);
                bundle.putInt("no_of_guest", noOfGuest);

                saveSharedPreference(noOfGuest, checkInDate, checkOutDate);
                HotelsListFragment hotelListFragment = new HotelsListFragment();
                hotelListFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_layout, hotelListFragment);
                fragmentTransaction.remove(HotelSearchFragment.this);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int noOfGuest;
                try {
                    noOfGuest = Integer.parseInt(noOfGuestEditText.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please enter valid Guest Number", Toast.LENGTH_LONG).show();
                    return;
                }
                saveSharedPreference(noOfGuest, getDateFromDatePicker(checkInDatePicker), getDateFromDatePicker(checkOutDatePicker));

            }
        });

        restoreButton = view.findViewById(R.id.restore_button);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreSharedPreference();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    // conversion of datepicker to string
    private String getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return dateFormat.format(calendar.getTime());
    }

    private void saveSharedPreference(int numberOfGuests, String checkInDateValue, String checkOutDateValue){
        sharedPreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(guestsCount, numberOfGuests);
        editor.putString(checkInDate, checkInDateValue);
        editor.putString(checkOutDate, checkOutDateValue);
        editor.commit();
    }

    private void restoreSharedPreference(){
        sharedPreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);

        try {
            if (sharedPreferences.contains(checkInDate)) {
                String[] checkInDateArray = sharedPreferences.getString(checkInDate, null).split("/");
                checkInDatePicker.init(Integer.parseInt(checkInDateArray[2]), Integer.parseInt(checkInDateArray[1])-1, Integer.parseInt(checkInDateArray[0]), null);
            }
            if (sharedPreferences.contains(checkOutDate)) {
                String[] checkOutDateArray = sharedPreferences.getString(checkOutDate, null).split("/");
                checkOutDatePicker.init(Integer.parseInt(checkOutDateArray[2]), Integer.parseInt(checkOutDateArray[1])-1, Integer.parseInt(checkOutDateArray[0]), null);
            }
            if (sharedPreferences.contains(guestsCount)) {
                noOfGuestEditText.setText(String.valueOf(sharedPreferences.getInt(guestsCount, 0)));
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}