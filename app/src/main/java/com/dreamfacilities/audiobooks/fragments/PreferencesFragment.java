package com.dreamfacilities.audiobooks.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.dreamfacilities.audiobooks.R;

/**
 * Created by alex on 22/01/17.
 */

public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
