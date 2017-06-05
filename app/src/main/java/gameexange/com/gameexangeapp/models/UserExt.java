package gameexange.com.gameexangeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import gameexange.com.gameexangeapp.models.enumeration.Genero;

public class UserExt implements Parcelable {

    private Long id;
    private Genero genero;
    private String ciudad;
    private String fechaNacimiento;
    private Integer cp;
    private String foto;
    private String fotoContentType;
    private String telefono;
    private User user;

    public UserExt(){}
    protected UserExt(Parcel in) {
        ciudad = in.readString();
        fechaNacimiento = in.readString();
        foto = in.readString();
        fotoContentType = in.readString();
        telefono = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<UserExt> CREATOR = new Creator<UserExt>() {
        @Override
        public UserExt createFromParcel(Parcel in) {
            return new UserExt(in);
        }

        @Override
        public UserExt[] newArray(int size) {
            return new UserExt[size];
        }
    };

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }
    public UserExt genero(Genero genero) {
        this.genero = genero;
        return this;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }
    public UserExt ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public UserExt fechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getCp() {
        return cp;
    }
    public UserExt cp(Integer cp) {
        this.cp = cp;
        return this;
    }
    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getFoto() {
        return foto;
    }
    public UserExt foto(String foto) {
        this.foto = foto;
        return this;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }
    public UserExt fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }
    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getTelefono() {
        return telefono;
    }
    public UserExt telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public User getUser() {
        return user;
    }
    public UserExt user(User user) {
        this.user = user;
        return this;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "UserExt{" +
                "id=" + id +
                ", genero='" + genero + "'" +
                ", ciudad='" + ciudad + "'" +
                ", fechaNacimiento='" + fechaNacimiento + "'" +
                ", cp='" + cp + "'" +
                ", foto='" + foto + "'" +
                ", fotoContentType='" + fotoContentType + "'" +
                ", telefono='" + telefono + "'" +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ciudad);
        parcel.writeString(fechaNacimiento);
        parcel.writeString(foto);
        parcel.writeString(fotoContentType);
        parcel.writeString(telefono);
        parcel.writeParcelable(user, i);
    }
}
