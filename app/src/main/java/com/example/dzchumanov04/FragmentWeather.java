package com.example.dzchumanov04;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dzchumanov04.model.Hourly;
import com.example.dzchumanov04.model.WeatherOneCall;
import com.example.dzchumanov04.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class FragmentWeather extends AbstractFragment {
    private static final String CITY = "CITY";
    private Context application;
    private Context context;
    private String WEATHER_URL_DOMAIN = "https://api.openweathermap.org/data/2.5";
    private Hourly[] hourly;

    static FragmentWeather create(City city) {
        FragmentWeather fragment = new FragmentWeather();
        Bundle args = new Bundle();
        args.putSerializable(CITY, city);
        fragment.setArguments(args);

        return fragment;
    }
    City getCurrentCity() {
        return (City) (getArguments() != null ? getArguments().getSerializable(CITY) : null);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        application = inflater.getContext();
        context = getContext();
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstraintLayout constraintLayout = (ConstraintLayout) view;
        RecyclerView rvForecast = constraintLayout.findViewById(R.id.rvForecast);
        TextView temp = constraintLayout.findViewById(R.id.tvTemp);
        ImageView sky = constraintLayout.findViewById(R.id.ivIcon);
        TextView details = constraintLayout.findViewById(R.id.tvDetails);
        TextView name = constraintLayout.findViewById(R.id.tvCity);

        City curCity = getCurrentCity();
        //TODO: получать всю эту инфу с сервера

        // Получаем хендлер, указывающий на основной (UI) поток
        String apiCall = String.format("%s/weather?q=%s&units=metric&appid=%s", WEATHER_URL_DOMAIN, getString(curCity.getName()), BuildConfig.WEATHER_API_KEY);
        try {
            final URL url = new URL(apiCall);
            final Handler handler = new Handler();
            new Thread(() -> {
                HttpsURLConnection urlConnection = null;
                try{
                    // 1) Открываем соединение
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    // 2) Подготоваливаем запрос
                    // устанавливаем метод протокола - GET (получение данных)
                    urlConnection.setRequestMethod("GET");
                    // устанавливаем таймаут (ожидание не больше 10 сек)
                    urlConnection.setReadTimeout(10000);
                    // 3) Читаем данные в поток
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    // сохраняем все данные в виде строки
                    String strData = getLines(in);
                    // преобразуем данных запроса в модель посредством библиотеки Gson
                    Gson gson = new Gson();
                    final WeatherRequest weatherRequest = gson.fromJson(strData, WeatherRequest.class);
                    Time t = new Time((weatherRequest.getDt() + weatherRequest.getTimezone())*1000);

                    handler.post(() -> Toast.makeText(this.getContext(), String.format("Time = %s. Temp = %s", t.toString(), Float.toString(weatherRequest.getMain().getTemp())), Toast.LENGTH_LONG).show());

                    float lat = weatherRequest.getCoord().getLat();
                    float lon = weatherRequest.getCoord().getLon();
                    String apiCall2 = String.format("%s/onecall?lat=%s&lon=%s&units=metric&exclude=minutely,daily,alerts&appid=%s", WEATHER_URL_DOMAIN, Float.toString(lat), Float.toString(lon), BuildConfig.WEATHER_API_KEY);

                    final URL url2 = new URL(apiCall2);
                    try {
                        HttpsURLConnection urlConnection2 = (HttpsURLConnection) url2.openConnection();
                        BufferedReader in2 = new BufferedReader(new InputStreamReader(urlConnection2.getInputStream()));
                        String strData2 = getLines(in2);
                        WeatherOneCall weatherOneCall = gson.fromJson(strData2, WeatherOneCall.class);
                        hourly = weatherOneCall.getHourly();
//                        Time t2 = new Time((hourly[1].getDt() + weatherOneCall.getTimezone_offset()) *1000);
//                        for (Hourly h : hourly) {
//                            Time t3 = new Time((h.getDt() + weatherOneCall.getTimezone_offset())*1000);
//                            Log.d("HOURLY_TEST", t3.toString());
//                        }
//                        handler.post(() -> Toast.makeText(this.getContext(), String.format("Later: Time = %s. Temp = %s", t2.toString(), Float.toString(hourly[5].getTemp())), Toast.LENGTH_LONG).show());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (urlConnection != null)
                        urlConnection.disconnect();
                    }

                }catch (Exception e) {
                    Log.d(this.className, "Something went wrong...");
                    handler.post(() -> Toast.makeText(this.getContext(), "Something went wrong...!", Toast.LENGTH_LONG).show());
                    e.printStackTrace();
                }finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (curCity != null) {
            temp.setText(curCity.getCurTemp().getTemp());
            details.setVisibility(View.VISIBLE);

            details.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(curCity.getLink()));
                startActivity(intent);
            });
            name.setText(curCity.getName());
            int icon = curCity.getCurTemp().getIcon();
            sky.setImageResource(icon);
        }

        // добавляем к RV менеджер макетов
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvForecast.setLayoutManager(layoutManager);
        // добавляем к RV докоратор в виде сепаратора
        DividerItemDecoration decorator = new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL);
        decorator.setDrawable(Objects.requireNonNull(application.getDrawable(R.drawable.separator)));
        rvForecast.addItemDecoration(decorator);
        // добавляем к RV адаптер
        AdapterWeather adapterWeather = null;
        if (curCity != null) adapterWeather = new AdapterWeather(curCity);
        rvForecast.setAdapter(adapterWeather);
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
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
