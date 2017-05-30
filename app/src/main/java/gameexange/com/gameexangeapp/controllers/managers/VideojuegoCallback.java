package gameexange.com.gameexangeapp.controllers.managers;


import java.util.List;

import gameexange.com.gameexangeapp.models.Videojuego;

public interface VideojuegoCallback {
    void onSuccessVideojuegoList(List<Videojuego> videojuegos);
    void onSuccessVideojuego(Videojuego videojuego);

    void onFailure(Throwable t);
}
