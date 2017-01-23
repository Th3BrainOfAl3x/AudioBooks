package com.dreamfacilities.audiobooks;

import java.util.Vector;


public class Book {

    public String title;
    public String autor;
    //public int imageResource;
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

    public Book(String title, String autor, String urlImage,
                String urlAudio, String genre, Boolean release, Boolean readed) {
        this.title = title;
        this.autor = autor;
        this.urlImage = urlImage;
        this.urlAudio = urlAudio;
        this.genre = genre;
        this.release = release;
        this.readed = readed;
    }

    public static Vector<Book> ejemploLibros() {
        final String SERVER = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Book> books = new Vector<Book>();
        books.add(new Book("Kappa", "Akutagawa",
                SERVER+"kappa.jpg", SERVER + "kappa.mp3",
                Book.G_S_XIX, false, false));
        books.add(new Book("Avecilla", "Alas Clarín, Leopoldo",
                SERVER + "avecilla.jpg", SERVER + "avecilla.mp3",
                Book.G_S_XIX, true, false));

        books.add(new Book("Divina Comedia", "Dante",
                SERVER + "divina_comedia.jpg", SERVER + "divina_comedia.mp3",
                Book.G_EPICO, true, false));
        books.add(new Book("Viejo Pancho, El", "Alonso y Trelles, José",
                SERVER + "viejo_pancho.jpg", SERVER + "viejo_pancho.mp3",
                Book.G_S_XIX, true, true));
        books.add(new Book("Canción de Rolando", "Anónimo",
                SERVER + "cancion_rolando.jpg", SERVER + "cancion_rolando.mp3",
                Book.G_EPICO, false, true));
        books.add(new Book("Matrimonio de sabuesos", "Agata Christie",
                SERVER + "matrim_sabuesos.jpg", SERVER + "matrim_sabuesos.mp3",
                Book.G_SUSPENSE, false, true));
        books.add(new Book("La iliada", "Homero",
                SERVER + "la_iliada.jpg", SERVER + "la_iliada.mp3",
                Book.G_EPICO, true, false));
        return books;
    }
}

