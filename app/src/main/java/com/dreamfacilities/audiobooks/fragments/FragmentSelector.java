package com.dreamfacilities.audiobooks.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dreamfacilities.audiobooks.AdaptadorLibros;
import com.dreamfacilities.audiobooks.App;
import com.dreamfacilities.audiobooks.Book;
import com.dreamfacilities.audiobooks.MainActivity;
import com.dreamfacilities.audiobooks.R;

import java.util.Vector;

/**
 * Created by alex on 20/01/17.
 */

public class FragmentSelector extends Fragment {

    private Activity activity;
    private RecyclerView recyclerView;
    private AdaptadorLibros adapter;
    private Vector<Book> vectorBooks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            App app = (App) activity.getApplication();
            adapter = app.getAdaptador();
            vectorBooks = app.getVectorLibros();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.fragment_selector, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).showDetail(recyclerView.getChildAdapterPosition(v));
                Toast.makeText(activity, "Element " + recyclerView.getChildAdapterPosition(v)
                        + " was selected", Toast.LENGTH_SHORT).show();
            }
        });


        adapter.setOnItemLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(final View v) {

                        //Nota: Observa como la variable id tienen el modificador final.
                        // Si lo eliminas aparece el error: “Cannot refer to a non-final
                        // variable id inside an inner class defined in a differ- ent method”.
                        // Al ser id una variable local es eliminada cuando el método termina.
                        // Sin embargo, este método crea un objeto OnClickListener que permanecerá
                        // activo una vez el método haya terminado. Y desde este objeto accedemos a
                        // un parámetro del método. Para asegurarnos que la variable no es destruida
                        // al terminar el método utiliza el modificador final.

                        final int id = recyclerView.getChildAdapterPosition(v);

                        AlertDialog.Builder menu = new AlertDialog.Builder(activity);
                        CharSequence[] options = {"Share", "Remove ", "Add"};

                        menu.setItems(options, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int opcion) {
                                        switch (opcion) {
                                            case 0: //Compartir
                                                Book book = vectorBooks.elementAt(id);
                                                Intent i = new Intent(Intent.ACTION_SEND);
                                                i.setType("text/plain");
                                                i.putExtra(Intent.EXTRA_SUBJECT, book.title);
                                                i.putExtra(Intent.EXTRA_TEXT, book.urlAudio);
                                                startActivity(Intent.createChooser(i, "Share"));
                                                break;
                                            case 1: //Remove
                                                Snackbar.make(v, "¿Are you sure?", Snackbar.LENGTH_LONG)
                                                        .setAction("Yes", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                vectorBooks.remove(id);
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        }).show();

                                                break;
                                            case 2: //Add
                                                vectorBooks.add(vectorBooks.elementAt(id));
                                                adapter.notifyDataSetChanged();

                                                Snackbar.make(v,
                                                        "Book added",
                                                        Snackbar.LENGTH_INDEFINITE)
                                                        .setAction("Thanks!", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                            }
                                                        })
                                                        .show();
                                                break;
                                        }
                                    }
                                }

                        );
                        menu.create().

                                show();

                        return true;
                    }
                }

        );

        //Possibility to add new menu items from the fragment
        setHasOptionsMenu(true);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_last) {
            ((MainActivity) activity).goToLastListen();
            return true;
        } else if (id == R.id.menu_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
