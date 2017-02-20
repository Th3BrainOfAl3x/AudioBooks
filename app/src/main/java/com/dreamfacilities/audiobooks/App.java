package com.dreamfacilities.audiobooks;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alex on 16/01/17.
 */

public class App extends Application {
    private FirebaseAuth auth;

    @Override
    public void onCreate() {
        this.auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth(){
        return this.auth;
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
