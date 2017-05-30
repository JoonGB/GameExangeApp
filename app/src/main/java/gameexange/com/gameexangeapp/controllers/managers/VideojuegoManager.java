package gameexange.com.gameexangeapp.controllers.managers;

import android.util.Log;

import java.util.Date;
import java.util.List;

import gameexange.com.gameexangeapp.controllers.services.ProductoService;
import gameexange.com.gameexangeapp.controllers.services.VideojuegoService;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.models.Videojuego;
import gameexange.com.gameexangeapp.util.CustomProperties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideojuegoManager {
    private static VideojuegoManager ourInstance;
    private List<Videojuego> videojuegos;
    private Videojuego videojuego;
    private Retrofit retrofit;
    private VideojuegoService videojuegoService;

    private VideojuegoManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        videojuegoService = retrofit. create(VideojuegoService.class);
    }

    public static VideojuegoManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new VideojuegoManager();
        }

        return ourInstance;
    }

    public synchronized void busquedaProductos (final VideojuegoCallback videojuegoCallback, String busqueda) {
        Call<List<Videojuego>> call = videojuegoService.busquedaVideojuegos(LoginManager.getInstance().getBearerToken(), busqueda);
        call.enqueue(new Callback<List<Videojuego>>() {
            @Override
            public void onResponse(Call<List<Videojuego>> call, Response<List<Videojuego>> response) {
                videojuegos = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    videojuegoCallback.onSuccessVideojuegoList(videojuegos);

                } else {
                    videojuegoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Videojuego>> call, Throwable t) {
                Log.e("VideojuegoManager->", t.toString());
                videojuegoCallback.onFailure(t);
            }
        });

    }
}
