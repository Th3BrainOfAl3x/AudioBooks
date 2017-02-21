package com.dreamfacilities.audiobooks;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by alex on 16/01/17.
 */

public class App extends Application {
    private FirebaseAuth auth;
    private final static String BOOKS_CHILD = "books";
    private final static String USERS_CHILD = "users";
    private DatabaseReference usersReference;
    private DatabaseReference booksReference;


    @Override
    public void onCreate() {
        this.auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        booksReference = database.getReference().child(BOOKS_CHILD);
        usersReference = database.getReference().child(USERS_CHILD);
    }

    public FirebaseAuth getAuth() {
        return this.auth;
    }

    public DatabaseReference getUsersReference() {
        return usersReference;
    }


    //public BooksFilterAdapter getAdapter() {
    //  return adapter;
    //}

    //public Vector<Book> getVectorBooks() {
    //return vectorBooks;
    //}

    /*public static RequestQueue getRequestQueue() {
        return requestQueue;
    }*/

    /*public static ImageLoader getImageLoader() {
        return imageLoader;
    }*/
}
