package com.dreamfacilities.audiobooks;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by alex on 21/01/17.
 */

public class BooksFilterAdapter extends BooksAdapter implements Observer {
    //private Vector<Book> booksNoFiltered;   // Vector con todos los libros
    private Vector<Integer> indexFilter;   // Índice en booksNoFiltered de
    private String search = "";           // Búsqueda sobre autor o título

    // Cada elemento de vectorBooks
    private String genre = "";             // Género seleccionado
    private boolean release = false;        // Si queremos ver solo novedades
    private boolean readed = false;          // Si queremos ver solo leidos
    private int lastFilterBook;             //Número libros del padre en último filtro

    public BooksFilterAdapter(Context contexto, DatabaseReference reference) {
        super(contexto, reference);

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
        indexFilter = new Vector<Integer>();
        lastFilterBook = super.getItemCount();
        for (int i = 0; i < lastFilterBook; i++) {

            Book book = super.getItem(i);

            if ((book.title.toLowerCase().contains(search) ||
                    book.autor.toLowerCase().contains(search))
                    && (book.genre.startsWith(genre))
                    && (!release || (release && book.release))
                    && (!readed || (readed /*&& book.readed*/))) {
                indexFilter.add(i);
            }
        }

    }

    public Book getItem(int position) {
        if (lastFilterBook != super.getItemCount()) {
            refreshFilter();
        }
        return super.getItem(indexFilter.elementAt(position));
    }

    public int getItemCount() {
        if (lastFilterBook != super.getItemCount()) {
            refreshFilter();
        }
        return indexFilter.size();
    }

    public long getItemId(int posicion) {

        return indexFilter.elementAt(posicion);
    }


    public Book getItemById(int id) {
        return super.getItem(id);
    }

    public void remove(int position) {
        DatabaseReference reference = getRef(indexFilter.elementAt(position));
        reference.removeValue();
        refreshFilter();
    }

    public void add(Book book) {
        booksReference.push().setValue(book);
        refreshFilter();
    }

    @Override
    public void update(Observable o, Object arg) {
        setSearch((String) arg);
        notifyDataSetChanged();
    }

    public String getItemKey(int posicion) {
        if (lastFilterBook != super.getItemCount()) {
            refreshFilter();
        }
        int id = indexFilter.elementAt(posicion);
        return super.getItemKey(id);
    }
}