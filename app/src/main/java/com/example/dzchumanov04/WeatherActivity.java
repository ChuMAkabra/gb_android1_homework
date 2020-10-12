package com.example.dzchumanov04;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {

    private static Map<String, String> cityWeather = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Button btnCity1 = findViewById(R.id.btnCity1);
        Button btnCity2 = findViewById(R.id.btnCity2);
        Button btnCity3 = findViewById(R.id.btnCity3);

        cityWeather.put(btnCity1.getText().toString(), "+14");
        cityWeather.put(btnCity2.getText().toString(), "+30");
        cityWeather.put(btnCity3.getText().toString(), "+20");

        btnCity1.setOnClickListener(listener);
        btnCity2.setOnClickListener(listener);
        btnCity3.setOnClickListener(listener);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private View.OnClickListener listener = v -> {
        Button button = findViewById(v.getId());
        TextView tvTemp = findViewById(R.id.txtTemp);

        String cityText = button.getText().toString();
        String temperature = cityWeather.get(cityText) + "°C";

        tvTemp.setText(temperature);

        String toastText = String.format("%s: %s", cityText, temperature);
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
    };
}
