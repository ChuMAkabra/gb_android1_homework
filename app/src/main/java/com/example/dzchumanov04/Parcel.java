package com.example.dzchumanov04;

import java.io.Serializable;

public class Parcel implements Serializable {
    private String temperature;
    private String city;

    Parcel(String temperature, String city) {
        this.temperature = temperature;
        this.city = city;
    }

    String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
