package com.example.dzchumanov04;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class WeatherActivityFr extends AbstractActivity {

    private void createFragment(int resIdLand, Class<? extends Fragment> cls) throws InstantiationException, IllegalAccessException {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // в портретной ориентации при нажатии кнопки "Назад" всегда возвращаться к списку
            if (cls == FragmentCity.class || getSupportFragmentManager().getBackStackEntryCount() == 1 ) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frContainer, cls.newInstance())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frContainer, cls.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        }
        else {
            // в альбомной ориентации при нажатии кнопки "Назад" приложение закрывается
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(resIdLand, cls.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_fr);

        try {
            createFragment(R.id.frCity, FragmentCity.class);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        /**
         * Система пишет: "Resource IDs will be non-final in Android Gradle Plugin version 5.0, avoid using them in switch case statements"
         * Не совсем  понял причины. Так не стоит все же?
         */
        try {
            switch (item.getItemId()) {
                case R.id.settings:
                    createFragment(R.id.frWeather, FragmentSettings.class);
                    return true;
                case R.id.about:
                    createFragment(R.id.frWeather, FragmentAbout.class);
                    return true;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
