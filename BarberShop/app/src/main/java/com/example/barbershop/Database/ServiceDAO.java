package com.example.barbershop.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.barbershop.Tables.Service;

import java.text.ParseException;
import java.util.List;

@Dao
public interface ServiceDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addService(Service service);

    @Transaction
    @Query("SELECT * FROM serviceT ORDER BY service_id DESC")
    LiveData<List<Service>> getAllServices() throws ParseException;

}
