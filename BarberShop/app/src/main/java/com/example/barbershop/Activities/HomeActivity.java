package com.example.barbershop.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Fragments.HomeFragment;
import com.example.barbershop.Fragments.MapFragment;
import com.example.barbershop.Fragments.ProfileFragment;
import com.example.barbershop.Fragments.RecensionFragment;
import com.example.barbershop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import Utils.Utilities;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setSupportActionBar(findViewById(R.id.topAppBar));

        if(savedInstanceState == null) {
            Utilities.insertHomeActivityFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
        }
        AppCompatActivity activity = this;
        navigationView = findViewById(R.id.bottomAppBar);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.homePage:
                        Utilities.insertHomeActivityFragment(activity, new HomeFragment(), HomeFragment.class.getSimpleName());
                        return true;
                    case R.id.recension:
                        Utilities.insertHomeActivityFragment(activity, new RecensionFragment(), RecensionFragment.class.getSimpleName());
                        return true;
                    case R.id.profile:
                        Utilities.insertHomeActivityFragment(activity, new ProfileFragment(), ProfileFragment.class.getSimpleName());
                        return true;
                    case R.id.mapItem:
                        Utilities.insertHomeActivityFragment(activity, new MapFragment(), MapFragment.class.getSimpleName());
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }
}
