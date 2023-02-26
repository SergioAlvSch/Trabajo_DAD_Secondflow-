package es.SecondFlow;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversacion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    Producto producto;

    @ManyToOne
    Usuario emisor;

    @ManyToOne
    Usuario receptor;

    @OneToMany(mappedBy = "conversacion", cascade = CascadeType.ALL, orphanRemoval = true)
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
