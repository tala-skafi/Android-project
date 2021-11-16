package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.WeatherForecastResult;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {
    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String url = new StringBuilder("https://openweathermap.org/img/wn/")
                .append(weatherForecastResult.getDaily().get(position).getWeather().get(0).getIcon())
                .append("@4x.png").toString();
        Picasso.get().load(url).into(holder.img_weather);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, position);
        date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        holder.txt_day.setText(new StringBuilder(day));

        holder.txt_temperature_min.setText(new StringBuilder(weatherForecastResult.getDaily().get(position).getTemp().getMin() + "").append(Common.defaultProfile.getUnit().equalsIgnoreCase("Metric") ?"°C":"°F"));
        holder.txt_temperature_max.setText(new StringBuilder(weatherForecastResult.getDaily().get(position).getTemp().getMax() + "").append(Common.defaultProfile.getUnit().equalsIgnoreCase("Metric") ?"°C":"°F"));
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_day, txt_temperature_min, txt_temperature_max;
        ImageView img_weather;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_weather=(ImageView)itemView.findViewById(R.id.img_weather);
            txt_day =(TextView)itemView.findViewById(R.id.txt_day);
            txt_temperature_min =(TextView)itemView.findViewById(R.id.txt_temperature_min);
            txt_temperature_max =(TextView)itemView.findViewById(R.id.txt_temperature_max);
        }
    }
}
