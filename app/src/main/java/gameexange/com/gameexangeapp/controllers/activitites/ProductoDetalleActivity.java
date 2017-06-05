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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
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

    private Button btnNegociar;

    LayoutInflater inflater;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) findViewById(R.id.content_layout);

        Bundle extras = getIntent().getExtras();
        Long productoId = extras.getLong("producto");
        ProductoManager.getInstance().getProductoByIdDTO(ProductoDetalleActivity.this, productoId);
     }

    @Override
    public void onSuccessProducto(final Producto producto) {
        Log.e("ProductosActivity->", producto.toString());

        View view = inflater.inflate(R.layout.producto_detalle, linearLayout);

        imUsuario = (ImageView) view.findViewById(R.id.foto_usuario);
        tvUsuario = (TextView) view.findViewById(R.id.usuario);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        tvNombre = (TextView) view.findViewById(R.id.nombre);
        tvPrecio = (TextView) view.findViewById(R.id.precio);
        tvDescripcion = (TextView) view.findViewById(R.id.descripcion);
        tvFecha = (TextView) view.findViewById(R.id.fecha);
        tvVideojuego = (TextView) view.findViewById(R.id.videojuego);
        btnNegociar = (Button) view.findViewById(R.id.btnNegociar);

        if (producto.getUsuarioext() != null) {
            if (producto.getUsuarioext().getFoto() != null) {
                byte[] imageUsuarioAsBytes = Base64.decode(producto.getUsuarioext().getFoto(), Base64.DEFAULT);
                imUsuario.setImageBitmap(BitmapFactory.decodeByteArray(imageUsuarioAsBytes, 0, imageUsuarioAsBytes.length));
                imUsuario.setMaxWidth(80);
            }
        }
        tvUsuario.setText(producto.getUsuario().getLogin());

        FotoPagerAdapter fotoPagerAdapter = new FotoPagerAdapter(this, new ArrayList<>(producto.getFotos()));
        viewPager.setAdapter(fotoPagerAdapter);

        tvNombre.setText(producto.getNombre());
        tvPrecio.setText(String.valueOf(producto.getPrecio()) + " €");
        tvDescripcion.setText(producto.getDescripcion());


        tvFecha.setText(producto.getCreado());
        tvVideojuego.setText(producto.getVideojuego().toString());

        btnNegociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(ProductoDetalleActivity.this, ChatDetalleActivity.class);
                intent.putExtra("chatNuevo", true);
            intent.putExtra("producto", producto);
            startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        Intent intent = new Intent(ProductoDetalleActivity.this, ProductoListActivity.class);
        intent.putExtra("errorProducto", true);
        startActivity(intent);
    }

    @Override
    public void onSuccessProductosList(List<Producto> productos) {
    }

    @Override
    public void onSuccessCrearProducto() {

    }
    @Override
    public void onSuccessFotos(List<Foto> fotos) {

    }
    @Override
    public void onSuccessFotoPrincipal(Foto foto) {

    }
}
