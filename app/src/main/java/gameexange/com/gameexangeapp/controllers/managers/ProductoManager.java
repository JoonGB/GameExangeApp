package gameexange.com.gameexangeapp.controllers.managers;

import android.support.v7.util.SortedList;
import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.Map;

import gameexange.com.gameexangeapp.controllers.services.ProductoService;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.util.CustomProperties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoManager {
    private static ProductoManager ourInstance;
    private List<Producto> productos;
    private Producto producto;
    private Retrofit retrofit;
    private ProductoService productoService;
    private Map<Producto, Foto> fotosProductos;

    private ProductoManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        productoService = retrofit.create(ProductoService.class);
    }

    public static ProductoManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ProductoManager();
        }

        return ourInstance;
    }


    /* GET - GET ALL TEAMS */
    public synchronized void getAllProductos(final ProductoCallback productoCallback) {
        Call<List<Producto>> call = productoService.getAllProductos(LoginManager.getInstance().getBearerToken());

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                productos = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    productoCallback.onSuccess(productos);

                } else {
                    productoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ProductoManager->", t.toString());
                productoCallback.onFailure(t);
            }
        });
    }

    public synchronized void getProductosByCategoria(final ProductoCallback productoCallback, String categoria) {
        Call<List<Producto>> call = productoService.getProductosByCategoria(LoginManager.getInstance().getBearerToken(), categoria);

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                productos = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    productoCallback.onSuccess(productos);
                } else {
                    productoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ProductoManager->", t.toString());
                productoCallback.onFailure(t);
            }

        });
    }

    public synchronized void getProductosByNombreContaining(final ProductoCallback productoCallback, String name) {
        Call<List<Producto>> call = productoService.getProductosByNombreContaining(LoginManager.getInstance().getBearerToken(), name);

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                productos = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    productoCallback.onSuccess(productos);
                } else {
                    productoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ProductoManager->", t.toString());
                productoCallback.onFailure(t);
            }

        });
    }

    public synchronized void getProductosByPrecioBetween(final ProductoCallback productoCallback, Double pmin, Double pmax) {
        Call<List<Producto>> call = productoService.getProductosByPrecioBetween(LoginManager.getInstance().getBearerToken(), pmin, pmax);

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                productos = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    productoCallback.onSuccess(productos);
                } else {
                    productoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ProductoManager->", t.toString());
                productoCallback.onFailure(t);
            }

        });
    }

    public synchronized void getProductosByCreadoBetween(final ProductoCallback productoCallback, Date fmax) {
        Call<List<Producto>> call = productoService.getProductosByCreadoBetween(LoginManager.getInstance().getBearerToken(), fmax);

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                productos = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    productoCallback.onSuccess(productos);
                } else {
                    productoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ProductoManager->", t.toString());
                productoCallback.onFailure(t);
            }

        });
    }

    public synchronized void getAllProductosWithFoto(final ProductoCallback productoCallback) {
        Call<Map<Producto, Foto>> call = productoService.getAllProductosWithFoto(LoginManager.getInstance().getBearerToken());

        call.enqueue(new Callback<Map<Producto, Foto>>() {
            @Override
            public void onResponse(Call<Map<Producto, Foto>> call, Response<Map<Producto, Foto>> response) {
                fotosProductos = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    productoCallback.onSuccess(fotosProductos);

                } else {
                    productoCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Map<Producto, Foto>> call, Throwable t) {
                Log.e("ProductoManager->", t.toString());
                productoCallback.onFailure(t);
            }
        });
    }
}
