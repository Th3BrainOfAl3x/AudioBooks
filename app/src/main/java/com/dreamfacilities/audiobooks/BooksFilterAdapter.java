package com.dreamfacilities.audiobooks;

import android.content.Context;

import java.util.Vector;

/**
 * Created by alex on 21/01/17.
 */

public class BooksFilterAdapter extends BooksAdapter {
    private Vector<Book> booksNoFiltered;   // Vector con todos los libros
    private Vector<Integer> indexFilter;   // Índice en booksNoFiltered de
    private String search = "";           // Búsqueda sobre autor o título
    // Cada elemento de vectorBooks
    private String genre = "";             // Género seleccionado
    private boolean release = false;        // Si queremos ver solo novedades
    private boolean readed = false;          // Si queremos ver solo leidos

    public BooksFilterAdapter(Context context,
                              Vector<Book> vectorBooks) {
        super(context, vectorBooks);
        booksNoFiltered = vectorBooks;
        refreshFilter();
    }

    public void setSearch(String search) {
        this.search = search.toLowerCase();
        refreshFilter();
    }

    public void setGenre(String genre) {
        this.genre = genre;
        refreshFilter();
    }

    public void setRelease(boolean release) {
        this.release = release;
        refreshFilter();
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
        refreshFilter();
    }

    public void refreshFilter() {
        vectorBooks = new Vector<Book>();
        indexFilter = new Vector<Integer>();
        for (int i = 0; i < booksNoFiltered.size(); i++) {
            Book book = booksNoFiltered.elementAt(i);
            if ((book.title.toLowerCase().contains(search) ||
                    book.autor.toLowerCase().contains(search))
                    && (book.genre.startsWith(genre))
                    && (!release || (release && book.release))
                    && (!readed || (readed && book.readed))) {
                vectorBooks.add(book);
                indexFilter.add(i);
            }
        }

    }

    public Book getItem(int position) {
        return booksNoFiltered.elementAt(indexFilter.elementAt(position));
    }

    public long getItemId(int posicion) {
        return indexFilter.elementAt(posicion);
    }

    public void remove(int position) {
        booksNoFiltered.remove((int) getItemId(position));
        refreshFilter();
    }

    public void add(Book book) {
        booksNoFiltered.add(0,book);
        refreshFilter();
    }
}