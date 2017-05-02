package gameexange.com.gameexangeapp.controllers.managers;


public interface ProductoCallback {
    void onSuccess(Object product);
    void onFailure(Throwable t);
}
