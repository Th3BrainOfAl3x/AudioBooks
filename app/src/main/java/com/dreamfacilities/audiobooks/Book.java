package com.dreamfacilities.audiobooks;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class Book {

    public String title;
    public String autor;
    public int imageResource;
    public String urlImage;
    public String urlAudio;
    public String genre; // Género literario
    public Boolean release; // Es una release
    //public boolean readed; // Leído por el usuario
    private Map<String, Boolean> readed;
    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[]{G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE};
    public int colorVibrante = -1, colorApagado = -1;
    public final static Book LIBRO_EMPTY = new Book("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", R.drawable.books, "", G_TODOS, true);

    public Book(String title, String autor, String urlImage, int imageResource,
                String urlAudio, String genre, Boolean release) {
        this.title = title;
        this.autor = autor;
        this.imageResource = imageResource;
        this.urlImage = urlImage;
        this.urlAudio = urlAudio;
        this.genre = genre;
        this.release = release;
        this.readed = new HashMap<String, Boolean>();
    }

    public Book(){}

    public static Vector<Book> ejemploLibros() {

        final String SERVER = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Book> books = new Vector<Book>();
        books.add(new BookBuilder()
                .withTitle("Kappa")
                .withAutor("Akutagawa")
                .withUrlImage(SERVER + "kappa.jpg")
                .withImageResource(R.drawable.kappa)
                .withUrlAudio(SERVER + "kappa.mp3")
                .withGenre(Book.G_S_XIX)
                .isRelease(false)
                .build());
        books.add(new BookBuilder()
                .withTitle("Avecilla")
                .withAutor("Alas Clarín, Leopoldo")
                .withUrlImage(SERVER + "avecilla.jpg")
                .withImageResource(R.drawable.avecilla)
                .withUrlAudio(SERVER + "avecilla.mp3")
                .withGenre(Book.G_S_XIX)
                .isRelease(false)
                .build());
        books.add(new BookBuilder()
                .withTitle("Divina Comedia")
                .withAutor("Dante")
                .withUrlImage(SERVER + "divina_comedia.jpg")
                .withImageResource(R.drawable.divinacomedia)
                .withUrlAudio(SERVER + "divina_comedia.mp3")
                .withGenre(Book.G_EPICO)
                .isRelease(false)
                .build());
        books.add(new BookBuilder()
                .withTitle("Viejo Pancho, El")
                .withAutor("Alonso y Trelles, José")
                .withUrlImage(SERVER + "viejo_pancho.jpg")
                .withImageResource(R.drawable.viejo_pancho)
                .withUrlAudio(SERVER + "viejo_pancho.mp3")
                .withGenre(Book.G_S_XIX)
                .isRelease(true)
                .build());
        books.add(new BookBuilder()
                .withTitle("Canción de Rolando")
                .withAutor("Anónimo")
                .withUrlImage(SERVER + "cancion_rolando.jpg")
                .withImageResource(R.drawable.cancion_rolando)
                .withUrlAudio(SERVER + "cancion_rolando.mp3")
                .withGenre(Book.G_EPICO)
                .isRelease(true)
                .build());
        books.add(new BookBuilder()
                .withTitle("Matrimonio de sabuesos")
                .withAutor("Agata Christie")
                .withUrlImage(SERVER + "matrim_sabuesos.jpg")
                .withImageResource(R.drawable.matrimonio_sabuesos)
                .withUrlAudio(SERVER + "matrim_sabuesos.mp3")
                .withGenre(Book.G_SUSPENSE)
                .isRelease(true)
                .build());
        books.add(new BookBuilder()
                .withTitle("La iliada")
                .withAutor("Homero")
                .withUrlImage(SERVER + "la_iliada.jpg")
                .withImageResource(R.drawable.iliada)
                .withUrlAudio(SERVER + "la_iliada.mp3")
                .withGenre(Book.G_EPICO)
                .isRelease(false)
                .build());
        return books;
    }

    public boolean readedBy(String userID) {
        if (this.readed != null) {
            return this.readed.keySet().contains(userID);
        } else {
            return false;
        }
    }

    private static class BookBuilder {
        private String title = "";
        private String autor = "anónimo";
        private String urlImage =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg";
        private int imageResource = R.drawable.books;
        private String urlAudio = "";
        private String genre = G_TODOS;
        private boolean release = true;


        public BookBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder withAutor(String autor) {
            this.autor = autor;
            return this;
        }

        public BookBuilder withUrlImage(String urlImage) {
            this.urlImage = urlImage;
            return this;
        }

        public BookBuilder withUrlAudio(String urlAudio) {
            this.urlAudio = urlAudio;
            return this;
        }

        public BookBuilder withImageResource(int imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public BookBuilder isRelease(Boolean release) {
            this.release = release;
            return this;
        }

        public Book build() {
            return new Book(title, autor, urlImage, imageResource, urlAudio, genre, release);
        }

        public BookBuilder withGenre(String genre) {
            this.genre = genre;
            return this;
        }


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Boolean getRelease() {
        return release;
    }

    public void setRelease(Boolean release) {
        this.release = release;
    }

    public Map<String, Boolean> getReaded() {
        return readed;
    }

    public void setReaded(Map<String, Boolean> readed) {
        this.readed = readed;
    }
}

