package com.example.dzchumanov04;

import android.net.Uri;

final class WeatherPresenter {
    private static WeatherPresenter instance = new WeatherPresenter();
    private String temperature;
    private Uri link;

    private WeatherPresenter() {
        temperature = "";
    }

    void generateLink(String city) {
        link = Uri.parse("https://yandex.ru/pogoda/" + city);
    }

    Uri getLink() {
        return link;
    }

    void setTemperature(Integer temperature) {
        this.temperature = temperature + "°C";
    }

    String getTemperature() {
        return temperature;
    }


    static WeatherPresenter getInstance() {
        return instance;
    }
}
