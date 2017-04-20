package gameexange.com.gameexangeapp.models;

import java.util.HashSet;
import java.util.Set;

public class Conversacion {

    private Long id;
    private String creado;
    private Set<Mensaje> mensajes = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }
    public Conversacion creado(String creado) {
        this.creado = creado;
        return this;
    }
    public void setCreado(String creado) {
        this.creado = creado;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }
    public Conversacion mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }
    public Conversacion addMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        mensaje.setConversacion(this);
        return this;
    }
    public Conversacion removeMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje);
        mensaje.setConversacion(null);
        return this;
    }
    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }


    @Override
    public String toString() {
        return "Conversacion{" +
                "id=" + id +
                ", creado='" + creado + "'" +
                '}';
    }
}
