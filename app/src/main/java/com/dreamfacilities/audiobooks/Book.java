package com.dreamfacilities.audiobooks;

import java.util.Vector;


public class Book {

    public String title;
    public String autor;
    public int imageResource;
    public String urlImage;
    public String urlAudio;
    public String genre; // Género literario
    public Boolean release; // Es una release
    public Boolean readed; // Leído por el usuario
    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[]{G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE};
    public int colorVibrante = -1, colorApagado = -1;
    public final static Book LIBRO_EMPTY = new Book("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", R.drawable.books, "", G_TODOS, true, false);

    public Book(String title, String autor, String urlImage, int imageResource,
                String urlAudio, String genre, Boolean release, Boolean readed) {
        this.title = title;
        this.autor = autor;
        this.imageResource = imageResource;
        this.urlImage = urlImage;
        this.urlAudio = urlAudio;
        this.genre = genre;
        this.release = release;
        this.readed = readed;
    }

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
                .isReaded(false)
                .isRelease(false)
                .build());
        books.add(new Book("Avecilla", "Alas Clarín, Leopoldo",
                SERVER + "avecilla.jpg", R.drawable.avecilla, SERVER + "avecilla.mp3",
                Book.G_S_XIX, true, false));
        books.add(new Book("Divina Comedia", "Dante",
                SERVER + "divina_comedia.jpg", R.drawable.divinacomedia, SERVER + "divina_comedia.mp3",
                Book.G_EPICO, true, false));
        books.add(new Book("Viejo Pancho, El", "Alonso y Trelles, José",
                SERVER + "viejo_pancho.jpg", R.drawable.viejo_pancho, SERVER + "viejo_pancho.mp3",
                Book.G_S_XIX, true, true));
        books.add(new Book("Canción de Rolando", "Anónimo",
                SERVER + "cancion_rolando.jpg", R.drawable.cancion_rolando, SERVER + "cancion_rolando.mp3",
                Book.G_EPICO, false, true));
        books.add(new Book("Matrimonio de sabuesos", "Agata Christie",
                SERVER + "matrim_sabuesos.jpg", R.drawable.matrimonio_sabuesos, SERVER + "matrim_sabuesos.mp3",
                Book.G_SUSPENSE, false, true));
        books.add(new Book("La iliada", "Homero",
                SERVER + "la_iliada.jpg", R.drawable.iliada, SERVER + "la_iliada.mp3",
                Book.G_EPICO, true, false));
        return books;
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
        private boolean readed = false;

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

        public BookBuilder isReaded(Boolean readed) {
            this.readed = readed;
            return this;
        }

        public Book build() {
            return new Book(title, autor, urlImage, imageResource, urlAudio, genre, release, readed);
        }

        public BookBuilder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

    }
}

