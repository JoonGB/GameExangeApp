package gameexange.com.gameexangeapp.controllers.managers;

import android.util.Log;

import com.google.android.gms.cast.framework.media.MediaNotificationService;

import java.util.List;

import gameexange.com.gameexangeapp.controllers.services.ChatService;
import gameexange.com.gameexangeapp.models.Conversacion;
import gameexange.com.gameexangeapp.models.Mensaje;
import gameexange.com.gameexangeapp.models.Producto;
import gameexange.com.gameexangeapp.util.CustomProperties;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatManager {
    private static ChatManager ourInstance;
    private List<Conversacion> conversacionList;
    private List<Mensaje> mensajeList;
    private Retrofit retrofit;
    private ChatService chatService;

    private ChatManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatService = retrofit. create(ChatService.class);
    }

    public static ChatManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ChatManager();
        }

        return ourInstance;
    }

    public synchronized void getAllUserChats(final ChatCallback chatCallback) {
        String usuario = LoginManager.getInstance().getUsuario();

        Call<List<Conversacion>> call = chatService.getAllUserChats(LoginManager.getInstance().getBearerToken(), usuario);

        call.enqueue(new Callback<List<Conversacion>>() {
            @Override
            public void onResponse(Call<List<Conversacion>> call, Response<List<Conversacion>> response) {
                conversacionList = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    chatCallback.onSuccessChatList(conversacionList);

                } else {
                    chatCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Conversacion>> call, Throwable t) {
                Log.e("ChatManager->", t.toString());
                chatCallback.onFailure(t);
            }
        });
    }

    public synchronized void getAllChatMensajes(final ChatCallback chatCallback, Conversacion conversacion) {
        Call<List<Mensaje>> call = chatService.getAllChatMensajes(LoginManager.getInstance().getBearerToken(), conversacion);

        call.enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                mensajeList = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    chatCallback.onSuccessMensajesList(mensajeList);

                } else {
                    chatCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Log.e("ChatManager->", t.toString());
                chatCallback.onFailure(t);
            }
        });
    }

    public synchronized void crearNuevoChat(final ChatCallback chatCallback, Conversacion conversacion) {
        Call<Conversacion> call = chatService.crearNuevoChat(LoginManager.getInstance().getBearerToken(), conversacion);

        call.enqueue(new Callback<Conversacion>() {
            @Override
            public void onResponse(Call<Conversacion> call, Response<Conversacion> response) {
                Conversacion conversacion = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    chatCallback.onSuccessChat(conversacion);

                } else {
                    chatCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Conversacion> call, Throwable t) {
                Log.e("ChatManager->", t.toString());
                chatCallback.onFailure(t);
            }
        });
    }

    public synchronized void enviarMensaje(final ChatCallback chatCallback, Mensaje mensaje) {
        Call<Mensaje> call = chatService.enviarMensaje(LoginManager.getInstance().getBearerToken(), mensaje);

        call.enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                int code = response.code();

                if (code == 200 || code == 201) {
                    chatCallback.onSuccessEnviarMensaje();

                } else {
                    chatCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                Log.e("ChatManager->", t.toString());
                chatCallback.onFailure(t);
            }
        });
    }
}

