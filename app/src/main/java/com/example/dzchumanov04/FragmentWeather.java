package com.example.dzchumanov04;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentWeather extends Fragment {
    public static final String CITY = "CITY";

    public static FragmentWeather create(City city) {
        FragmentWeather fragment = new FragmentWeather();
        Bundle args = new Bundle();
        args.putSerializable(CITY, city);
        fragment.setArguments(args);

        return fragment;
    }
    public City getCurrentCity() {
        return (City) (getArguments() != null ? getArguments().getSerializable(CITY) : null);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstraintLayout constraintLayout = (ConstraintLayout) view;
        RecyclerView rvForecast = constraintLayout.findViewById(R.id.rvForecast);
        TextView temp = constraintLayout.findViewById(R.id.tvTemp);
        TextView details = constraintLayout.findViewById(R.id.tvDetails);
        TextView name = constraintLayout.findViewById(R.id.tvCity);

        City curCity = getCurrentCity();
        if (curCity != null) {
            temp.setText(String.format("%s°C", curCity.getTemp()));
            details.setVisibility(View.VISIBLE);

            details.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, curCity.getLink());
                startActivity(intent);
            });
            name.setText(curCity.getName());
        }

/**
 * Тестовая версия горизонтального RecyclerView
 */
        CityDataSource dataSource = new CitySourceBuilder()
                .setResources(getResources())
                .build();
        CityChangeableSource data = new CityChangeableSource(dataSource);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvForecast.setLayoutManager(layoutManager);

        AdapterCity adapterCity = new AdapterCity(data);
        rvForecast.setAdapter(adapterCity);
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
