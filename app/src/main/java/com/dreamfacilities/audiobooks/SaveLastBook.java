package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 19/02/17.
 */

public class SaveLastBook {

    private final BooksRespository booksRespository;

    public SaveLastBook(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }
    public void execute(String key) {
        booksRespository.saveLastBook(key);
    }
}
