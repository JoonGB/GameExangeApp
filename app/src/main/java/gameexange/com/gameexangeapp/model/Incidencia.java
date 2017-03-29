package gameexange.com.gameexangeapp.model;

public class Incidencia {

    private Long id;
    private String creado;
    private String descripcion;
    private String titulo;
    private User usuario;
    private Venta venta;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }
    public Incidencia creado(String creado) {
        this.creado = creado;
        return this;
    }
    public void setCreado(String creado) {
        this.creado = creado;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public Incidencia descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }
    public Incidencia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public User getUsuario() {
        return usuario;
    }
    public Incidencia usuario(User user) {
        this.usuario = user;
        return this;
    }
    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Venta getVenta() {
        return venta;
    }
    public Incidencia venta(Venta venta) {
        this.venta = venta;
        return this;
    }
    public void setVenta(Venta venta) {
        this.venta = venta;
    }


    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", creado='" + creado + "'" +
                ", descripcion='" + descripcion + "'" +
                ", titulo='" + titulo + "'" +
                '}';
    }
}
