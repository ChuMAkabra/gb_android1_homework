package com.example.dzchumanov04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCity extends RecyclerView.Adapter<AdapterCity.ViewHolder> {
    private CityDataSource dataSource;
    private OnItemClickListener itemClickListener;
//    private Context appContext;

    // Передаем в конструктор источник данных
    // В нашем случае это класс CityDataSource, но может быть и запросом к БД
    AdapterCity(CityDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // интерфейс обработки нажатия на элемент списка RV
    interface OnItemClickListener
    {
        // метод также содержит порядковый номер элемента в списке
        void onItemClick(View v, int position);
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public AdapterCity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);

//        appContext = parent.getContext().getApplicationContext();

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

            if (itemClickListener != null) imageView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                itemClickListener.onItemClick(v, position);
            });
        }

        void setData(int cityImage, int cityName) {
            getImageView().setImageResource(cityImage);
            getTextView().setText(cityName);
        }

        //TODO: добавить клик-листенер
    }
}
