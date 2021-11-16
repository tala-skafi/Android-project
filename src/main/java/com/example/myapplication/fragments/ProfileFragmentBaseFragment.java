package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.contracts.MainActivityContract;


abstract public class ProfileFragmentBaseFragment extends Fragment {
    protected EditText name, city, apiKey, unit;
    protected CheckBox isDefault;
    protected Button save, cancel;
    protected TextView title;
    protected MainActivityContract contractor;

    static ProfileFragmentBaseFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_profile, container, false);
       title = view.findViewById(R.id.profile_title);
       name = view.findViewById(R.id.profile_name);
       city = view.findViewById(R.id.city_name);
       apiKey = view.findViewById(R.id.api_key);
       unit = view.findViewById(R.id.unit);
       isDefault = view.findViewById(R.id.mark_as_default);
       save = view.findViewById(R.id.save_button);
       cancel = view.findViewById(R.id.cancel_button);
       setOnClickListeners();

       return view;
    }

    private void setOnClickListeners() {
        save.setOnClickListener(view -> saveActionTapped());
        cancel.setOnClickListener(view -> cancelActionTapped());
    }

    abstract protected void saveActionTapped();

    private void cancelActionTapped() {
        contractor.cancel();
    }

    public void setContractor(MainActivityContract activity) {
        contractor = activity;
    }
}