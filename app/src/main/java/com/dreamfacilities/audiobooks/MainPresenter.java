package com.dreamfacilities.audiobooks;

/**
 * Created by alex on 19/02/17.
 */

public class MainPresenter {
    private final View view;
    private BooksRespository booksRespository;
    private SaveLastBook saveLastBook;
    private GetLastBook getLastBook;
    private HasLastBook hasLastBook;

    public MainPresenter(BooksRespository booksRespository, MainPresenter.View view) {
        this.booksRespository = booksRespository;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (this.booksRespository.hasLastBook()) {
            view.showDetail(this.booksRespository.getLastBook());
        } else {
            view.showNotLastView();
        }
    }

    public void openDetail(int id) {
        this.booksRespository.saveLastBook(id);
        view.showDetail(id);
    }

    public interface View {
        void showDetail(int lastBook);

        void showNotLastView();
    }
}
