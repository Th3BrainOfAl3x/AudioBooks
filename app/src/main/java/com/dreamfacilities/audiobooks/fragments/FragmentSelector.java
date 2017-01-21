package com.dreamfacilities.audiobooks.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private Vector<Book> vectorLibros;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            App app = (App) activity.getApplication();
            adapter = app.getAdaptador();
            vectorLibros = app.getVectorLibros();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflator.inflate(R.layout.fragment_selector, container, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
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
        return vista;

    }
}
