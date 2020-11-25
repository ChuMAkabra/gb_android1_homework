package com.example.dzchumanov04;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class FragmentCity extends AbstractFragment {
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
        final CityDataSource sourceData = new CitySourceBuilder()
                .setResources(getResources())
                .build();

        // Создаем RecyclerView и возвращаем его адаптер
        final AdapterCity adapter = initRecyclerView(sourceData);
    }

    private AdapterCity initRecyclerView(CityDataSource sourceData) {
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
        AdapterCity adapter = new AdapterCity(sourceData);

        /**
         *  Я верно понимаю, что такой подход нужен именно для того, чтобы не
         *  передавать ссылки на фрагмент менеджер и ресурсы в класс фрагмента?
         */
        // передаем фрагменту обработчик нажатий на элемент RV
        adapter.setOnItemClickListener(new AdapterCity.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                /**
                 * действительно ли надо проверять не равен ли getFragmentManager() null?
                 * И как это лучше делать?
                  */
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frContainer, FragmentWeather.create (sourceData.getCity(position)))
                            .addToBackStack(null)
                            .commit();

                }
                else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frWeather, FragmentWeather.create(sourceData.getCity(position)))
                            .commit();

                }
//                makeSnackBarWithButton(v, sourceData.getCity(position).getLink());
            }
        });
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    private void makeSnackBarWithButton(View v, String link) {
        Snackbar
            .make(v, getString(R.string.snackBar), Snackbar.LENGTH_LONG)
            .setAction("YANDEX", view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
                })
            .show();
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
