package com.example.barbershop.Tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "account", indices = {@Index(value = "account_email", unique = true)})
public class ProfileInformation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "account_email")
    private final String email;

    @NonNull
    @ColumnInfo(name = "account_password")
    private String password;

    @NonNull
    @ColumnInfo(name = "account_name")
    private final String name;

    @NonNull
    @ColumnInfo(name = "account_surname")
    private final String surname;

    public ProfileInformation(@NonNull final String email, @NonNull final String password, @NonNull final String name, @NonNull final String surname){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
