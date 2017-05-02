package gameexange.com.gameexangeapp.controllers.services;


import gameexange.com.gameexangeapp.models.UserToken;
import retrofit2.Call;
import retrofit2.http.*;

public interface TokenService {
    @POST("/oauth/token")
    @FormUrlEncoded
        //username=admin&password=admin&grant_type=password&scope=read%20write&client_secret=mySecretOAuthSecret&client_id=basketballapp
    Call<UserToken> requestToken(
            @Header("Authorization") String Authorization,
            @Field("username") String userName,
            @Field("password") String password,
            @Field("grant_type") String grantType,
            @Field("scope") String scope,
            @Field("client_secret") String client_secret,
            @Field("client_id") String client_id
    );
}