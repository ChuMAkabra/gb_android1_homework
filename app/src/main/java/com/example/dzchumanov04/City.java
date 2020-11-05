package com.example.dzchumanov04;

import android.net.Uri;

import java.io.Serializable;

// данные для элемента списка
public class City implements Serializable {
    private int name;               // ID стринги с названием города
    private int image;              // ID фотографии города
    private Uri link;               // ссылка на yandex.ru/pogoda
    private int gmtDiff;            // разница во времени с GMT
    private String temp2hBefore;    // температура 2 часа назад
    private String temp1hBefore;    // температура 1 час  назад
    private String temp;            // температура сейчас
    private String temp1hAfter;     // температура через 1 час
    private String temp2hAfter;     // температура через 2 часа

    public City(int name, int image, String link, int gmtDiff, String temp2hBefore, String temp1hBefore, String temp, String temp1hAfter, String temp2hAfter) {
        this.name = name;
        this.image = image;
        this.link = Uri.parse(link);
        this.gmtDiff = gmtDiff;
        this.temp2hBefore = temp2hBefore;
        this.temp1hBefore = temp1hBefore;
        this.temp = temp;
        this.temp1hAfter = temp1hAfter;
        this.temp2hAfter = temp2hAfter;
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

    public int getGmtDiff() {
        return gmtDiff;
    }

    public String getTemp2hBefore() {
        return temp2hBefore;
    }

    public String getTemp1hBefore() {
        return temp1hBefore;
    }

    public String getTemp() {
        return temp;
    }

    public String getTemp1hAfter() {
        return temp1hAfter;
    }

    public String getTemp2hAfter() {
        return temp2hAfter;
    }
}
