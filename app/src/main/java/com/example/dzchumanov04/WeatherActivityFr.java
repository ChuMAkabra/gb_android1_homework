package com.example.dzchumanov04;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;


public class WeatherActivityFr extends AbstractActivity {
    private final String PREVIOUS_ORIENTATION = "PREVIOUS_ORIENTATION";

    private void createFragment(int resIdLand, Class<? extends Fragment> cls) throws InstantiationException, IllegalAccessException {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // в портретной ориентации при нажатии кнопки "Назад" всегда возвращаться к списку
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frContainer, cls.newInstance());
            if (cls != FragmentCity.class && getSupportFragmentManager().getBackStackEntryCount() != 1)
                transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            // в альбомной ориентации при нажатии кнопки "Назад" приложение закрывается
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(resIdLand, cls.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_fr);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null ||
                savedInstanceState.getInt(PREVIOUS_ORIENTATION) != getResources().getConfiguration().orientation) {
            try {
                createFragment(R.id.frCity, FragmentCity.class);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        /**
         * Система пишет: "Resource IDs will be non-final in Android Gradle Plugin version 5.0, avoid using them in switch case statements"
         * Не совсем  понял причины. Так не стоит все же?
         */
        try {
            switch (item.getItemId()) {
                case R.id.settings:
                    createFragment(R.id.frWeather, FragmentSettings.class);
                    return true;
                case R.id.about:
                    createFragment(R.id.frWeather, FragmentAbout.class);
                    return true;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PREVIOUS_ORIENTATION, getResources().getConfiguration().orientation);
    }
}
