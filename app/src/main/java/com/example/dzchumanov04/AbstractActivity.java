package com.example.dzchumanov04;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

/**
 * Создал этот абстрактный класс для логирования методов обратного вызова
 * Наследуя его, можно избежать:
 * 1) однообразоного переопределения всех интересующих нас методов;
 * 2) ручного указания названия активити в первом аргументе Log.d().
 */

public abstract class AbstractActivity extends AppCompatActivity {
    protected String className = this.getClass().getName();
    protected final String SP_NAME = "WEATHER";
    protected final String SP_DARK_THEME = "IS_DARK_THEME";

    public void setFragmentToDisplay(String fragmentToDisplay) {
        this.fragmentToDisplay = fragmentToDisplay;
    }

    public String getFragmentToDisplay() {
        return fragmentToDisplay;
    }

    protected String fragmentToDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isDarkTheme())
            setTheme(R.style.AppDarkTheme);
        else
            setTheme(R.style.AppTheme);

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

    public boolean isDarkTheme() {
        SharedPreferences sh = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        return sh.getBoolean(SP_DARK_THEME, true);
    };

    public void setDarkTheme(boolean isChecked){
        SharedPreferences sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if(isChecked) {
            sp.edit()
                    .putBoolean(SP_DARK_THEME, true)
                    .apply();
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            sp.edit()
                    .putBoolean(SP_DARK_THEME, false)
                    .apply();
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    };
}