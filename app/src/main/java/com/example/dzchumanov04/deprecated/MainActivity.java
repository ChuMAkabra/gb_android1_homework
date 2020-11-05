package com.example.dzchumanov04.deprecated;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dzchumanov04.AbstractActivity;
//import com.example.dzchumanov04.Constants;
import com.example.dzchumanov04.R;

import java.util.Objects;

public class MainActivity extends AbstractActivity {

    private final int REQUEST_CODE = 101;
    private TextView tvCity;
    private TextView tvTemp;
    private Button btnWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);

        btnWeather = findViewById(R.id.btnWeather);
        btnWeather.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        else if (resultCode == RESULT_OK && data != null) {
            Parcel inParcel = (Parcel) Objects.requireNonNull(data.getExtras()).getSerializable(Constants.PARCEL);
            if (inParcel != null) {
                tvCity.setText(inParcel.getCity());
                tvTemp.setText(inParcel.getTemperature());
            }
        }
    }

    /**
     * При нажатии устанавливает размер текста в 40dp и меняет текст.
     * При смене языка в настройках, текст и его размер сбросятся до дефолтных.
     * При смене размера шрифта в настройках, текст меняет дефолтный размер
     */
    public void helloOnClick(View view) {
        TextView textHello = findViewById(R.id.text_hello);
        textHello.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        if (textHello.getText().toString().equals("Hello!"))
            textHello.setText("Привет!");
        else textHello.setText("Hello!");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.CITY, tvCity.getText().toString());
        outState.putString(Constants.TEMP, tvTemp.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvCity.setText(savedInstanceState.getString(Constants.CITY));
        tvTemp.setText(savedInstanceState.getString(Constants.TEMP));
    }
}
