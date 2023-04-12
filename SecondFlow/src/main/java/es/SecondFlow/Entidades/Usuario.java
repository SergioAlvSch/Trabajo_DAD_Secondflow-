package es.SecondFlow.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.util.*;

@Entity
@Component
@SessionScope //instancia del componente de cada usuario
public class Usuario {
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Producto> listaMisProductos;
    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Producto> listaMisProductosComprados;

    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Conversacion> listaMisConversacionesE;

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Conversacion> listaMisConversacionesR;
    String nombreUsuario;

    String clave;

    String correo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Usuario(String nombreUsuario, String clave,String correo, String... roles) {
        super();
        this.nombreUsuario = nombreUsuario;
        this.clave= clave;
        this.roles= List.of(roles);
        this.listaMisProductosComprados= new ArrayList<>();
        this.listaMisConversacionesR = new ArrayList<>();
        this.listaMisConversacionesE = new ArrayList<>();
        this.listaMisProductos = new ArrayList<>();
        this.correo=correo;
    }

    public Usuario() {

    }



    public String getClave() {
        return clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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
