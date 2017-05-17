package gameexange.com.gameexangeapp.controllers.activitites;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import gameexange.com.gameexangeapp.controllers.managers.ProductoCallback;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public class CrearProductoActivity extends AppCompatActivity implements ProductoCallback {
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
