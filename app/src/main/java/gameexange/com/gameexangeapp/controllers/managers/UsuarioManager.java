package gameexange.com.gameexangeapp.controllers.managers;

import android.util.Log;

import java.util.Date;
import java.util.List;

import gameexange.com.gameexangeapp.controllers.services.ProductoService;
import gameexange.com.gameexangeapp.controllers.services.UsuarioService;
import gameexange.com.gameexangeapp.models.Foto;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.models.User;
import gameexange.com.gameexangeapp.models.UserExt;
import gameexange.com.gameexangeapp.util.CustomProperties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UsuarioManager {
    private Retrofit retrofit;
    private static UsuarioManager ourInstance;
    private UsuarioService usuarioService;

    private UsuarioManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        usuarioService = retrofit.create(UsuarioService.class);
    }

    public static UsuarioManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new UsuarioManager();
        }

        return ourInstance;
    }


    public synchronized void getUsuarioExtByLogin(final UsuarioCallback usuarioCallback){
        Call<UserExt> call = usuarioService.getUsuarioExtByLogin(LoginManager.getInstance().getBearerToken(), LoginManager.getInstance().getUsuario());

        call.enqueue(new Callback<UserExt>() {
            @Override
            public void onResponse(Call<UserExt> call, Response<UserExt> response) {
                Log.e("UserManager->", response.body().toString());
                UserExt userExt = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {

                    usuarioCallback.onSuccessUserExt(userExt);
                } else {
                    usuarioCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserExt> call, Throwable t) {
                Log.e("UserManager->", t.toString());
                usuarioCallback.onFailure(t);
            }
        });
    }
}
