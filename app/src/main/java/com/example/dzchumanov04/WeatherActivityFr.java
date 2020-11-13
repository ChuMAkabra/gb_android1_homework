package com.example.dzchumanov04;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WeatherActivityFr extends AbstractActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_fr);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frContainer, new FragmentCity())
                    .commit();
        }
        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frCity, new FragmentCity())
//                    .replace(R.id.frWeather, new FragmentWeather())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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
        //TODO замени тост переходом на соответствующую страницу
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(getApplicationContext(), getString(R.string.settings), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(this, getString(R.string.about), Toast.LENGTH_SHORT).show();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frContainer, new FragmentAbout())
                            .commit();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frWeather, new FragmentAbout())
                            .commit();
                }


                return true;
        }
        return true;
    }
}
