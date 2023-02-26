package es.SecondFlow;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.util.*;

@Entity
@Component
@SessionScope //instancia del componente de cada usuario
public class Usuario {
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Producto> listaMisProductos;
    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Producto> listaMisProductosComprados;

    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Conversacion> listaMisConversacionesE;

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Conversacion> listaMisConversacionesR;
    String nombreUsuario;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Usuario(String nombreUsuario) {
        super();
        this.nombreUsuario = nombreUsuario;
        this.listaMisProductosComprados= new ArrayList<>();
        this.listaMisConversacionesR = new ArrayList<>();
        this.listaMisConversacionesE = new ArrayList<>();
        this.listaMisProductos = new ArrayList<>();
    }

    public Usuario() {

    }



    public void meterArticuloALista(Producto producto) {
        listaMisProductos.add(producto);
    }

    public List<Producto> getListaProductos() {
        return this.listaMisProductos;
    }

    public Long getId() {
        return id;
    }

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public List<Producto> getListaProductosComprados() {
        return this.listaMisProductosComprados;
    }

    public List<Conversacion> getListaMisConversacionesE() {
        return listaMisConversacionesE;
    }

    public List<Conversacion> getListaMisConversacionesR() {
        return listaMisConversacionesR;
    }
}



