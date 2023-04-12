package es.SecondFlow.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String nombre;

    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private double precio;

    @Lob
    private Blob imagenProducto;

    private boolean hayImagen;
    @ManyToOne
    @JsonIgnore
    private Usuario vendedor;

    @ManyToOne
    @JsonIgnore
    private Usuario comprador;
    
    private boolean hayComprador;

    protected Producto() {
    }

    public Producto(String nombre, String categoria, String descripcion, double precio, Usuario vendedor) {
        super();
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.vendedor = vendedor;
    }


    public Blob getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(Blob imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public boolean isHayImagen() {
        return hayImagen;
    }

    public void setHayImagen(boolean hayImagen) {
        this.hayImagen = hayImagen;
    }

    public Producto getProducto() {
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public Boolean isHayComprador() {
        return hayComprador;
    }

    public void setHayComprador() {
        this.hayComprador=true;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
