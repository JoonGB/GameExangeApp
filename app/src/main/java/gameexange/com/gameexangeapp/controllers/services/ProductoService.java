package gameexange.com.gameexangeapp.controllers.services;

import java.util.Date;
import java.util.List;

import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductoService {

    @GET("api/productos")
    Call<List<Producto>> getAllProductos(
            @Header("Authorization") String Authorization
    );

    @GET("/api/productos/byCategoria/{categoria}")
    Call<List<Producto>> getProductosByCategoria(
            @Header("Authorization") String Authorization,
            @Path("categoria") String categoria
    );

    @GET("/api/home/productos/like/{name}")
    Call<List<Producto>> getProductosByNombreContaining(
            @Header("Authorization") String Authorization,
            @Path("name") String name
    );

    @GET("/api/home/productos/precio/between/{pmin}/{pmax}")
    Call<List<Producto>> getProductosByPrecioBetween(
            @Header("Authorization") String Authorization,
            @Path("pmin") Double pmin, @Path("pmax") Double pmax
    );

    @GET("/api/home/productos/creado/between/{fmax}")
    Call<List<Producto>> getProductosByCreadoBetween(
            @Header("Authorization") String Authorization,
            @Path("fmax") Date fmax
    );

    @GET("/api/home/productos/{idproducto}/foto")
    Call<Foto> getFotoByProducto(
            @Header("Authorization") String Authorization,
            @Path("idproducto") Long idproducto
    );

    /*
    @POST("api/productos")
    Call<Producto> createProduct(
            @Header("Authorization") String Authorization,
            @Body Producto product

    );

    @PUT("api/productos")
    Call<Producto> updateProduct(

    );
    */
}
