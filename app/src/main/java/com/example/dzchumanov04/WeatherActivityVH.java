package com.example.dzchumanov04;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherActivityVH extends AbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_rv);

        // строим источник данных (пока это интерфейс)
        CityDataSource sourceData = new CitySourceBuilder()
                .setResources(getResources())
                .build();

        // Декорируем источник данных, теперь он изменяем (можно добавлять/удалять элементы)
        final CityChangeableSource sourceChangeableData = new CityChangeableSource(sourceData);
        // Создаем RecyclerView и возвращаем его адаптер
        final CityAdapter adapter = initRecyclerView(sourceChangeableData);
    }

    private CityAdapter initRecyclerView(CityChangeableSource sourceChangeableData) {
        // получаем RV
        RecyclerView recyclerView = findViewById(R.id.rvCity);
        // Эта установка служит для повышения производительности системы
        // (все элементы будут иметь одинаковый размер, и не надо его пересчитывать)
        recyclerView.setHasFixedSize(true);
        // добавляем менеджер
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // TODO дописать
        CityAdapter adapter = new CityAdapter(sourceChangeableData);
        recyclerView.setAdapter(adapter);
        return adapter;
    }
}