package com.dreamfacilities.audiobooks;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dreamfacilities.audiobooks.interfaces.ClickAction;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by alex on 20/01/17.
 * <p>
 * ADAPTER: is a standard mechanism  in Android that allow us to
 * create a serie of views to be showed in a container
 */

public class BooksAdapter
        extends RecyclerView.Adapter<BooksAdapter.ViewHolder> implements ChildEventListener {

    private LayoutInflater inflador;
    protected Vector<Book> vectorBooks;
    private Context context;
    protected DatabaseReference booksReference;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private ClickAction clickAction = new EmptyClickAction();
    private ArrayList<String> keys;
    private ArrayList<DataSnapshot> items;

    public BooksAdapter(Context context, DatabaseReference reference) {
        keys = new ArrayList<String>();
        items = new ArrayList<DataSnapshot>();
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.booksReference = reference;
        this.context = context;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        items.add(dataSnapshot);
        keys.add(dataSnapshot.getKey());
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            items.set(index, dataSnapshot);
            notifyItemChanged(index, dataSnapshot.getValue(Book.class));
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cover;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            cover.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    // Create the ViewHolder with the default views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate view via XML
        View v = inflador.inflate(R.layout.element_selector, null);

        // Set Listeners for the views
        v.setOnLongClickListener(onLongClickListener);

        return new ViewHolder(v);
    }

    public void activateBooksListener() {

        keys = new ArrayList<String>();
        items = new ArrayList<DataSnapshot>();

        FirebaseDatabase.getInstance().goOnline();
    }

    public void desactivateBooksListener() {

        FirebaseDatabase.getInstance().goOffline();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Using the base viewholder set the different content views
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Book book = getItem(position);

        // Get content from the vector in the position
        // Book book = vectorBooks.elementAt(position);

        App app = (App) context.getApplicationContext();
        VolleySingleton.getInstance(context).getImageLoader().get(book.urlImage,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response,
                                           boolean isImmediate) {
                        Bitmap bitmap = response.getBitmap();
                        holder.cover.setImageBitmap(bitmap);
                        //Extraemos el color principal de un bitmap

                        if (bitmap != null) {
                            Palette palette = Palette.from(bitmap).generate();
                            holder.itemView.setBackgroundColor(palette.getLightMutedColor(0));
                            holder.title.setBackgroundColor(palette.getLightVibrantColor(0));
                            //holder.cover.invalidate();
                            holder.cover.invalidate();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.cover.setImageResource(R.drawable.books);
                    }
                }

        );

        holder.title.setText(book.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction.execute(getItemKey(position));
            }
        });

    }

    public String getItemKey(int pos) {
        return keys.get(pos);
    }

    public Book getItemByKey(String key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return items.get(index).getValue(Book.class);
        } else {
            return null;
        }
    }

    public DatabaseReference getRef(int pos) {
        return items.get(pos).getRef();
    }

    public Book getItem(int pos) {
        return items.get(pos).getValue(Book.class);
    }
   /* @Override
    public int getItemCount() {
        return vectorBooks.size();
    }*/

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }


    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }
}
