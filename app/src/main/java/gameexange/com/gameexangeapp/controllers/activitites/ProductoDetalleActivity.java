package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class ProductoDetalleActivity extends BaseDrawerActivity implements ProductoCallback {
    private ImageView imUsuario;
    private TextView tvUsuario;
    private ImageView imImagen;
    private TextView tvNombre;
    private TextView tvPrecio;
    private TextView tvDescripcion;
    private TextView tvFecha;
    private TextView tvVideojuego;

    LayoutInflater inflater;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) findViewById(R.id.content_layout);
        View view = inflater.inflate(R.layout.producto_detalle, linearLayout);

        imUsuario = (ImageView) view.findViewById(R.id.foto_usuario);
        tvUsuario = (TextView) view.findViewById(R.id.usuario);
        imImagen = (ImageView) view.findViewById(R.id.foto);
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
