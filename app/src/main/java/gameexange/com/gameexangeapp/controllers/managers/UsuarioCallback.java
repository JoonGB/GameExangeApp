package gameexange.com.gameexangeapp.controllers.managers;

import gameexange.com.gameexangeapp.models.UserExt;

public interface UsuarioCallback {
    void onSuccessUserExt(UserExt userExt);
    void onFailure(Throwable t);
}
