package com.example.hotelreservation_a00444846;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuestDetailsListAdapter extends RecyclerView.Adapter<GuestDetailsListAdapter.ViewHolder> {

    private final int numberOfGuests;
    private final LayoutInflater layoutInflater;
    private ItemClickListener clickListener;

    GuestDetailsListAdapter(Context context, int numberOfGuests) {
        this.layoutInflater = LayoutInflater.from(context);
        this.numberOfGuests = numberOfGuests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.guest_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.guestNoTextView.setText("Guest " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return numberOfGuests;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView guestNoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guestNoTextView = itemView.findViewById(R.id.guest_no_text_view);
//            guestNameEditText = itemView.findViewById(R.id.guest_name_edit_text);
//            guestGenderTextView = itemView.findViewById(R.id.gender_text_view);
//            hotelPrice = itemView.findViewById(R.id.gender_radio_group);

        }
    }
}
