package gameexange.com.gameexangeapp.controllers.activitites;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import gameexange.com.gameexangeapp.controllers.managers.VideojuegoCallback;
import gameexange.com.gameexangeapp.controllers.managers.VideojuegoManager;
import gameexange.com.gameexangeapp.controllers.services.VideojuegoService;
import gameexange.com.gameexangeapp.models.Videojuego;

/**
 * Created by JonGarcia on 29/05/2017.
 */

public class VideojuegosFilter extends Filter implements VideojuegoCallback{

    AutoCompleteVideojuegosAdapter adapter;
    List<Videojuego> videojuegos;
    List<Videojuego> videojuegosFiltrados;

    public VideojuegosFilter(AutoCompleteVideojuegosAdapter adapter, List<Videojuego> videojuegos) {
        super();
        this.adapter = adapter;
        this.videojuegos = videojuegos;
        this.videojuegosFiltrados = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        videojuegosFiltrados.clear();
        videojuegos.clear();

        final FilterResults results = new FilterResults();

        if(constraint == null || constraint.length() == 0) {
            videojuegosFiltrados.addAll(videojuegos);
        } else {
            VideojuegoManager.getInstance().busquedaProductos(VideojuegosFilter.this, constraint.toString().toLowerCase());

            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (final Videojuego videojuego : videojuegos) {
                if (videojuego.getNombre().toLowerCase().contains(filterPattern)){
                    videojuegosFiltrados.add(videojuego);
                }
            }

        }
        results.values = videojuegosFiltrados;
        results.count = videojuegosFiltrados.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.filteredVideojuegos.clear();
        adapter.filteredVideojuegos.addAll((List) results.values);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessVideojuegoList(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    @Override
    public void onSuccessVideojuego(Videojuego videojuego) {

    }

    @Override
    public void onFailure(Throwable t) {
        Videojuego nuevoVideojuego = new Videojuego();
        nuevoVideojuego.setId(7346L);
        nuevoVideojuego.setNombre("The Legend of Zelda: Breath of the Wild");
        nuevoVideojuego.setMiniatura("http://images.igdb.com/igdb/image/upload/t_thumb/jk9el4ksl4c7qwaex2y5.png");
        videojuegos.add(nuevoVideojuego);
        Videojuego nuevoVideojuego2 = new Videojuego();
        nuevoVideojuego2.setId(19459L);
        nuevoVideojuego2.setNombre("FIFA 17");
        nuevoVideojuego2.setMiniatura("http://images.igdb.com/igdb/image/upload/t_thumb/cmtplicvdajycqx2vz6t.png");
        videojuegos.add(nuevoVideojuego2);
    }
}
