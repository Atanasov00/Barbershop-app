package com.example.barbershop.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.ProfileInformation;

import java.text.ParseException;
import java.util.List;

@Dao
public interface AppointmentsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addAppointment(Appointments appointment);

    @Query("SELECT * FROM appointment ORDER BY appointment_id DESC")
    LiveData<List<Appointments>> getAllAppointments() throws ParseException;

    @Query("SELECT * FROM appointment WHERE date = :date")
    LiveData<List<Appointments>> getDateAppointments(String date);

    @Query("SELECT * FROM appointment WHERE user_id = :userID ORDER BY appointment_id DESC")
    LiveData<List<Appointments>> getUserAppointments(int userID);

    @Query("UPDATE appointment SET recension = :value WHERE appointment_id = :id")
    void updateRecension(int value, int id);
}
