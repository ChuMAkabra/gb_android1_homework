package com.example.dzchumanov04;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

class CitySource implements CityDataSource {
    private List<City> dataSource;  // строим этот источник данных
    private Resources resources;    // ресурсы приложения

    CitySource(Resources resources) {
        dataSource = new ArrayList<>(4);
        this.resources = resources;
    }

    CitySource init(){
        // передача списка иконок
        int[] names = getArray(R.array.cities); // строки описаний из ресурсов
        int[] images = getArray(R.array.pictures); // изображения
        String[] links = resources.getStringArray(R.array.links); // ссылка на yandex.ru/pogoda
        // заполнение источника данных
        for (int i = 0; i < names.length; i++) {
            dataSource.add(new City(names[i], images[i], links[i]));
        }
        return this;
    }

    public City getCity(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    // Механизм вытаскивания идентификаторов (к сожалению просто массив не работает)
    // https://stackoverflow.com/questions/5347107/creating-integer-array-of-resource-ids
    private int[] getArray(int arrayId){
        TypedArray ta = resources.obtainTypedArray(arrayId);
        int length = ta.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = ta.getResourceId(i, 0);
        }
        return answer;
    }
}