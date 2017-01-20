package com.dreamfacilities.audiobooks;

import android.app.Application;

import java.util.Vector;

/**
 * Created by alex on 16/01/17.
 */

public class App extends Application {
    private Vector<Book> vectorLibros;
    private AdaptadorLibros adaptador;

    @Override
    public void onCreate() {
        vectorLibros = Book.ejemploLibros();
        adaptador = new AdaptadorLibros(this, vectorLibros);
    }


    public AdaptadorLibros getAdaptador() {
        return adaptador;
    }

    public Vector<Book> getVectorLibros() {
        return vectorLibros;
    }
}
