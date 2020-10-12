package com.example.dzchumanov04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AbstractActivity{

    private static Map<String, Integer> cityWeather = new HashMap<>();
    private final WeatherPresenter presenter = WeatherPresenter.getInstance();

    private TextView tvTemp;
    private Button btnCity1;
    private Button btnCity2;
    private Button btnCity3;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvTemp = findViewById(R.id.txtTemp);
        btnCity1 = findViewById(R.id.btnCity1);
        btnCity2 = findViewById(R.id.btnCity2);
        btnCity3 = findViewById(R.id.btnCity3);
        btnBack = findViewById(R.id.btnBack);

        cityWeather.put(btnCity1.getText().toString(), -14);
        cityWeather.put(btnCity2.getText().toString(), 30);
        cityWeather.put(btnCity3.getText().toString(), 20);

        btnCity1.setOnClickListener(listener);
        btnCity2.setOnClickListener(listener);
        btnCity3.setOnClickListener(listener);

        btnBack.setOnClickListener(v -> finish());
        tvTemp.setText(presenter.getTemperature());
    }

    private View.OnClickListener listener = v -> {
        Button button = findViewById(v.getId());

        String cityText = button.getText().toString();
        presenter.setTemperature(cityWeather.get(cityText));
        tvTemp.setText(presenter.getTemperature());

        String toastText = String.format("%s: %s", cityText,
                presenter.getTemperature());
        Toast.makeText(getApplicationContext(), toastText,
                Toast.LENGTH_SHORT).show();
    };
}
