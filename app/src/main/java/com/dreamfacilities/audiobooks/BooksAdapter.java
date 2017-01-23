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

import java.util.Vector;

/**
 * Created by alex on 20/01/17.
 * <p>
 * ADAPTER: is a standard mechanism  in Android that allow us to
 * create a serie of views to be showed in a container
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private LayoutInflater inflador;
    protected Vector<Book> vectorBooks;
    private Context context;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public BooksAdapter(Context context, Vector<Book> vectorLibros) {
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorBooks = vectorLibros;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate view via XML
        View v = inflador.inflate(R.layout.element_selector, null);

        // Set Listeners for the views
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);

        return new ViewHolder(v);
    }

    // Using the base viewholder set the different content views
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get content from the vector in the position
        Book book = vectorBooks.elementAt(position);

        App app = (App) context.getApplicationContext();
        app.getImageLoader().get(book.urlImage,
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
    }

    @Override
    public int getItemCount() {
        return vectorBooks.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

}
