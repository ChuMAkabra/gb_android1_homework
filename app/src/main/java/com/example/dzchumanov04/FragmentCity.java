package com.example.dzchumanov04;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class FragmentCity extends Fragment {
    private RecyclerView recyclerView;
    private Context application;
    private Context fragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        application = inflater.getContext();
        fragment = getContext();
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvCity);

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
        // Эта установка служит для повышения производительности системы
        // (все элементы будут иметь одинаковый размер, и не надо его пересчитывать)
        recyclerView.setHasFixedSize(true);
        // добавляем менеджер
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(fragment, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // добавляем декоратор
        DividerItemDecoration decorator = new DividerItemDecoration(fragment, LinearLayoutManager.VERTICAL);
        decorator.setDrawable(Objects.requireNonNull(application.getDrawable(R.drawable.separator)));
        recyclerView.addItemDecoration(decorator);
        //добавляем адаптер
        CityAdapter adapter = new CityAdapter(sourceChangeableData);
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
