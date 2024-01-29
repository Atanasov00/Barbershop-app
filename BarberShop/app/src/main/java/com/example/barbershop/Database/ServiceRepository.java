package com.example.barbershop.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.barbershop.R;
import com.example.barbershop.Tables.Service;

import java.text.ParseException;
import java.util.List;

public class ServiceRepository {

    private final ServiceDAO serviceDAO;

    private int id;

    private final LiveData<List<Service>> serviceList;

    public ServiceRepository(Application application) throws ParseException {
        BarberShopDatabase db = BarberShopDatabase.getDatabase(application);
        serviceDAO = db.serviceDAO();
        serviceList = serviceDAO.getAllServices();
    }

    public LiveData<List<Service>> getServiceList() {
        return this.serviceList;
    }

    public void addService(Service service){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                serviceDAO.addService(service);
            }
        });
    }

    public int getIDfromName(String name){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                id = serviceDAO.getIDfromName(name);
            }
        });
        return id;
    }
}
