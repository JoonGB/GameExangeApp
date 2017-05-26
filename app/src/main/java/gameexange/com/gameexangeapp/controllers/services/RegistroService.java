package gameexange.com.gameexangeapp.controllers.services;

import gameexange.com.gameexangeapp.models.UserExt;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface RegistroService {


    @POST ("/api/user-exts")
    Call<UserExt> registerAccount(
            @Body UserExt useEXT
            );

}
