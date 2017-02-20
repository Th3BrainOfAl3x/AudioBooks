package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 19/02/17.
 */

public class MainController {
    BookStorage bookStorage;

    public MainController(BookStorage libroStorage) {
        this.bookStorage = libroStorage;
    }

    public void saveLastBook(int id) {
        bookStorage.saveLastBook(id);
    }

    public int getLastBook(){
        return bookStorage.getLastBook();
    }

    public boolean hasLastBook() {
        return bookStorage.hasLastBook();
    }
}
