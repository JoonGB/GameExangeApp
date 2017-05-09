package gameexange.com.gameexangeapp.models;

import java.util.HashSet;
import java.util.Set;

public class Categoria  {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String descripcion;
    private Set<Videojuego> videojuegos = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public Categoria nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public Categoria descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Videojuego> getVideojuegos() {
        return videojuegos;
    }
    public Categoria videojuegos(Set<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
        return this;
    }
    public Categoria addVideojuego(Videojuego videojuego) {
        videojuegos.add(videojuego);
        videojuego.getCategorias().add(this);
        return this;
    }

    public Categoria removeVideojuego(Videojuego videojuego) {
        videojuegos.remove(videojuego);
        videojuego.getCategorias().remove(this);
        return this;
    }
    public void setVideojuegos(Set<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }



    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", descripcion='" + descripcion + "'" +
                '}';
    }
}
