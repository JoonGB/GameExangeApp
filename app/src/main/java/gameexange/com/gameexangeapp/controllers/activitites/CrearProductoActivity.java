package gameexange.com.gameexangeapp.controllers.activitites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class CrearProductoActivity extends AppCompatActivity implements ProductoCallback {

    private EditText etNombreProducto;
    private EditText etPrecioProducto;
    private EditText etDescripcionProducto;
    private Button btnSubirFoto;
    private Button btnHacerFoto;
    private Button btnCrearProducto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        etNombreProducto = (EditText) findViewById(R.id.etNompreProducto);
        etPrecioProducto = (EditText) findViewById(R.id.etPrecioProducto);
        etDescripcionProducto = (EditText) findViewById(R.id.etDescripcionProducto);
        btnSubirFoto = (Button) findViewById(R.id.btnSubirFoto);
        btnHacerFoto = (Button) findViewById(R.id.btnHacerFoto);
        btnCrearProducto = (Button) findViewById(R.id.btnCrearProducto);

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CrearProductoActivity.this, "Subir foto de la galería", Toast.LENGTH_SHORT).show();
            }
        });

        btnHacerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CrearProductoActivity.this, "Hacer una nueva foto", Toast.LENGTH_SHORT).show();
            }
        });

        btnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CrearProductoActivity.this, "Crear el producto", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onSuccessProductosList(List<Producto> productos) {
        
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
    public void onFailure(Throwable t) {

    }
}
