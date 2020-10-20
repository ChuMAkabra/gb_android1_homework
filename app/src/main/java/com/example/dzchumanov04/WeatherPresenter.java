package com.example.dzchumanov04;

import android.net.Uri;

final class WeatherPresenter {
    private static WeatherPresenter instance = new WeatherPresenter();

    private String temperature;
    private Uri detailsLink;


    public void setDetailsLink(String city) {
        this.detailsLink = Uri.parse("https://yandex.ru/pogoda/" + city);
    }

    public Uri getDetailsLink() {
        return detailsLink;
    }

    private WeatherPresenter() {
        temperature = "";
    }

    String getTemperature() {
        return temperature;
    }

    void setTemperature(Integer temp) {
        this.temperature = temp + "°C";
    }


    static WeatherPresenter getInstance() {
        return instance;
    }
}
