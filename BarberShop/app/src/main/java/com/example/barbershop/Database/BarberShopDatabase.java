package com.example.barbershop.Database;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.ProfileInformation;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.Tables.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProfileInformation.class, Appointments.class, Service.class, Recension.class}, version = 10)
public abstract class BarberShopDatabase extends RoomDatabase {

    public abstract ProfileInformationDAO profileInformationDAO();

    public abstract ServiceDAO serviceDAO();

    public abstract AppointmentsDAO appointmentsDAO();

    public abstract RecensionDAO recensionDAO();

    private static volatile BarberShopDatabase INSTANCE;

    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static BarberShopDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (BarberShopDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BarberShopDatabase.class, "barbershop_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
