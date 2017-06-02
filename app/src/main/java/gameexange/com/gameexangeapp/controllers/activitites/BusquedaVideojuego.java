package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.VideojuegoCallback;
import gameexange.com.gameexangeapp.controllers.managers.VideojuegoManager;
import gameexange.com.gameexangeapp.models.Videojuego;

public class BusquedaVideojuego extends AppCompatActivity implements VideojuegoCallback {
    private EditText etBusqueda;
    private Button btnBusqueda;
    private ListView lvBusqueda;

    private String busqueda;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_videojuego);
        toolbar = (Toolbar) findViewById(R.id.tbBusqueda);

        etBusqueda = (EditText) (findViewById(R.id.etBusqueda));
        btnBusqueda = (Button) (findViewById(R.id.btnBusqueda));
        lvBusqueda = (ListView) (findViewById(R.id.lvBusqueda));

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busqueda = etBusqueda.getText().toString().trim();
                busqueda = busqueda.replace(" ", "+");
                if(busqueda.length()>2) {
                    VideojuegoManager.getInstance().busquedaProductos(BusquedaVideojuego.this, busqueda);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.busqueda_videojuego_bar, menu);
        return true;
        /*inflater.inflate(R.menu.videojuego_search_bar, menu);
        MenuItem item = menu.findItem(R.id.etBusqueda);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String busqueda) {
                VideojuegoManager.getInstance().busquedaProductos(BusquedaVideojuego.this, busqueda);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String busqueda) {
                VideojuegoManager.getInstance().busquedaProductos(BusquedaVideojuego.this, busqueda);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);*/
    }

    @Override
    public void onSuccessVideojuegoList(List<Videojuego> videojuegos) {
        List<Videojuego> videojuegosFiltrados = new ArrayList<>();
        for(Videojuego videojuego : videojuegos) {
            if(videojuego.getNombre().toLowerCase().contains(busqueda)){
                videojuegosFiltrados.add(videojuego);
            }
        }
        VideojuegoListAdapter adapter = new VideojuegoListAdapter(this, videojuegos);
        lvBusqueda.setAdapter(adapter);
        lvBusqueda.deferNotifyDataSetChanged();
    }

    @Override
    public void onSuccessVideojuego(Videojuego videojuego) {

    }

    @Override
    public void onFailure(Throwable t) {
        List<Videojuego> videojuegos = new ArrayList<>();
        videojuegos = new ArrayList<>();
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
        VideojuegoListAdapter adapter = new VideojuegoListAdapter(this, videojuegos);
        lvBusqueda.setAdapter(adapter);
    }


    public class VideojuegoListAdapter extends BaseAdapter {

        private Context context;
        private List<Videojuego> videojuegos;

        public VideojuegoListAdapter(Context context, List<Videojuego> videojuegos){
            this.context = context;
            this.videojuegos = videojuegos;
        }

        @Override
        public int getCount() { return videojuegos.size(); }
        @Override
        public Object getItem(int i) { return videojuegos.get(i); }
        @Override
        public long getItemId(int i) { return videojuegos.get(i).getId(); }

        public class ViewHolder {
            public TextView tvNombreVideojuego;
            public ImageView ivImagenVideojuego;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            if (myView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                myView = inflater.inflate(R.layout.buscador_videojuego_item, parent, false);

                ViewHolder holder = new ViewHolder();
                holder.ivImagenVideojuego = (ImageView) myView.findViewById(R.id.imagenVideojuego);
                holder.tvNombreVideojuego = (TextView) myView.findViewById(R.id.nombreVideojuego);

                myView.setTag(holder);
            }
            ViewHolder holder = (ViewHolder) myView.getTag();

            Videojuego videojuego = videojuegos.get(position);
            String nombre = videojuego.getNombre();
            holder.tvNombreVideojuego.setText(nombre);

            String miniatura = videojuego.getMiniatura();
            if(!videojuego.getMiniatura().equals("")){
                Picasso.with(context).load(miniatura).into(holder.ivImagenVideojuego);
            }
            return myView;
        }
    }

}

