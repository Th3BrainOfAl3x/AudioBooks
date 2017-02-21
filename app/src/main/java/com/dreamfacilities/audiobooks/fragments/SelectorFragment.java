package com.dreamfacilities.audiobooks.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.dreamfacilities.audiobooks.App;
import com.dreamfacilities.audiobooks.Book;
import com.dreamfacilities.audiobooks.BooksAdapter;
import com.dreamfacilities.audiobooks.BooksFilterAdapter;
import com.dreamfacilities.audiobooks.BooksSingleton;
import com.dreamfacilities.audiobooks.MainActivity;
import com.dreamfacilities.audiobooks.OpenDetailClickAction;
import com.dreamfacilities.audiobooks.R;
import com.dreamfacilities.audiobooks.SearchObservable;

import java.util.Vector;

/**
 * Created by alex on 20/01/17.
 */

public class SelectorFragment extends Fragment implements Animation.AnimationListener, Animator.AnimatorListener {

    private Activity activity;
    private RecyclerView recyclerView;

    private BooksFilterAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            App app = (App) activity.getApplication();
            adapter = BooksSingleton.getInstance(context).getAdapter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.fragment_selector, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setMoveDuration(1000);

        recyclerView.setItemAnimator(animator);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(adapter);
        adapter.setClickAction(new OpenDetailClickAction((MainActivity) getActivity()));


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
                                            case 0: //Share
                                                Animator anim = AnimatorInflater.loadAnimator(activity,
                                                        R.animator.menguar);
                                                anim.addListener(SelectorFragment.this);
                                                anim.setTarget(v);
                                                anim.start();
                                                Book book = adapter.getItemById(id);
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
                                                                Animation anim = AnimationUtils.loadAnimation(activity, R.anim.menguar);
                                                                anim.setAnimationListener(SelectorFragment.this);
                                                                v.startAnimation(anim);

                                                                adapter.remove(id);
                                                                //adapter.notifyDataSetChanged();
                                                            }
                                                        }).show();

                                                break;
                                            case 2: //Add
                                                int position = recyclerView.getChildLayoutPosition(v);
                                                adapter.add((Book) adapter.getItem(position));
                                                //adapter.notifyDataSetChanged();
                                                adapter.notifyItemInserted(adapter.getItemCount()+1);
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
                        menu.create().show();

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

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchObservable searchObservable = new SearchObservable();
        searchObservable.addObserver(adapter);
        searchView.setOnQueryTextListener(searchObservable);

        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setSearch("");
                        adapter.notifyDataSetChanged();
                        return true; // To closed
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                }
        );
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

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).showElements(true);
        adapter.activateBooksListener();
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
        adapter.desactivateBooksListener();
    }
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
