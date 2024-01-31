package com.example.barbershop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.barbershop.Fragments.LoginFragment;
import com.example.barbershop.R;

import Utils.Utilities;

public class MainActivity extends AppCompatActivity {

    private boolean nightMode = false;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String NIGHT_MODE_KEY = "nightMode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "default");

        if(!email.equals("default")) {
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
            this.finish();
        } else if(savedInstanceState == null)
            Utilities.insertMainActivityFragment(this, new LoginFragment(), LoginFragment.class.getSimpleName());


        //setSupportActionBar(findViewById(R.id.topAppBar));
        nightMode = loadNightModeState();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_settings:
                toggleNightMode();
                recreate(); // Aggiorna l'UI con il nuovo tema
                return true;
        }
        return false;
    }

    private void toggleNightMode() {
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            System.out.println("Click");
            nightMode = false;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            System.out.println("Click night");
            nightMode = true;
        }

        // Salva lo stato di nightMode nelle preferenze
        saveNightModeState();
    }

    private void saveNightModeState() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(NIGHT_MODE_KEY, nightMode);
        editor.apply();
    }

    private boolean loadNightModeState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(NIGHT_MODE_KEY, false);
    }

}