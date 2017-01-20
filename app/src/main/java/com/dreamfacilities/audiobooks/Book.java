package com.dreamfacilities.audiobooks;

import java.util.Vector;

/**
 * Created by alex on 18/01/17.
 */

public class Book {

    public String title;
    public String autor;
    public int imageResource;
    public String urlAudio;
    public String genero; // Género literario
    public Boolean novedad; // Es una novedad
    public Boolean leido; // Leído por el usuario
    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[]{G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE};

    public Book(String title, String autor, int imageResource,
                String urlAudio, String genero, Boolean novedad, Boolean leido) {
        this.title = title;
        this.autor = autor;
        this.imageResource = imageResource;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        this.leido = leido;
    }

    public static Vector<Book> ejemploLibros() {
        final String SERVIDOR = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Book> libros = new Vector<Book>();
        libros.add(new Book("Kappa", "Akutagawa",
                R.drawable.kappa, SERVIDOR + "kappa.mp3",
                Book.G_S_XIX, false, false));
        libros.add(new Book("Avecilla", "Alas Clarín, Leopoldo",
                R.drawable.avecilla, SERVIDOR + "avecilla.mp3",
                Book.G_S_XIX, true, false));

        libros.add(new Book("Divina Comedia", "Dante",
                R.drawable.divinacomedia, SERVIDOR + "divina_comedia.mp3",
                Book.G_EPICO, true, false));
        libros.add(new Book("Viejo Pancho, El", "Alonso y Trelles, José",
                R.drawable.viejo_pancho, SERVIDOR + "viejo_pancho.mp3",
                Book.G_S_XIX, true, true));
        libros.add(new Book("Canción de Rolando", "Anónimo",
                R.drawable.cancion_rolando, SERVIDOR + "cancion_rolando.mp3",
                Book.G_EPICO, false, true));
        libros.add(new Book("Matrimonio de sabuesos", "Agata Christie",
                R.drawable.matrimonio_sabuesos, SERVIDOR + "matrim_sabuesos.mp3",
                Book.G_SUSPENSE, false, true));
        libros.add(new Book("La iliada", "Homero",
                R.drawable.iliada, SERVIDOR + "la_iliada.mp3",
                Book.G_EPICO, true, false));
        return libros;
    }
}

