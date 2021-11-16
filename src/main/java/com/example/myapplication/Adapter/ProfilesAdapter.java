package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.WeatherProfile;
import com.example.myapplication.R;
import com.example.myapplication.contracts.MainActivityContract;

import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.MyViewHolder> {
    Context context;
    MainActivityContract contractor;
    List<WeatherProfile> profiles;

    public ProfilesAdapter(Context context, List<WeatherProfile> profiles, MainActivityContract contractor) {
        this.context = context;
        this.profiles = profiles;
        this.contractor = contractor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_profile,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_name.setText(profiles.get(position).getName());
        holder.txt_city.setText(profiles.get(position).getCity());
        holder.txt_unit.setText(profiles.get(position).getUnit());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contractor.currentProfileSwitched(profiles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_city, txt_unit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = (TextView)itemView.findViewById(R.id.txt_name);
            txt_city =(TextView)itemView.findViewById(R.id.txt_city);
            txt_unit =(TextView)itemView.findViewById(R.id.txt_unit);
        }
    }
}
