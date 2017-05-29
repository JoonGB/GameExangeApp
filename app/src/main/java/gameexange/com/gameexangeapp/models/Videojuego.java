package gameexange.com.gameexangeapp.models;

import java.util.HashSet;
import java.util.Set;

public class Videojuego {

    private Long id;
    private String nombre;
    private String miniatura;
    private String caratula;
    private Set<Categoria> categorias = new HashSet<>();
    private Set<Producto> productos = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public Videojuego nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }
    public Videojuego categorias(Set<Categoria> categorias) {
        this.categorias = categorias;
        return this;
    }
    public Videojuego addCategoria(Categoria categoria) {
        categorias.add(categoria);
        categoria.getVideojuegos().add(this);
        return this;
    }
    public Videojuego removeCategoria(Categoria categoria) {
        categorias.remove(categoria);
        categoria.getVideojuegos().remove(this);
        return this;
    }
    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Producto> getProductos() {
        return productos;
    }
    public Videojuego productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }
    public Videojuego addProducto(Producto producto) {
        productos.add(producto);
        producto.setVideojuego(this);
        return this;
    }
    public Videojuego removeProducto(Producto producto) {
        productos.remove(producto);
        producto.setVideojuego(null);
        return this;
    }
    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }


    @Override
    public String toString() {
        return "Videojuego{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
