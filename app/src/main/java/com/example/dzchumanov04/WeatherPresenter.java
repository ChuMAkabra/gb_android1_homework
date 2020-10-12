package com.example.dzchumanov04;

final class WeatherPresenter {
    private static WeatherPresenter instance = new WeatherPresenter();

    private String temperature;

    private WeatherPresenter() {
        temperature = "";
    }

    String getTemperature() {
        return temperature;
    }

    void setTemperature(Integer temp) {
        this.temperature = temp + "°C";
    }


    static WeatherPresenter  getInstance() {
        return instance;
    }
}
