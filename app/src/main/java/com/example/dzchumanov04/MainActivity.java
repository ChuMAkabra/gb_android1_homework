package com.example.dzchumanov04;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWeather = findViewById(R.id.btnWeather);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        WeatherActivity.class));
            }
        });

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
}
