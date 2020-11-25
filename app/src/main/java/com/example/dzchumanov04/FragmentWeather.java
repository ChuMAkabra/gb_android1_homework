package com.example.dzchumanov04;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class FragmentWeather extends AbstractFragment {
    private static final String CITY = "CITY";
    private Context application;
    private Context context;
    private final Handler handler = new Handler(); // хендлер, указывающий на основной (UI) поток
    private final String WEATHER_URL_DOMAIN = "https://api.openweathermap.org/data/2.5";
    private String curTemp;
    private Bitmap curIcon;
    private Hourly[] hourly;
    private long timezoneOffset;
    private List<String>  times;
    private List<Bitmap> images;
    private List<String>  temps;

    private RecyclerView rvForecast;
    TextView temp;
    ImageView sky;
    TextView details;
    TextView name;

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
        // получить данные с сервера
        City curCity = getCurrentCity();
        Thread thread = new Thread(() -> {
            // запрос 1: через Current Weather Api получить координаты, текущие температуру и иконку погоды выбранного города
            String apiCall = String.format("%s/weather?q=%s&units=metric&appid=%s", WEATHER_URL_DOMAIN, getString(curCity.getName()), BuildConfig.WEATHER_API_KEY);
            final WeatherRequest weatherRequest = (WeatherRequest) getObjectFromGson(apiCall, WeatherRequest.class);
            curTemp = floatTempToString(weatherRequest.getMain().getTemp());
            curIcon = getBitmap(weatherRequest.getWeather()[0].getIcon());
            float lat = weatherRequest.getCoord().getLat();
            float lon = weatherRequest.getCoord().getLon();

            // запрос 2: через One Call Api по координатам получить почасовой прогноз погоды на 2 дня вперед и имена иконок погоды
            String apiCall2 = String.format("%s/onecall?lat=%s&lon=%s&units=metric&exclude=minutely,daily,alerts&appid=%s", WEATHER_URL_DOMAIN, Float.toString(lat), Float.toString(lon), BuildConfig.WEATHER_API_KEY);
            WeatherOneCall weatherOneCall = (WeatherOneCall) getObjectFromGson(apiCall2, WeatherOneCall.class);
            hourly = weatherOneCall.getHourly();
            timezoneOffset = weatherOneCall.getTimezone_offset();
            images = new ArrayList<>();

            // запрос 3: по именам иконок загрузить их изображения с сервера
            // получаем лист всех иконок
            List<String> imageNamesAll = new ArrayList<>();
            for (Hourly value : hourly) {
                imageNamesAll.add(value.getWeather()[0].getIcon());
            }
            // получаем набор уникальных иконок
            Set<String> imageNamesUnique = new HashSet<>(imageNamesAll);
            // получаем хэш-таблицу уникальных битмапов
            Map<String, Bitmap> imagesUnique = new HashMap<>(imageNamesUnique.size());
            // устанавливаем обратный отчет потоков
            CountDownLatch countDownLatch = new CountDownLatch(imageNamesUnique.size());
            // создаем динамический пул потоков
            ExecutorService executorService = Executors.newCachedThreadPool();
            // для каждой уникальной картинки:
            for (String iName : imageNamesUnique) {
                // качаем картинку в отдельном потоке
                executorService.execute(() -> {
                    Bitmap image = getBitmap(iName);
                    if (image != null) imagesUnique.put(iName,image);
                    countDownLatch.countDown();
                });
            }
            // дожидаемся завершения всех потоков
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // закрываем пул потоков
            executorService.shutdown();
            // получаем лист всех битмапов
            for (String name : imageNamesAll) {
                images.add(imagesUnique.get(name));
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // вывести данные в элементы фрагмента
        if (curCity != null) {
            // получить ссылки на элементы фрагмента
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            rvForecast = constraintLayout.findViewById(R.id.rvForecast);
            temp = constraintLayout.findViewById(R.id.tvTemp);
            sky = constraintLayout.findViewById(R.id.ivIcon);
            details = constraintLayout.findViewById(R.id.tvDetails);
            name = constraintLayout.findViewById(R.id.tvCity);

            //TODO: заменить ограниченный набор городов на запрос пользователя
            temp.setText(curTemp);
            name.setText(curCity.getName());
            //TODO: автоматически генерировать ссылку на яндекс
            details.setVisibility(View.VISIBLE);
            details.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(curCity.getLink()));
                startActivity(intent);
            });

            times = new ArrayList<>();
            temps = new ArrayList<>();
            for (Hourly h : hourly) {
                Time t3 = new Time((h.getDt() + timezoneOffset) * 1000);

                // не показывать секунды
                String[] strSplit = t3.toString().split(":");
                String hour = String.format("%s:%s", strSplit[0], strSplit[1]);
                times.add(hour);
                temps.add(floatTempToString(h.getTemp()));
            }

            // установим текущую картинку
            sky.setImageBitmap(curIcon);
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

//        if (curCity != null) adapterWeather = new AdapterWeather(curCity);
        if(times != null) adapterWeather = new AdapterWeather(times, images, temps);
        rvForecast.setAdapter(adapterWeather);
    }

    private Object getObjectFromGson(String apiCall, Class<? extends Object> objClass) {
        HttpsURLConnection urlConnection = null;
        InputStream in = null;
        try {
            URL url = new URL(apiCall);
            // 1) Открываем соединение
            urlConnection = (HttpsURLConnection) url.openConnection();
            // 2) Подготоваливаем запрос
            // устанавливаем метод протокола - GET (получение данных)
            urlConnection.setRequestMethod("GET");
            // устанавливаем таймаут (ожидание не больше 10 сек)
            urlConnection.setReadTimeout(10000);

            // 3) Читаем данные в поток
            BufferedReader inReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            // сохраняем все данные в виде строки
            String strData = getLines(inReader);
            // преобразуем данные запроса в модель посредством библиотеки Gson
            Gson gson = new Gson();
            return gson.fromJson(strData, objClass);
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(() -> Toast.makeText(this.getContext(), "Something went wrong...!", Toast.LENGTH_LONG).show());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private Bitmap getBitmap(String imageName) {
        String apiCall = String.format("https://openweathermap.org/img/wn/%s@2x.png", imageName);

        HttpsURLConnection urlConnection = null;
        Bitmap image = null;
        try {
            URL url = new URL(apiCall);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);

            InputStream in = urlConnection.getInputStream();
            image = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(() -> Toast.makeText(this.getContext(), "Something went wrong...!", Toast.LENGTH_LONG).show());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return image;
    }

    private String floatTempToString(float temp) {
        return String.format("%d°C", Math.round(temp));
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
