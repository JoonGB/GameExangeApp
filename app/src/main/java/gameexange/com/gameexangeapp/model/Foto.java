package gameexange.com.gameexangeapp.model;

public class Foto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String creado;
    private byte[] foto;
    private String fotoContentType;
    private Producto producto;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public Foto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public Foto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreado() {
        return creado;
    }
    public Foto creado(String creado) {
        this.creado = creado;
        return this;
    }
    public void setCreado(String creado) {
        this.creado = creado;
    }

    public byte[] getFoto() {
        return foto;
    }
    public Foto foto(byte[] foto) {
        this.foto = foto;
        return this;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }
    public Foto fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }
    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Producto getProducto() {
        return producto;
    }
    public Foto producto(Producto producto) {
        this.producto = producto;
        return this;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    @Override
    public String toString() {
        return "Foto{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", descripcion='" + descripcion + "'" +
                ", creado='" + creado + "'" +
                ", foto='" + foto + "'" +
                ", fotoContentType='" + fotoContentType + "'" +
                '}';
    }
}
