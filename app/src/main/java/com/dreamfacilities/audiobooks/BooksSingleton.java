package com.dreamfacilities.audiobooks;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

/**
 * Created by alex on 19/02/17.
 */
public class BooksSingleton {

    private final static String BOOKS_CHILD = "books";
    private final static String USERS_CHILD = "users";
    private BooksFilterAdapter booksAdapter = null;
    private static BooksSingleton ourInstance = null;
    DatabaseReference booksReference;

    public static BooksSingleton getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new BooksSingleton(context);
        }
        return ourInstance;
    }

    public BooksFilterAdapter getAdapter(){
        return booksAdapter;

    }

    private BooksSingleton(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        booksReference = database.getReference().child(BOOKS_CHILD);
    }
}
