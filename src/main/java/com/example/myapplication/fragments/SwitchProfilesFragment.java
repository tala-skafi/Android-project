package com.example.myapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ProfilesAdapter;
import com.example.myapplication.Model.WeatherProfile;
import com.example.myapplication.R;
import com.example.myapplication.contracts.MainActivityContract;
import com.example.myapplication.managers.SqLiteManager;

import java.util.List;

public class SwitchProfilesFragment extends Fragment {
    RecyclerView recycler_profiles;
    MainActivityContract contractor;
 static SwitchProfilesFragment instance;

    public static SwitchProfilesFragment getInstance() {
        if(instance==null)
            instance=new SwitchProfilesFragment();
        return instance;
    }

    public SwitchProfilesFragment() {

    }

    public static SwitchProfilesFragment newInstance() {
        SwitchProfilesFragment fragment = new SwitchProfilesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView =inflater.inflate(R.layout.fragment_switch_profiles, container, false);
        recycler_profiles =(RecyclerView)itemView.findViewById(R.id.recycler_profiles);
        recycler_profiles.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        getProfiles();

        return itemView;
    }

    private void getProfiles() {
        List<WeatherProfile> profiles = SqLiteManager.getInstance(getContext()).getAllEntries();
        displayProfiles(profiles);
    }

    public void setContractor(MainActivityContract contractor) {
        this.contractor = contractor;
    }

    private void displayProfiles(List<WeatherProfile> profiles) {
        ProfilesAdapter adapter=new ProfilesAdapter(getContext(),profiles, contractor);
        recycler_profiles.setAdapter(adapter);
    }
}