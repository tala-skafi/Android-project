package com.example.myapplication.fragments;

import com.example.myapplication.Common.Common;
import com.example.myapplication.managers.SqLiteManager;


public class AddProfileFragment extends ProfileFragmentBaseFragment {

    public static AddProfileFragment getInstance() {
        if (instance == null)
            instance = new AddProfileFragment();
        return (AddProfileFragment) instance;
    }

    @Override
    protected void saveActionTapped() {

        if(SqLiteManager.getInstance(getContext()).getDefaultProfile() == null)
            isDefault.setChecked(true);
        if(isDefault.isChecked())
            SqLiteManager.getInstance(getContext()).removePrevDefault();

        int id = SqLiteManager.getInstance(getContext()).insertEntry(
                name.getText().toString(),
                city.getText().toString(),
                apiKey.getText().toString(),
                unit.getText().toString(),
                isDefault.isChecked()
        );

        if (isDefault.isChecked())
            Common.updateInMemoryProfile(
                    id,
                    name.getText().toString(),
                    city.getText().toString(),
                    apiKey.getText().toString(),
                    unit.getText().toString(),
                    isDefault.isChecked());

        contractor.newProfileAdded(isDefault.isChecked());
    }
}