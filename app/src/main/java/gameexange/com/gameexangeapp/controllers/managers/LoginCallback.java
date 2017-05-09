package gameexange.com.gameexangeapp.controllers.managers;

import gameexange.com.gameexangeapp.models.UserToken;

public interface LoginCallback {
    void onSuccess(UserToken userToken);
    void onFailure(Throwable t);
}
