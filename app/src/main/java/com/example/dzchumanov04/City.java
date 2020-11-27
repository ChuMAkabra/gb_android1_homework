package com.example.dzchumanov04;

import android.net.Uri;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// данные для элемента списка
public class City implements Serializable {
    private static int[] icons_day;     // иконки дневные
    private static int[] icons_night;   // иконки ночные
    private int name;                   // ID стринги с названием города
    private int image;                  // ID фотографии города
    private Uri link;                   // ссылка на yandex.ru/pogoda
    private String gmtDiff;             // временная зона

    private List<TempStamp> temps;      // история и прогноз погоды
    private TempStamp curTemp;          // погода сейчас


    public City(int name, int image, String link, String gmtDiff, String curTemp, String ... strTemps) {
        this.name = name;
        this.image = image;
        this.link = Uri.parse(link);
        this.gmtDiff = gmtDiff;

        LocalTime localTime = LocalTime.now(ZoneId.of(gmtDiff));
        this.curTemp = new TempStamp(localTime, curTemp, icons_day, icons_night);

        temps = generateTemps(localTime, strTemps);
    }

    private List<TempStamp> generateTemps(LocalTime localTime, String[] strTemps) {
        List<TempStamp> temps = new ArrayList<>();

        int k = 0;
        for (int i = -2; i < strTemps.length -2; i++) {
            if (i == 0) k++;
            LocalTime hour = localTime.plusHours(i+k);
            temps.add(new TempStamp(hour, strTemps[i+2], icons_day, icons_night));
        }
        return temps;
    }

    // сеттеры
    public static void setIcons_day(int[] icons_day) {
        City.icons_day = icons_day;
    }

    public static void setIcons_night(int[] icons_night) {
        City.icons_night = icons_night;
    }

    // геттеры
    public int getName() {
        return name;
    }

    int getImage() {
        return image;
    }

    public Uri getLink() {
        return link;
    }

    public String getGmtDiff() {
        return gmtDiff;
    }

    public List<TempStamp> getTemps() {
        return temps;
    }

//    public String getTemp2hBefore() {
//        return temp2hBefore;
//    }
//
//    public String getTemp1hBefore() {
//        return temp1hBefore;
//    }
//
    public TempStamp getCurTemp() {
        return curTemp;
    }
//
//    public String getTemp1hAfter() {
//        return temp1hAfter;
//    }
//
//    public String getTemp2hAfter() {
//        return temp2hAfter;
//    }
}
