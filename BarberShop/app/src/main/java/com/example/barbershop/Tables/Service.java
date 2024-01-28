package com.example.barbershop.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "service", indices = {@Index(value = {"name"}, unique = true)})
public class Service {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "service_id")
    private int id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "duration")
    private final int duration;

    @ColumnInfo(name = "price")
    private final double price;

    public Service(final String name, final int duration, final double price){
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

}
