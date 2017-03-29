package gameexange.com.gameexangeapp.model;

public class Mensaje {

    private Long id;
    private String creado;
    private String texto;
    private Conversacion conversacion;
    private User emisor;
    private User receptor;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }
    public Mensaje creado(String creado) {
        this.creado = creado;
        return this;
    }
    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getTexto() {
        return texto;
    }
    public Mensaje texto(String texto) {
        this.texto = texto;
        return this;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }
    public Mensaje conversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
        return this;
    }
    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }

    public User getEmisor() {
        return emisor;
    }
    public Mensaje emisor(User user) {
        this.emisor = user;
        return this;
    }
    public void setEmisor(User user) {
        this.emisor = user;
    }

    public User getReceptor() {
        return receptor;
    }
    public Mensaje receptor(User user) {
        this.receptor = user;
        return this;
    }
    public void setReceptor(User user) {
        this.receptor = user;
    }


    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", creado='" + creado + "'" +
                ", texto='" + texto + "'" +
                '}';
    }
}
