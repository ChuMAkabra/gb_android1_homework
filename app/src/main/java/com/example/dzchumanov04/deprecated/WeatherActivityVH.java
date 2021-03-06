//package com.example.dzchumanov04.depricated;
//
//import android.os.Bundle;
//
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.dzchumanov04.AbstractActivity;
//import com.example.dzchumanov04.AdapterCity;
//import com.example.dzchumanov04.CityChangeableSource;
//import com.example.dzchumanov04.CityDataSource;
//import com.example.dzchumanov04.CitySourceBuilder;
//import com.example.dzchumanov04.R;
//
//import java.util.Objects;
//
//public class WeatherActivityVH extends AbstractActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather_rv);
//
//        // строим источник данных (пока это интерфейс)
//        CityDataSource sourceData = new CitySourceBuilder()
//                .setResources(getResources())
//                .build();
//
//        // Декорируем источник данных, теперь он изменяем (можно добавлять/удалять элементы)
//        final CityChangeableSource sourceChangeableData = new CityChangeableSource(sourceData);
//        // Создаем RecyclerView и возвращаем его адаптер
//        final AdapterCity adapter = initRecyclerView(sourceChangeableData);
//    }
//
//    private AdapterCity initRecyclerView(CityChangeableSource sourceChangeableData) {
//        // получаем RV
//        RecyclerView recyclerView = findViewById(R.id.rvCity);
//        // Эта установка служит для повышения производительности системы
//        // (все элементы будут иметь одинаковый размер, и не надо его пересчитывать)
//        recyclerView.setHasFixedSize(true);
//        // добавляем менеджер
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        // добавляем декоратор
//        DividerItemDecoration decorator = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
//        decorator.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.separator)));
//        recyclerView.addItemDecoration(decorator);
//        //добавляем адаптер
//        AdapterCity adapter = new AdapterCity(sourceChangeableData);
//        recyclerView.setAdapter(adapter);
//        return adapter;
//    }
//}