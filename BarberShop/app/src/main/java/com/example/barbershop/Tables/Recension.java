package com.example.barbershop.Tables;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (tableName = "recension")

public class Recension {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recension_id")
    private int id;

    @ColumnInfo(name = "user_id")
    private final int user_id;

    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "rating")
    private final int rating;

    @ColumnInfo(name = "description")
    private final String description;

    @ColumnInfo(name = "image")
    private final String image;

    @ColumnInfo(name = "name")
    private final String name;

    public Recension(final int user_id, final String date, final int rating, final String description, final String image, final String name){
        this.user_id = user_id;
        this.date = date;
        this.rating = rating;
        this.description = description;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }


    public String getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
