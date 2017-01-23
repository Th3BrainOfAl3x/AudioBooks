package com.dreamfacilities.audiobooks;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Vector;

/**
 * Created by alex on 16/01/17.
 */

public class App extends Application {
    private Vector<Book> vectorBooks;
    private BooksFilterAdapter adapter;

    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    @Override
    public void onCreate() {
        vectorBooks = Book.ejemploLibros();
        adapter = new BooksFilterAdapter(this, vectorBooks);

        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) { cache.put(url, bitmap); }

                    public Bitmap getBitmap(String url) {  return cache.get(url);  }
                });
    }


    public BooksFilterAdapter getAdapter() {
        return adapter;
    }

    public Vector<Book> getVectorBooks() {
        return vectorBooks;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }
}
