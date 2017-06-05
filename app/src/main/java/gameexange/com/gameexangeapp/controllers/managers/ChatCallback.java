package gameexange.com.gameexangeapp.controllers.managers;

import java.util.List;

import gameexange.com.gameexangeapp.models.Conversacion;
import gameexange.com.gameexangeapp.models.Mensaje;

public interface ChatCallback {
    void onSuccessChatList(List<Conversacion> conversacionList);
    void onSuccessMensajesList(List<Mensaje> mensajeList);
    void onSuccessChat(Conversacion conversacion);
    void onSuccessEnviarMensaje();
    void onFailure(Throwable t);
}
