package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoDetalleActivity extends BaseDrawerActivity implements ProductoCallback {
    private ImageView imUsuario;
    private TextView tvUsuario;
    private ViewPager viewPager;
    private TextView tvNombre;
    private TextView tvPrecio;
    private TextView tvDescripcion;
    private TextView tvFecha;
    private TextView tvVideojuego;

    LayoutInflater inflater;
    LinearLayout linearLayout;
    // onsuccess VideojuegosFilter
    // primera vez que se ejecuta el backend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) findViewById(R.id.content_layout);
        View view = inflater.inflate(R.layout.producto_detalle, linearLayout);

        imUsuario = (ImageView) view.findViewById(R.id.foto_usuario);
        tvUsuario = (TextView) view.findViewById(R.id.usuario);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        tvNombre = (TextView) view.findViewById(R.id.nombre);
        tvPrecio = (TextView) view.findViewById(R.id.precio);
        tvDescripcion = (TextView) view.findViewById(R.id.descripcion);
        tvFecha = (TextView) view.findViewById(R.id.fecha);
        tvVideojuego = (TextView) view.findViewById(R.id.videojuego);

        Bundle extras = getIntent().getExtras();
        Long productoId = extras.getLong("producto");
        ProductoManager.getInstance().getProductoByIdDTO(ProductoDetalleActivity.this, productoId);
     }

    @Override
    public void onSuccessProducto(Producto producto) {
        Log.e("ProductosActivity->", producto.toString());

        if (producto.getUsuarioext().getFoto() != null) {
            byte[] imageUsuarioAsBytes = Base64.decode(producto.getUsuarioext().getFoto(), Base64.DEFAULT);
            imUsuario.setImageBitmap(BitmapFactory.decodeByteArray(imageUsuarioAsBytes, 0, imageUsuarioAsBytes.length));
            imUsuario.setMaxWidth(80);
        }
        tvUsuario.setText(producto.getUsuario().getLogin());

        FotoPagerAdapter fotoPagerAdapter = new FotoPagerAdapter(this, new ArrayList<>(producto.getFotos()));
        viewPager.setAdapter(fotoPagerAdapter);

        tvNombre.setText(producto.getNombre());
        tvPrecio.setText(String.valueOf(producto.getPrecio()) + " €");
        tvDescripcion.setText(producto.getDescripcion());


        tvFecha.setText(producto.getCreado());
        tvVideojuego.setText(producto.getVideojuego().getNombre());
    }

    @Override
    public void onFailure(Throwable t) {
        Intent intent = new Intent(ProductoDetalleActivity.this, ProductoListActivity.class);
        intent.putExtra("errorProducto", true);
        startActivity(intent);
    }


    private class FotoPagerAdapter extends PagerAdapter {

        private Context contextAdapter;
        private LayoutInflater inflaterAdapter;
        private List<Foto> fotosAdapter;

        public FotoPagerAdapter(Context context, List<Foto> fotos) {
            contextAdapter = context;
            inflaterAdapter = (LayoutInflater) contextAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            fotosAdapter = fotos;
        }

        @Override
        public int getCount() {
            return fotosAdapter.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = inflaterAdapter.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            byte[] imageProductoAsBytes  = Base64.decode(fotosAdapter.get(position).getFoto(), Base64.DEFAULT);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageProductoAsBytes, 0, imageProductoAsBytes.length));

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @Override
    public void onSuccessProductosList(List<Producto> productos) {

    }
    @Override
    public void onSuccessFotos(List<Foto> fotos) {

    }
    @Override
    public void onSuccessFotoPrincipal(Foto foto) {

    }
}
