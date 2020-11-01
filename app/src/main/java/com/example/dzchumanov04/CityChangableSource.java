package com.example.dzchumanov04;

class CityChangeableSource implements CitiesChangeableSource {

    private int count;
    private CityDataSource dataSource;

    CityChangeableSource(CityDataSource dataSource) {
        count = dataSource.size();
        this.dataSource = dataSource;
    }

    @Override
    public void add() {
        if (count < dataSource.size()){
            count ++;
        }
    }

    @Override
    public void delete() {
        if (count > 0){
            count--;
        }
    }

    @Override
    public City getCity(int position) {
        return dataSource.getCity(position);
    }

    @Override
    public int size() {
        return count;
    }
}
