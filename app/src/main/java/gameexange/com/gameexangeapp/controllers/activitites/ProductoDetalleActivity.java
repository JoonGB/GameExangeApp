package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoDetalleActivity extends AppCompatActivity implements ProductoCallback {
    private ImageView imUsuario;
    private TextView tvUsuario;
    private ImageView imImagen;
    private TextView tvNombre;
    private TextView tvPrecio;
    private TextView tvDescripcion;
    private TextView tvFecha;
    private TextView tvVideojuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_detalle);

        imUsuario = (ImageView) findViewById(R.id.foto_usuario);
        tvUsuario = (TextView) findViewById(R.id.usuario);
        imImagen = (ImageView) findViewById(R.id.foto);
        tvNombre = (TextView) findViewById(R.id.nombre);
        tvPrecio = (TextView) findViewById(R.id.precio);
        tvDescripcion = (TextView) findViewById(R.id.descripcion);
        tvFecha = (TextView) findViewById(R.id.fecha);
        tvVideojuego = (TextView) findViewById(R.id.videojuego);

        Bundle extras = getIntent().getExtras();
        Long productoId = extras.getLong("producto");
        ProductoManager.getInstance().getProductoByIdDTO(ProductoDetalleActivity.this, productoId);
    }

    @Override
    public void onSuccessProducto(Producto producto) {
        Log.e("ProductosActivity->", producto.toString());

        byte[] imageUsuarioAsBytes  = Base64.decode(producto.getFotoPrincipal().getFoto(), Base64.DEFAULT);
        imUsuario.setImageBitmap(BitmapFactory.decodeByteArray(imageUsuarioAsBytes, 0, imageUsuarioAsBytes.length));
        imUsuario.setMaxWidth(80);
        tvUsuario.setText(producto.getUsuario().getLogin());
        byte[] imageProductoAsBytes  = Base64.decode(producto.getFotoPrincipal().getFoto(), Base64.DEFAULT);
        imImagen.setImageBitmap(BitmapFactory.decodeByteArray(imageProductoAsBytes, 0, imageProductoAsBytes.length));
        imImagen.setMaxWidth(80);
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
