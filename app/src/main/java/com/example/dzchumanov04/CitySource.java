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
        int[] names = getArray(R.array.cities); // строки описаний из ресурсов
        int[] images = getArray(R.array.pictures); // изображения
        String[] links = resources.getStringArray(R.array.links); // ссылка на yandex.ru/pogoda
        int[] gmtDiff = resources.getIntArray(R.array.time_diff); // разница во времени с GMT
        String[] temp2hBefore = resources.getStringArray(R.array.temp_2before);    // температура 2 часа назад
        String[] temp1hBefore = resources.getStringArray(R.array.temp_1before);    // температура 1 час  назад
        String[] temp = resources.getStringArray(R.array.temp);   // температура сейчас
        String[] temp1hAfter = resources.getStringArray(R.array.temp_1after);     // температура через 1 час
        String[] temp2hAfter = resources.getStringArray(R.array.temp_2after);     // температура через 2 часа
        // заполнение источника данных
        for (int i = 0; i < names.length; i++) {
            dataSource.add(new City(names[i], images[i], links[i], gmtDiff[i],
                    temp2hBefore[i], temp1hBefore[i], temp[i], temp1hAfter[i], temp2hAfter[i]));
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