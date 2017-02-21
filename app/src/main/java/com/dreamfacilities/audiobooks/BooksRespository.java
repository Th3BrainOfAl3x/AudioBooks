package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 20/02/17.
 */

public class BooksRespository {
    private final BookStorage bookStorage;


    public BooksRespository(BookStorage bookStorage) { this.bookStorage = bookStorage; }

    public String getLastBook() { return bookStorage.getLastBook(); }

    public boolean hasLastBook() { return bookStorage.hasLastBook(); }

    public void saveLastBook(String key) { bookStorage.saveLastBook(key); }
}
