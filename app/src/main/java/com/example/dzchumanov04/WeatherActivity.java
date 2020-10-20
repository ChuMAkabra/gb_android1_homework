package com.example.dzchumanov04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AbstractActivity {

    private static Map<String, Integer> cityTemperature = new HashMap<>();
    private static Map<String, String > cityName = new HashMap<>();
    private static final WeatherPresenter presenter = WeatherPresenter.getInstance();

    private TextView tvTemp;
    private Button btnCity1;
    private Button btnCity2;
    private Button btnCity3;
    private Button btnBack;
    private TextView tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvTemp = findViewById(R.id.txtTemp);
        btnCity1 = findViewById(R.id.btnCity1);
        btnCity2 = findViewById(R.id.btnCity2);
        btnCity3 = findViewById(R.id.btnCity3);
        btnBack = findViewById(R.id.btnBack);
        tvDetails = findViewById(R.id.txt_details);

        cityTemperature.put(btnCity1.getText().toString(), -14);
        cityTemperature.put(btnCity2.getText().toString(), 30);
        cityTemperature.put(btnCity3.getText().toString(), 20);

        cityName.put(btnCity1.getText().toString(), "moscow");
        cityName.put(btnCity2.getText().toString(), "los-angeles");
        cityName.put(btnCity3.getText().toString(), "verona");

        btnCity1.setOnClickListener(btnListener);
        btnCity2.setOnClickListener(btnListener);
        btnCity3.setOnClickListener(btnListener);

        btnBack.setOnClickListener(v -> finish());

        tvTemp.setText(presenter.getTemperature());

        tvDetails.setOnClickListener(tvListener);
        if (!tvTemp.getText().toString().isEmpty()) {
            tvDetails.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener btnListener = v -> {
        // получить текст кнопки для использования в качестве ключа HashMap
        Button button = findViewById(v.getId());
        String cityText = button.getText().toString();

        // записать значение температуры в презентер и вывести его на экран
        presenter.setTemperature(cityTemperature.get(cityText));
        tvTemp.setText(presenter.getTemperature());

        // сформировать ссылку на подробный прогноз в презентере и прикрепить
        presenter.setDetailsLink(cityName.get(cityText));
        tvDetails.setVisibility(View.VISIBLE);

        // сформировать текст тоста и отобразить его
        String toastText = String.format("%s: %s", cityText, presenter.getTemperature());
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
    };

    private View.OnClickListener tvListener = v-> {
        Intent intent = new Intent(Intent.ACTION_VIEW, presenter.getDetailsLink());
        startActivity(intent);
    };
}
