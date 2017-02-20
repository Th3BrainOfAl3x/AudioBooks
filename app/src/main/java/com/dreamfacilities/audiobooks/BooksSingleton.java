package com.dreamfacilities.audiobooks;

import android.content.Context;

import java.util.Vector;

/**
 * Created by alex on 19/02/17.
 */
public class BooksSingleton {
    private BooksFilterAdapter booksAdapter = null;
    private Vector<Book> vectorBooks = null;
    private static BooksSingleton ourInstance = null;

    public static BooksSingleton getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new BooksSingleton(context);
        }
        return ourInstance;
    }

    public BooksFilterAdapter getAdapter(){
        return booksAdapter;

    }

    public Vector<Book> getBooks(){
        return vectorBooks;
    }

    private BooksSingleton(Context context) {
        vectorBooks = Book.ejemploLibros();
        booksAdapter = new BooksFilterAdapter(context,vectorBooks);
    }
}
