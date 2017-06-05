package gameexange.com.gameexangeapp.controllers.services;

import gameexange.com.gameexangeapp.models.UserExt;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("api/usuariosget/userext/{login}")
    Call<UserExt> getUsuarioExtByLogin(
            @Header("Authorization") String Authorization,
            @Path("login") String login
    );
}
