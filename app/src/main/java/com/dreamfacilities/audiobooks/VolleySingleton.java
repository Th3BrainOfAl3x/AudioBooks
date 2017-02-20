package com.dreamfacilities.audiobooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Vector;

/**
 * Created by alex on 19/02/17.
 */
public class VolleySingleton {
    private static VolleySingleton ourInstance = null;
    private RequestQueue requestQueue = null;
    private ImageLoader imageLoader = null;

    public static VolleySingleton getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new VolleySingleton(context);
        }
        return ourInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;

    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) { cache.put(url, bitmap); }

                    public Bitmap getBitmap(String url) {  return cache.get(url);  }
                });
    }
}
