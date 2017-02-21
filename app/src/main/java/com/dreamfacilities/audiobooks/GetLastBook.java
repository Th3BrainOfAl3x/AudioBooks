package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 19/02/17.
 */

public class GetLastBook {
    private final BooksRespository booksRespository;

    public GetLastBook(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }
    public String execute() {
        return  booksRespository.getLastBook();
    }
}
