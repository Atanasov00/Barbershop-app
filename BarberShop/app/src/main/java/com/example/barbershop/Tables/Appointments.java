package com.example.barbershop.Tables;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointment", indices = {@Index(value = {"date", "user_id"}, unique = true)}, foreignKeys = {
        @ForeignKey(
                entity = ProfileInformation.class,
                parentColumns = "account_id",
                childColumns = "user_id",
                onUpdate = CASCADE,
                onDelete = CASCADE
        )
})
public class Appointments {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "appointment_id")
    private int id;

    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "time")
    private final String time;

    @ColumnInfo(name = "service")
    private final int service_id;

    @ColumnInfo(name = "user_id")
    private final int user_id;

    @ColumnInfo(name = "recension")
    private int recension;

    public Appointments(final String date, final String time, final int service_id, final int user_id, final int recension){
        this.date = date;
        this.time = time;
        this.service_id = service_id;
        this.user_id = user_id;
        this.recension = recension;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getService_id() {
        return service_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecension() {
        return recension;
    }

    public void setRecension(int recension) {
        this.recension = recension;
    }
}
