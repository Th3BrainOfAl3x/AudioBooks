package com.dreamfacilities.audiobooks;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alex on 14/02/17.
 */

public class BookSharedPrefenceStorage implements BookStorage {

    public static final String PREF_AUDIOBOOKS = "com.dreamfacilities.audiobooks_internal";
    public static final String KEY_LAST_BOOK = "last";
    private final Context context;
    private static BookSharedPrefenceStorage instance;

    public static BookStorage getInstance(Context context) {
        if (instance == null) {
            instance = new BookSharedPrefenceStorage(context);
        }
        return instance;
    }

    private BookSharedPrefenceStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasLastBook() {
        return getPreference().contains(KEY_LAST_BOOK);
    }


    @Override
    public String getLastBook() {
        return getPreference().getString(KEY_LAST_BOOK, "last");
    }

    @Override
    public void saveLastBook(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_AUDIOBOOKS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("last", key);
        editor.commit();
    }


    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOBOOKS, Context.MODE_PRIVATE);
    }


}