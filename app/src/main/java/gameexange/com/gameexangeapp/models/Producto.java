package gameexange.com.gameexangeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.Set;

public class Producto implements Parcelable {

    private Long id;
    private String descripcion;
    private String creado;
    private Double precio;
    private String nombre;
    private Long videojuego;
    private User usuario;

    private Set<Foto> fotos = new HashSet<>();
    private Set<Venta> ventas = new HashSet<>();
    private Foto fotoPrincipal;
    private UserExt usuarioext;

    public Producto() {

    }
    protected Producto(Parcel in) {
        id = in.readLong();
        descripcion = in.readString();
        creado = in.readString();
        precio = in.readDouble();
        nombre = in.readString();
        videojuego = in.readLong();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreado() {
        return creado;
    }
    public Producto creado(String creado) {
        this.creado = creado;
        return this;
    }
    public void setCreado(String creado) {
        this.creado = creado;
    }

    public Double getPrecio() {
        return precio;
    }
    public Producto precio(Double precio) {
        this.precio = precio;
        return this;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }
    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getVideojuego() {
        return videojuego;
    }
    public Producto videojuego(Long videojuego) {
        this.videojuego = videojuego;
        return this;
    }
    public void setVideojuego(Long videojuego) {
        this.videojuego = videojuego;
    }

    public User getUsuario() {
        return usuario;
    }
    public Producto usuario(User user) {
        this.usuario = user;
        return this;
    }
    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Foto> getFotos() {
        return fotos;
    }
    public Producto fotos(Set<Foto> fotos) {
        this.fotos = fotos;
        return this;
    }
    public Producto addFoto(Foto foto) {
        fotos.add(foto);
        foto.setProducto(this);
        return this;
    }
    public Producto removeFoto(Foto foto) {
        fotos.remove(foto);
        foto.setProducto(null);
        return this;
    }
    public void setFotos(Set<Foto> fotos) {
        this.fotos = fotos;
    }

    public Set<Venta> getVentas() {
        return ventas;
    }
    public Producto ventas(Set<Venta> ventas) {
        this.ventas = ventas;
        return this;
    }
    public Producto addVenta(Venta venta) {
        ventas.add(venta);
        venta.setProducto(this);
        return this;
    }
    public Producto removeVenta(Venta venta) {
        ventas.remove(venta);
        venta.setProducto(null);
        return this;
    }
    public void setVentas(Set<Venta> ventas) {
        this.ventas = ventas;
    }

    public Foto getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(Foto fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public UserExt getUsuarioext() {
        return usuarioext;
    }

    public void setUsuarioext(UserExt usuarioext) {
        this.usuarioext = usuarioext;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", creado='" + creado + '\'' +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", videojuego=" + videojuego +
                ", usuario=" + usuario +
                ", userExt=" + usuarioext +
                ", fotos=" + fotos +
                ", ventas=" + ventas +
                ", fotoPrincipal=" + fotoPrincipal +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(descripcion);
        parcel.writeString(creado);
        parcel.writeDouble(precio);
        parcel.writeString(nombre);
        parcel.writeLong(videojuego);
    }
}
