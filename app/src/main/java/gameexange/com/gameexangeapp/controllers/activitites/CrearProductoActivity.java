package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.controllers.managers.ProductoManager;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;


public class CrearProductoActivity extends AppCompatActivity implements ProductoCallback {

    private final static int SELECT_PHOTO = 1;
    private final static int CAPTURE_PHOTO = 2;
    private final static int MY_CHILD_ACTIVITY = 3;

    private EditText etNombreProducto;
    private EditText etPrecioProducto;
    private EditText etDescripcionProducto;
    private Button btnEscogerVideojuego;
    private ViewPager viewPagerProducto;
    private Button btnSubirFoto;
    private Button btnHacerFoto;
    private Button btnCrearProducto;

    private Long videojuego;

    private TextView tvErrorFotoProducto;

    private List<Foto> fotoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        fotoList = new ArrayList<>();

        etNombreProducto = (EditText) findViewById(R.id.etNompreProducto);
        etPrecioProducto = (EditText) findViewById(R.id.etPrecioProducto);
        etDescripcionProducto = (EditText) findViewById(R.id.etDescripcionProducto);
        viewPagerProducto = (ViewPager) findViewById(R.id.viewPagerProducto);
        btnEscogerVideojuego = (Button) findViewById(R.id.btnEscogerVideojuego);
        btnSubirFoto = (Button) findViewById(R.id.btnSubirFoto);
        btnHacerFoto = (Button) findViewById(R.id.btnHacerFoto);
        btnCrearProducto = (Button) findViewById(R.id.btnCrearProducto);
        tvErrorFotoProducto = (TextView) findViewById(R.id.tv_error_fotos);


        btnEscogerVideojuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrearProductoActivity.this, BusquedaVideojuego.class);
                startActivityForResult(i, MY_CHILD_ACTIVITY);
            }
        });

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUploadFoto = new Intent(Intent.ACTION_GET_CONTENT);
                intentUploadFoto.setType("image/*");
                startActivityForResult(Intent.createChooser(intentUploadFoto, "Select Source"), SELECT_PHOTO);
            }
        });

        btnHacerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAPTURE_PHOTO);
                }
            }
        });

        btnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               crearProducto();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case (MY_CHILD_ACTIVITY): {
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    videojuego = Long.parseLong(extras.getString("videojuego"));
                    Log.e("CrearProductoActivity->", videojuego.toString());
                }
                break;
            }
            case (CAPTURE_PHOTO): {
                if (resultCode == RESULT_OK) {
                    Foto foto = new Foto();
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    foto.setFoto(Base64.encodeToString(byteArray, Base64.DEFAULT));
                    fotoList.add(foto);
                    FotoPagerAdapter fotoPagerAdapter = new FotoPagerAdapter(this, fotoList);
                    viewPagerProducto.setAdapter(fotoPagerAdapter);
                }
            }
            case (SELECT_PHOTO): {
                if (resultCode == RESULT_OK) {
                    Foto foto = new Foto();
                    Uri img = data.getData();
                    if (img != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(img);
                            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];

                            int len = 0;
                            while ((len = inputStream.read(buffer)) != -1) {
                                byteBuffer.write(buffer, 0, len);
                            }

                            byte[] inputData = byteBuffer.toByteArray();
                            foto.setFoto(Base64.encodeToString(inputData, Base64.DEFAULT));
                            fotoList.add(foto);
                        } catch (IOException e) {
                            Log.e("CrearProductoActivity->", e.toString());
                        }
                        FotoPagerAdapter fotoPagerAdapter = new FotoPagerAdapter(this, fotoList);
                        viewPagerProducto.setAdapter(fotoPagerAdapter);
                    }
                }
            }
        }
    }

    private void crearProducto() {

        etNombreProducto.setError(null);
        etPrecioProducto.setError(null);
        etDescripcionProducto.setError(null);
        tvErrorFotoProducto.setVisibility(View.INVISIBLE);

        boolean cancel = false;
        View focusView = null;

        String nombreProducto = etNombreProducto.getText().toString();
        String precioProducto = etPrecioProducto.getText().toString();
        String descripcionProducto = etDescripcionProducto.getText().toString();


        if (nombreProducto.equals("")) {
            etNombreProducto.setError(getString(R.string.error_nombre_producto));
            focusView = etNombreProducto;
            cancel = true;
        } else if (nombreProducto.length() < 4) {
            etNombreProducto.setError(getString(R.string.error_nombre_corto_producto));
            focusView = etNombreProducto;
            cancel = true;
        }

        if (descripcionProducto.equals("")) {
            etDescripcionProducto.setError(getString(R.string.error_descripcion_producto));
            focusView = etDescripcionProducto;
            cancel = true;
        } else if (descripcionProducto.length() < 10) {
            etDescripcionProducto.setError(getString(R.string.error_descripcion_corto_producto));
            focusView = etDescripcionProducto;
            cancel = true;
        }

        if (precioProducto.equals("")) {
            etPrecioProducto.setError(getString(R.string.error_precio_producto));
            focusView = etPrecioProducto;
            cancel = true;
        }
        try {
            double d = Double.parseDouble(precioProducto);
        } catch(NumberFormatException nfe) {
            etPrecioProducto.setError(getString(R.string.error_precio_formato_producto));
            focusView = etPrecioProducto;
            cancel = true;
        }

        if (fotoList.size() == 0) {
            tvErrorFotoProducto.setVisibility(View.VISIBLE);
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Producto producto = new Producto();
            producto.setNombre(etNombreProducto.getText().toString());
            producto.setDescripcion(etDescripcionProducto.getText().toString());
            producto.setPrecio(Double.parseDouble(etPrecioProducto.getText().toString()));
            producto.setVideojuego(1L);
            Set<Foto> set = new HashSet<>(fotoList);
            producto.setFotos(set);

            ProductoManager.getInstance().crearProducto(CrearProductoActivity.this, producto);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            videojuego = extras.getLong("videojuego");
        }
    }

    @Override
    public void onSuccessCrearProducto() {
        Intent i = new Intent(CrearProductoActivity.this, ProductoListActivity.class);
        i.putExtra("productoCreado", true);
        startActivity(i);
        Log.e("CrearProductoActivity->", "En teoría ha ido bien");
    }
    @Override
    public void onFailure(Throwable t) {
        Log.e("CrearProductoActivity->", t.toString());
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
}
