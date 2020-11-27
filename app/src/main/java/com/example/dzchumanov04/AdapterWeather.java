package com.example.dzchumanov04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdapterWeather extends RecyclerView.Adapter<AdapterWeather.ViewHolder> {
    List<TempStamp> temps;

    public AdapterWeather (City city) {
        temps = city.getTemps();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_temp, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TempStamp temp = temps.get(position);
        holder.setData(temp.getTime(), temp.getIcon(), temp.getTemp());
    }

    @Override
    public int getItemCount() {
        return temps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private ImageView ivSky;
        private TextView tvTemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTime =  itemView.findViewById(R.id.tvTime);
            this.ivSky = itemView.findViewById(R.id.ivSky);
            this.tvTemp = itemView.findViewById(R.id.tvTemp);
        }
        public void setData(String time, int icon, String temp) {
            tvTime.setText(time);
            ivSky.setImageResource(icon);
            tvTemp.setText(temp);

        }
    }
}
