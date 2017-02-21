package com.dreamfacilities.audiobooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dreamfacilities.audiobooks.interfaces.ClickAction;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.Vector;

/**
 * Created by alex on 21/02/17.
 */

public class BooksAdapterUI extends FirebaseRecyclerAdapter<Book, BooksAdapter.ViewHolder> {

    private LayoutInflater inflador;
    protected Vector<Book> vectorBooks;
    private Context context;
    protected DatabaseReference booksReference;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private ClickAction clickAction = new EmptyClickAction();

    public BooksAdapterUI(Context context, DatabaseReference reference) {
        super(Book.class, R.layout.element_selector,
                BooksAdapter.ViewHolder.class, reference);
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.booksReference = reference;
        this.context = context;
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
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate view via XML
        View v = inflador.inflate(R.layout.element_selector, null);

        // Set Listeners for the views
        v.setOnLongClickListener(onLongClickListener);

        return new BooksAdapter.ViewHolder(v);
    }

    // Using the base viewholder set the different content views
    @Override
    public void populateViewHolder(final BooksAdapter.ViewHolder holder, Book book, final int position) {
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
                //clickAction.execute(getItemKey(position));
            }
        });
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
