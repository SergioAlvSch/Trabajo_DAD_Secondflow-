package es.SecondFlow.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Conversacion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    Producto producto;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    Usuario emisor;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    Usuario receptor;

    @OneToMany(mappedBy = "conversacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Mensaje> listaMensajes;

    public Long getId() {
        return id;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public Producto getProducto() {
        return producto;
    }

    public List<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public Conversacion(Usuario emisor, Usuario receptor,Producto producto) {
        super();
        this.emisor = emisor;
        this.receptor = receptor;
        this.producto= producto;
    }
    public Conversacion() {

    }
}
