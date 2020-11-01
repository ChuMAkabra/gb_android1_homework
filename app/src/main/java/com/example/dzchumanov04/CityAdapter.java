package com.example.dzchumanov04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private CityDataSource dataSource;

    // Передаем в конструктор источник данных
    // В нашем случае это класс CityDataSource, но может быть и запросом к БД
    CityAdapter(CityDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        //TODO: добавить клик-листенер

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = dataSource.getCity(position);
        holder.setData(city.getImage(), city.getName());
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на
    // один пункт списка
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        ImageView getImageView() {
            return imageView;
        }

        TextView getTextView() {
            return textView;
        }

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cityImage);
            textView = itemView.findViewById(R.id.cityItem);
        }

        void setData(int cityImage, int cityName) {
            getImageView().setImageResource(cityImage);
            getTextView().setText(cityName);
        }

        //TODO: добавить клик-листенер
    }
}
