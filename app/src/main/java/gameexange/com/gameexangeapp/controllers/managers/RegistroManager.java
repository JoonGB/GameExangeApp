package gameexange.com.gameexangeapp.controllers.managers;

import gameexange.com.gameexangeapp.controllers.services.RegistroService;
import gameexange.com.gameexangeapp.models.User;
import gameexange.com.gameexangeapp.models.UserExt;
import gameexange.com.gameexangeapp.util.CustomProperties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroManager {
    private static RegistroManager ourInstance;
    private Retrofit retrofit;
    private RegistroService registerService;

    private RegistroManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registerService = retrofit.create(RegistroService.class);
    }

    public static RegistroManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new RegistroManager();
        }
        return ourInstance;
    }


    public synchronized void Register(final RegistroCallback registerCallback, User user) {
        Call<Void> call = registerService.registerAccount(user);
       // user.setEmail("aa@aabaa.com");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();

                if (code == 200 || code == 201) {
                    registerCallback.onSuccessRegister();

                } else {
                    registerCallback.onFailureRegister(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerCallback.onFailureRegister(t);
            }
        });
    }



}
