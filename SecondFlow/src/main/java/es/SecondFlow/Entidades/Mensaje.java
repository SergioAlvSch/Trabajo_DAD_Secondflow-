package es.SecondFlow.Entidades;

import javax.persistence.*;
import java.time.*;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String texto;

    LocalDateTime fecha;

    long idEmisor;


    @ManyToOne(cascade = CascadeType.ALL)
    Conversacion conversacion;


    public Mensaje(String texto, LocalDateTime fecha, long idEmisor,Conversacion conversacion) {
        super();
        this.texto = texto;
        this.fecha = fecha;
        this.idEmisor = idEmisor;
        this.conversacion=conversacion;
    }
    public Mensaje(){
    }
}
