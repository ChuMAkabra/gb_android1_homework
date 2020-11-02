package com.example.dzchumanov04;

import android.content.res.Configuration;
import android.os.Bundle;

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
                    .replace(R.id.frWeather, new FragmentWeather())
                    .commit();
        }
    }
}
