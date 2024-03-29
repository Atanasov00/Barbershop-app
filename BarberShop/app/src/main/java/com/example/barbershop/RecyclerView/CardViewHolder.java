package com.example.barbershop.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;

/**
 * A ViewHolder describes an item view and the metadata about its place within the RecyclerView.
 */
public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView placeImageView;
    TextView placeTextView;
    TextView dateTextView;

    RatingBar ratingBarView;

    private OnItemListener itemListener;

    public CardViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        placeImageView = itemView.findViewById(R.id.place_imageview);
        placeTextView = itemView.findViewById(R.id.place_textview);
        dateTextView = itemView.findViewById(R.id.date_textview);
        ratingBarView = itemView.findViewById(R.id.ratingBar_Card);
        itemListener = listener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
