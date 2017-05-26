package gameexange.com.gameexangeapp.controllers.managers;


import java.util.List;
import java.util.Set;

import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;

public interface ProductoCallback {
    void onSuccessProductosList(List<Producto> productos);
    void onSuccessProducto(Producto producto);
    void onSuccessFotos(List<Foto> fotos);
    void onSuccessFotoPrincipal(Foto foto);

    void onFailure(Throwable t);
}
