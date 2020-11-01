package com.example.dzchumanov04;

// данные для элемента списка
public class City {
    private int name;    // ID стринги с названием города
    private int image;   // ID фотографии города

    City(int name, int image) {
        this.name = name;
        this.image = image;
    }

    // геттеры
    public int getName() {
        return name;
    }

    int getImage() {
        return image;
    }
}
