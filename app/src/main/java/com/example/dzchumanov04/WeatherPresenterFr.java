package com.example.dzchumanov04;

import android.net.Uri;

final class WeatherPresenterFr {
    final WeatherPresenterFr instance = new WeatherPresenterFr();
    private String name;
    private String temperature;
    private Uri link;

    private WeatherPresenterFr() {
    }

    public WeatherPresenterFr getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
    }

    public Uri getLink() {
        return link;
    }
}
