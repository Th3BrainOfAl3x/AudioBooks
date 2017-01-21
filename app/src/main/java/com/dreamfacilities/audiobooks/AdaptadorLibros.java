package com.dreamfacilities.audiobooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by alex on 20/01/17.
 *
 * ADAPTER: is a standard mechanism  in Android that allow us to
 *         create a serie of views to be showed in a container
 */

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {

    private LayoutInflater inflador;
    protected Vector<Book> vectorLibros;
    private Context contexto;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public AdaptadorLibros(Context contexto, Vector<Book> vectorLibros) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorLibros = vectorLibros;
        this.contexto = contexto;
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
        View v = inflador.inflate(R.layout.fragment_detail, null);

        // Set Listeners for the views
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);

        return new ViewHolder(v);
    }

    // Using the base viewholder set the different content views
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get content from the vector in the position
        Book libro = vectorLibros.elementAt(position);
        holder.cover.setImageResource(libro.imageResource);
        holder.title.setText(libro.title);
    }

    @Override
    public int getItemCount() {
        return vectorLibros.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }


}
