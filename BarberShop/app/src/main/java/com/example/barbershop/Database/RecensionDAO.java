package com.example.barbershop.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.barbershop.Tables.Recension;

import java.util.List;

@Dao
public interface RecensionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addRecension(Recension recension);

    @Query("SELECT * FROM recension ORDER BY recension_id DESC")
    LiveData<List<Recension>> getAllRecensions();



}
