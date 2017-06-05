package gameexange.com.gameexangeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.Set;

public class Conversacion implements Parcelable {

    private Long id;
    private String creado;
    private UserExt usuario1;
    private UserExt usuario2;
    private Producto producto;
    private Set<Mensaje> mensajes = new HashSet<>();

    public  Conversacion(){}

    protected Conversacion(Parcel in) {
        id = in.readLong();
        creado = in.readString();
        usuario1 = in.readParcelable(UserExt.class.getClassLoader());
        usuario2 = in.readParcelable(UserExt.class.getClassLoader());
        producto = in.readParcelable(Producto.class.getClassLoader());
    }

    public static final Creator<Conversacion> CREATOR = new Creator<Conversacion>() {
        @Override
        public Conversacion createFromParcel(Parcel in) {
            return new Conversacion(in);
        }

        @Override
        public Conversacion[] newArray(int size) {
            return new Conversacion[size];
        }
    };

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

    public UserExt getUsuario1() {
        return usuario1;
    }
    public void setUsuario1(UserExt usuario1) {
        this.usuario1 = usuario1;
    }

    public UserExt getUsuario2() {
        return usuario2;
    }
    public void setUsuario2(UserExt usuario2) {
        this.usuario2 = usuario2;
    }

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    @Override
    public String toString() {
        return "Conversacion{" +
                "id=" + id +
                ", creado='" + creado + "'" +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(creado);
        parcel.writeParcelable(usuario1, i);
        parcel.writeParcelable(usuario2, i);
        parcel.writeParcelable(producto, i);
    }
}
