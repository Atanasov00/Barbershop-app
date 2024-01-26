package com.example.barbershop;

import android.widget.RatingBar;

/**
 * Class which represents every card item with its information (image, place, data, description)
 */
public class CardItem {
    private String imageResource;
    private String placeName;
    private String placeDescription;
    private String date;

    private int rating;

    public CardItem(String imageResource, String placeName, String placeDescription, String date, int rating) {
        this.imageResource = imageResource;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.date = date;
        this.rating = rating;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public String getDate() {
        return date;
    }

    public int getRating() {return rating;}
}
