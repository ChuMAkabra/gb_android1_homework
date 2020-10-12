package com.example.dzchumanov04;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Создал этот абстрактный класс для логирования методов обратного вызова
 * Наследуя его, можно избежать:
 * 1) однообразоного переопределения всех интересующих нас методов;
 * 2) ручного указания названия активити в первом аргументе Log.d().
 *
 * Для корректной работы в классе активити достаточно переопределить onCreate():
 * 1) передать в setClassName() имя активити в строковом формате;
 * 2) вызвать super.onCreate().
 */

public abstract class AbstractActivity extends AppCompatActivity {
    protected String className;

    // позволяет получить имя активити (в строковом формате) для логирования
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(className, "onCreate()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(className, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(className, "onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(className, "onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(className, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(className, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(className, "onStop()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(className, "onSaveInstanceState()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(className, "onDestroy()");
    }
}
