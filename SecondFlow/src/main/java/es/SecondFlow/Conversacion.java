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
    Usuario emisor;

    @ManyToOne
    Usuario receptor;

    @OneToMany(mappedBy = "conversacionEmisor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Mensaje> conversacionEmisor;

    @OneToMany(mappedBy = "conversacionReceptor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Mensaje> conversacionReceptor;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
