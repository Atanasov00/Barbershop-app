package com.example.barbershop.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.barbershop.Tables.Appointments;

import java.text.ParseException;
import java.util.List;

public class AppointmentsRepository {

    private final AppointmentsDAO appointmentsDAO;

    private final LiveData<List<Appointments>> appointmentsList;

    private LiveData<List<Appointments>> appointmentsDateList;

    public AppointmentsRepository(Application application) throws ParseException {
        BarberShopDatabase db = BarberShopDatabase.getDatabase(application);
        appointmentsDAO = db.appointmentsDAO();
        appointmentsList = appointmentsDAO.getAllAppointments();
    }

    public LiveData<List<Appointments>> getAppointmentsList() {
        return appointmentsList;
    }

    public LiveData<List<Appointments>> getUserAppointments(int id){
        return appointmentsDAO.getUserAppointments(id);
    }

    public void addAppointment(Appointments appointment){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                appointmentsDAO.addAppointment(appointment);
            }
        });
    }

    public LiveData<List<Appointments>> getDateAppointmentsList(String date){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                appointmentsDateList = appointmentsDAO.getDateAppointments(date);
            }
        });
        return appointmentsDateList;
    }

    public void updateRecension(int value, int id){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                appointmentsDAO.updateRecension(value, id);
            }
        });
    }
}
