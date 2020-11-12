package com.example.dzchumanov04;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class TempStamp implements Serializable {
    private String time;
    private String temp;
    private String condition;
    private int icon;

    private final String[] conditions = {"clear", "few", "scattered", "broken", "rain", "shower", "thunder", "snow", "mist"};

    public TempStamp(LocalTime time, String weather, int[] icons_day, int[] icons_night) {
        String[] parts = weather.split("\\s", 2);
        this.temp = String.format("%s°C", parts[0]);
        if (parts.length == 2) this.condition = parts[1];
//        icon = icons_day[2];
        this.time = time.truncatedTo(ChronoUnit.HOURS).toString();

        if (condition != null)
        if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(20, 0))) {
            setIcon(icons_day);
        } else {
            setIcon(icons_night);
        }

    }

    private void setIcon(int[] icons_day) {
        for (int i = 0; i < conditions.length; i++) {
            if (condition.equals(conditions[i])) {
                icon = icons_day[i];
                break;
            }
        }
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public int getIcon() {
        return icon;
    }
}
