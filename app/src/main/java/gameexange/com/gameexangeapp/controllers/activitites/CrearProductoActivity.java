package gameexange.com.gameexangeapp.controllers.activitites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import gameexange.com.gameexangeapp.R;
import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

import static android.R.attr.type;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class CrearProductoActivity extends AppCompatActivity implements ProductoCallback {

    private final static int SELECT_PHOTO = 1;
    private final static int CAPTURE_PHOTO = 2;

    private EditText etNombreProducto;
    private EditText etPrecioProducto;
    private EditText etDescripcionProducto;
    private ImageView mImageView;
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
        mImageView = (ImageView) findViewById(R.id.mImageView);
        btnSubirFoto = (Button) findViewById(R.id.btnSubirFoto);
        btnHacerFoto = (Button) findViewById(R.id.btnHacerFoto);
        btnCrearProducto = (Button) findViewById(R.id.btnCrearProducto);

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
                Toast.makeText(CrearProductoActivity.this, "Crear el producto", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }


        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Uri img = data.getData();
            if (img != null) {
                mImageView.setImageURI(img);
            }
        }

        mImageView.invalidate();
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
