package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 19/02/17.
 */

public class HasLastBook {
    private final BooksRespository booksRespository;

    public HasLastBook(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }
    public boolean execute() {
        return  booksRespository.hasLastBook();
    }
}
