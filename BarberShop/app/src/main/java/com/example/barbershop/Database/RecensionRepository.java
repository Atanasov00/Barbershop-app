package com.example.barbershop.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.barbershop.Tables.Recension;

import java.text.ParseException;
import java.util.List;

public class RecensionRepository {

    private final RecensionDAO recensionDAO;

    private final LiveData<List<Recension>> recensionList;

    public RecensionRepository(Application application) throws ParseException {
        BarberShopDatabase db = BarberShopDatabase.getDatabase(application);
        recensionDAO = db.recensionDAO();
        recensionList = recensionDAO.getAllRecensions();
    }

    public LiveData<List<Recension>> getRecensionList() {return recensionList;}

    public void addRecension(Recension recension) {
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                recensionDAO.addRecension(recension);
            }
        });
    }

}
