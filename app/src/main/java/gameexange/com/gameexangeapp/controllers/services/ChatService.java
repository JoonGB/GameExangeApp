package gameexange.com.gameexangeapp.controllers.services;

import java.util.List;

import gameexange.com.gameexangeapp.models.Conversacion;
import gameexange.com.gameexangeapp.models.Mensaje;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatService {
    @GET("api/chats/user/{user}")
    Call<List<Conversacion>> getAllUserChats(
            @Header("Authorization") String Authorization,
            @Path("user") String usuario
    );

    @POST("api/chats/mensajes")
    Call<List<Mensaje>> getAllChatMensajes(
            @Header("Authorization") String Authorization,
            @Body Conversacion conversacion
    );
    @GET("api/chats/mensajes/{idchat}")
    Call<List<Mensaje>> getAllChatMensajesById(
            @Header("Authorization") String Authorization,
            @Path("idchat") Long idchat
    );

    @POST("api/chats/nuevo")
    Call<Conversacion> crearNuevoChat(
            @Header("Authorization") String Authorization,
            @Body Conversacion conversacion
    );

    @POST("api/chats/crearmensaje")
    Call<Mensaje> enviarMensaje(
            @Header("Authorization") String Authorization,
            @Body Mensaje mensaje
    );
}
