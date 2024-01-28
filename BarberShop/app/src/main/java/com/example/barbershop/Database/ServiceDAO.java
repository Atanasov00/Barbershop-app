package com.example.barbershop.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.barbershop.Tables.Service;

import java.text.ParseException;
import java.util.List;

public interface ServiceDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addService(Service service);

    @Query("SELECT * FROM service ORDER BY service_id DESC")
    LiveData<List<Service>> getAllServices() throws ParseException;

}
