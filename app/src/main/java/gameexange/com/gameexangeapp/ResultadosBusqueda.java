package gameexange.com.gameexangeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import gameexange.com.gameexangeapp.controllers.activitites.ProductoDetalleActivity;
import gameexange.com.gameexangeapp.controllers.activitites.ProductoListActivity;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.models.Videojuego;

public class ResultadosBusqueda extends AppCompatActivity implements ProductoCallback{

    RecyclerView recyclerView;
    ProductosAdapter adapter;

    String busquedaFiltros;
    Long videojuego;
    int precioMin;
    int precioMax;
    double latitud;
    double longitud;
    int orden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busqueda);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        Bundle extras = getIntent().getExtras();
        busquedaFiltros = extras.getString("busquedaFiltros");
        videojuego = extras.getLong("busquedaVideojuego");
        precioMin = extras.getInt("precioMin");
        precioMax = extras.getInt("precioMax");
        latitud = extras.getDouble("latitud");
        longitud = extras.getDouble("longitud");
        orden = extras.getInt("orden");


        ProductoManager.getInstance().getAllProductosDTO(this);


    }

    public List<Producto> aplicarFiltros(List<Producto> productos) {
        List<Producto> productosFiltrados = new ArrayList<>();
        for (Producto producto : productos) {
            boolean aceptado = true;
            if(busquedaFiltros != null && busquedaFiltros.replace(" ", "") == "") {
                if(!producto.getNombre().toLowerCase().contains(busquedaFiltros)){
                    aceptado = false;
                }
            }
            if (videojuego != 0) {
                if(producto.getVideojuego().longValue() != videojuego.longValue()){
                    aceptado = false;
                }
            }
            if (precioMin != 0) {
                if (producto.getPrecio() < precioMin) {
                    aceptado = false;
                }
            }
            if (precioMax != 0) {
                if (producto.getPrecio() > precioMax) {
                    aceptado = false;
                }
            }
            if (aceptado == true) {
                productosFiltrados.add(producto);
            }
        }
        if (orden == 1) {
            java.util.Collections.sort(productosFiltrados, new DistanceFromMeComparator(latitud, longitud));
        } else if (orden == 2) {
            java.util.Collections.sort(productosFiltrados, new Comparator<Producto>() {
                @Override
                public int compare(Producto p1, Producto p2) {
                    if (p1.getPrecio().compareTo(p2.getPrecio()) > 0) return 1;
                    if (p1.getPrecio().compareTo(p2.getPrecio()) < 0) return -1;
                    else return 0;
                }
            });
        } else if (orden == 3) {
            java.util.Collections.sort(productosFiltrados, new Comparator<Producto>() {
                @Override
                public int compare(Producto p1, Producto p2) {
                    if (p1.getPrecio().compareTo(p2.getPrecio()) > 0) return -1;
                    if (p1.getPrecio().compareTo(p2.getPrecio()) < 0) return 1;
                    else return 0;
                }
            });
        } else if (orden == 4) {
            java.util.Collections.sort(productosFiltrados, new Comparator<Producto>() {
                @Override
                public int compare(Producto p1, Producto p2) {
                    return p2.getCreado().compareTo(p1.getCreado());
                }
            });
        }
        return productosFiltrados;
    }

    class DistanceFromMeComparator implements Comparator<Producto> {
        double latitud;
        double longitud;

        public DistanceFromMeComparator(double latitud, double longitud) {
            this.latitud = latitud;
            this.longitud = longitud;
        }

        private Double distanceFromMe(Producto producto) {
            double theta = producto.getLongitud() - longitud;
            double dist = Math.sin(deg2rad(producto.getLatitud())) * Math.sin(deg2rad(latitud))
                    + Math.cos(deg2rad(producto.getLatitud())) * Math.cos(deg2rad(latitud))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            return dist;
        }

        private double deg2rad(double deg) { return (deg * Math.PI / 180.0); }
        private double rad2deg(double rad) { return (rad * 180.0 / Math.PI); }

        @Override
        public int compare(Producto o1, Producto o2) {
            return distanceFromMe(o1).compareTo(distanceFromMe(o2));
        }
    }


    @Override
    public void onSuccessProductosList(List<Producto> productos) {
        List<Producto> productosFiltrados = aplicarFiltros(productos);
        adapter = new ProductosAdapter(ResultadosBusqueda.this, productosFiltrados);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
    }

    @Override
    public void onSuccessProducto(Producto producto) {

    }

    @Override
    public void onSuccessFotos(List<Foto> fotos) {

    }

    @Override
    public void onSuccessFotoPrincipal(Foto foto) {

    }

    @Override
    public void onSuccessCrearProducto() {

    }

    @Override
    public void onFailure(Throwable t) {

    }

    class ProductosAdapter extends RecyclerView.Adapter<ProductoHolder> {
        private Context context;
        private List<Producto> productos;
        LayoutInflater inflater;

        public ProductosAdapter (Context context, List<Producto> productos) {
            this.context = context;
            this.productos = productos;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public ProductoHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = inflater.inflate(R.layout.producto_item, parent, false);
            ProductoHolder holder = new ProductoHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ProductoHolder holder, final int position) {
            holder.tvPrecio.setText(productos.get(position).getPrecio().toString()+"€");
            holder.tvNombre.setText(productos.get(position).getNombre());
            if (productos.get(position).getFotoPrincipal() != null) {
                String fotoprincipal = productos.get(position).getFotoPrincipal().getFoto();
                byte[] imageAsBytes = Base64.decode(fotoprincipal, Base64.DEFAULT);
                holder.ivProducto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            } else {
                holder.ivProducto.setImageResource(R.drawable.logo);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productos.get(position).getId() != null) {
                        Intent intent = new Intent(ResultadosBusqueda.this, ProductoDetalleActivity.class);
                        intent.putExtra("producto", productos.get(position).getId());
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return productos.size();
        }
    }


    class ProductoHolder extends RecyclerView.ViewHolder{
        ImageView ivProducto;
        TextView tvNombre;
        TextView tvPrecio;

        public ProductoHolder(View itemView) {
            super(itemView);
            ivProducto = (ImageView) itemView.findViewById(R.id.ivProducto);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
        }
    }

}

