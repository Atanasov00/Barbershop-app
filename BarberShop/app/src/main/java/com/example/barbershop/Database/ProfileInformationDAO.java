package com.example.barbershop.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.barbershop.Tables.ProfileInformation;

import java.text.ParseException;
import java.util.List;

@Dao
public interface ProfileInformationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProfile(ProfileInformation profile);

    @Transaction
    @Query("SELECT * FROM account ORDER BY account_id DESC")
    LiveData<List<ProfileInformation>> getAllProfilesCredentials() throws ParseException;

    @Transaction
    @Query("UPDATE account SET account_password = :newPassword WHERE account_id = :id")
    void updatePassword(String newPassword, int id);

}
