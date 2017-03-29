package gameexange.com.gameexangeapp.model;

import java.util.HashSet;
import java.util.Set;

public class Venta {

    private Long id;
    private String creado;
    private Integer valoracionCliente;
    private Integer valoreacionVendedor;
    private String comentarioCliente;
    private String comentarioVendedor;
    private User vendedor;
    private User cliente;
    private Producto producto;
    private Set<Incidencia> incidencias = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCreado() {
        return creado;
    }
    public Venta creado(String creado) {
        this.creado = creado;
        return this;
    }
    public void setCreado(String creado) {
        this.creado = creado;
    }

    public Integer getValoracionCliente() {
        return valoracionCliente;
    }
    public Venta valoracionCliente(Integer valoracionCliente) {
        this.valoracionCliente = valoracionCliente;
        return this;
    }
    public void setValoracionCliente(Integer valoracionCliente) {
        this.valoracionCliente = valoracionCliente;
    }

    public Integer getValoreacionVendedor() {
        return valoreacionVendedor;
    }
    public Venta valoreacionVendedor(Integer valoreacionVendedor) {
        this.valoreacionVendedor = valoreacionVendedor;
        return this;
    }
    public void setValoreacionVendedor(Integer valoreacionVendedor) {
        this.valoreacionVendedor = valoreacionVendedor;
    }

    public String getComentarioCliente() {
        return comentarioCliente;
    }
    public Venta comentarioCliente(String comentarioCliente) {
        this.comentarioCliente = comentarioCliente;
        return this;
    }
    public void setComentarioCliente(String comentarioCliente) {
        this.comentarioCliente = comentarioCliente;
    }

    public String getComentarioVendedor() {
        return comentarioVendedor;
    }
    public Venta comentarioVendedor(String comentarioVendedor) {
        this.comentarioVendedor = comentarioVendedor;
        return this;
    }
    public void setComentarioVendedor(String comentarioVendedor) {
        this.comentarioVendedor = comentarioVendedor;
    }

    public User getVendedor() {
        return vendedor;
    }
    public Venta vendedor(User user) {
        this.vendedor = user;
        return this;
    }
    public void setVendedor(User user) {
        this.vendedor = user;
    }

    public User getCliente() {
        return cliente;
    }
    public Venta cliente(User user) {
        this.cliente = user;
        return this;
    }
    public void setCliente(User user) {
        this.cliente = user;
    }

    public Producto getProducto() {
        return producto;
    }
    public Venta producto(Producto producto) {
        this.producto = producto;
        return this;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }
    public Venta incidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
        return this;
    }
    public Venta addIncidencia(Incidencia incidencia) {
        incidencias.add(incidencia);
        incidencia.setVenta(this);
        return this;
    }
    public Venta removeIncidencia(Incidencia incidencia) {
        incidencias.remove(incidencia);
        incidencia.setVenta(null);
        return this;
    }
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }


    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", creado='" + creado + "'" +
                ", valoracionCliente='" + valoracionCliente + "'" +
                ", valoreacionVendedor='" + valoreacionVendedor + "'" +
                ", comentarioCliente='" + comentarioCliente + "'" +
                ", comentarioVendedor='" + comentarioVendedor + "'" +
                '}';
    }
}
