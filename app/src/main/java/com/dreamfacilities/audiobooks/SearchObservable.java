package com.dreamfacilities.audiobooks;

import android.support.v7.widget.SearchView;

import java.util.Observable;

/**
 * Created by alex on 16/02/17.
 */

public class SearchObservable extends Observable implements SearchView.OnQueryTextListener {

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setChanged();
        notifyObservers(newText);
        return true;
    }
}
