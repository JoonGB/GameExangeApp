package gameexange.com.gameexangeapp.controllers.services;

import gameexange.com.gameexangeapp.models.User;
import gameexange.com.gameexangeapp.models.UserExt;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface RegistroService {

    @POST("api/register")
    Call<Void> registerAccount(
            @Body User user
    );

}
