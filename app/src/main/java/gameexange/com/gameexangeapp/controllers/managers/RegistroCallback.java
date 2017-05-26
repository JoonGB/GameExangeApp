package gameexange.com.gameexangeapp.controllers.managers;

/**
 * Created by DAM on 24/5/17.
 */

public interface RegistroCallback {

    void onSuccessRegister();
    void onFailureRegister(Throwable t);

}
