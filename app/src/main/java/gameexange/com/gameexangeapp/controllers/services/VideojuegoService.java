package gameexange.com.gameexangeapp.controllers.services;

import java.util.Date;
import java.util.List;

import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.models.Videojuego;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface VideojuegoService {

    @GET("/api/videojuegos/busqueda/{busqueda}")
    Call<List<Videojuego>> busquedaVideojuegos(
            @Header("Authorization") String Authorization,
            @Path("busqueda") String busqueda
    );

}
