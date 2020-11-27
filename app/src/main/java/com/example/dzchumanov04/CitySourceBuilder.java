package com.example.dzchumanov04;

import android.content.res.Resources;

class CitySourceBuilder {
    private Resources resources;

    CitySourceBuilder setResources(Resources resources){
        this.resources = resources;
        return this;
    }

    CityDataSource build(){
        CitySource citySource = new CitySource(resources);
        citySource.init();
        return citySource;
    }
}
