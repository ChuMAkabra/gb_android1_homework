package com.example.dzchumanov04;

import java.io.Serializable;

// данные для элемента списка
public class City implements Serializable {
    private int name;    // ID стринги с названием города
    private int image;   // ID фотографии города
    private String link; // текст ссылки на yandex.ru/pogoda

    public City(int name, int image, String link) {
        this.setName(name);
        this.setImage(image);
        this.setLink(link);
    }

    public int getName() {
        return name;
    }

    int getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
