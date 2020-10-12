package com.example.dzchumanov04;

import android.widget.TextView;

final class WeatherPresenter {
    private static WeatherPresenter instance = new WeatherPresenter();

    private String temperature;

    public TextView getTvTemp() {
        return etTemp;
    }

    public void setTvTemp(TextView etTemp) {
        this.etTemp = etTemp;
    }

    private TextView etTemp; 

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
