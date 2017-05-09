package gameexange.com.gameexangeapp.models;

import gameexange.com.gameexangeapp.models.enumeration.Genero;

public class UserExt {

    private Long id;
    private Genero genero;
    private String ciudad;
    private String fechaNacimiento;
    private Integer cp;
    private byte[] foto;
    private String fotoContentType;
    private String telefono;
    private User user;

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

    public byte[] getFoto() {
        return foto;
    }
    public UserExt foto(byte[] foto) {
        this.foto = foto;
        return this;
    }
    public void setFoto(byte[] foto) {
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
}
