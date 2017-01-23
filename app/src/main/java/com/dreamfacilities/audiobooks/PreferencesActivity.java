package com.dreamfacilities.audiobooks;

import android.app.Activity;
import android.os.Bundle;
import com.dreamfacilities.audiobooks.fragments.PreferencesFragment;

/**
 * Created by alex on 22/01/17.
 */

public class PreferencesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment()).commit();
    }
}
