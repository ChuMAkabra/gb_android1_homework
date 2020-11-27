package com.example.dzchumanov04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

public class FragmentSettings extends AbstractFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwitchCompat switchTheme = view.findViewById(R.id.swDarkTheme);
        switchTheme.setChecked(((WeatherActivityFr)getActivity()).isDarkTheme());
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                ((WeatherActivityFr) getActivity()).setDarkTheme(isChecked));
    }
}
