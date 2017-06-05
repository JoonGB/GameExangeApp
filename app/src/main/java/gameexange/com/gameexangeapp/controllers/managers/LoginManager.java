package gameexange.com.gameexangeapp.controllers.managers;

import android.content.Context;
import android.util.Log;

import gameexange.com.gameexangeapp.models.UserExt;
import gameexange.com.gameexangeapp.models.UserToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginManager {
    private static LoginManager ourInstance;
    private UserToken userToken;
    private Context context;
    private String bearerToken;
    private String usuario;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if(ourInstance == null){
            ourInstance = new LoginManager();
        }

        return ourInstance;
    }

    public synchronized void performLogin(final String username, String password, final LoginCallback loginCallback){
        Call<UserToken> call =  TokenManager.getInstance().getUserToken(username, password);

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                Log.i("UserLoginManager ", " performtaks->call.enqueue->onResponse res: " + response.body());
                userToken = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {

                    bearerToken = "Bearer " + userToken.getAccessToken();
                    usuario = username;
                    loginCallback.onSuccess(userToken);
                } else {
                    loginCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                Log.e("UserLoginManager ", " performtaks->call.enqueue->onResponse err: " + t.toString());
                loginCallback.onFailure(t);
            }
        });
    }

    public UserToken getUserToken(){
        return userToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public String getUsuario(){
        return usuario;
    }


}