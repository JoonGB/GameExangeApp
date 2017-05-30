package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.media.Image;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.models.Videojuego;

/**
 * Created by JonGarcia on 29/05/2017.
 */

public class AutoCompleteVideojuegosAdapter extends ArrayAdapter<Videojuego> {

    private final List<Videojuego> videojuegos;
    public List<Videojuego> filteredVideojuegos = new ArrayList<>();

    public AutoCompleteVideojuegosAdapter (Context context, List<Videojuego> videojuegos){
        super(context, 0, videojuegos);
        this.videojuegos = videojuegos;
    }

    @Override
    public int getCount() {
        return filteredVideojuegos.size();
    }


    @Override
    public Filter getFilter() {
        return new VideojuegosFilter(this, videojuegos);
    }

    public class ViewHolder {
        public TextView tvNombreVideojuego;
        public ImageView ivImagenVideojuego;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        if (myView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            myView = inflater.inflate(R.layout.buscador_videojuego_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.ivImagenVideojuego = (ImageView) myView.findViewById(R.id.imagenVideojuego);
            holder.tvNombreVideojuego = (TextView) myView.findViewById(R.id.nombreVideojuego);

            myView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) myView.getTag();

        Videojuego videojuego = filteredVideojuegos.get(position);
        String nombre = videojuego.getNombre();
        holder.tvNombreVideojuego.setText(nombre);

        String miniatura = videojuego.getMiniatura();
        if(!videojuego.getMiniatura().equals("")){
            Picasso.with(this.getContext()).load(miniatura).into(holder.ivImagenVideojuego);
        }
        return myView;

    }
}
