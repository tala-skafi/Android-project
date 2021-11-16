package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.WeatherProfile;
import com.example.myapplication.managers.SqLiteManager;


public class EditProfileFragment extends ProfileFragmentBaseFragment {

    int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        id = Common.defaultProfile.getId();
        fillData(id);
        return view;
    }

    private void fillData(int id) {
        WeatherProfile profile = SqLiteManager.getInstance(getContext()).getEntryByKey(
                SqLiteManager.KEY_ID, String.valueOf(id)
        );

        name.setText(profile.getName());
        city.setText(profile.getCity());
        apiKey.setText(profile.getApiKey());
        unit.setText(profile.getUnit());
        isDefault.setChecked(profile.isDefault());

        title.setText("Edit Profile");
    }

    public static EditProfileFragment getInstance() {
        if(instance==null)
            instance=new EditProfileFragment();
        return (EditProfileFragment) instance;
    }

    @Override
    protected void saveActionTapped() {
        if(SqLiteManager.getInstance(getContext()).getDefaultProfile() == null)
            isDefault.setChecked(true);
        if(isDefault.isChecked())
            SqLiteManager.getInstance(getContext()).removePrevDefault();

        SqLiteManager.getInstance(getContext()).editEntry(
                id,
                name.getText().toString(),
                city.getText().toString(),
                apiKey.getText().toString(),
                unit.getText().toString(),
                isDefault.isChecked()
        );

        if (isDefault.isChecked() || SqLiteManager.getInstance(getContext()).getDefaultProfile() == null || id == Common.defaultProfile.getId()) {
            Common.updateInMemoryProfile(
                    id,
                    name.getText().toString(),
                    city.getText().toString(),
                    apiKey.getText().toString(),
                    unit.getText().toString(),
                    isDefault.isChecked());
            contractor.currentProfileUpdated();
        }
    }
}