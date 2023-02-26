package es.SecondFlow;

import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.time.*;
import java.util.List;

@Entity
public class Mensaje {
    @Id
    Long id;

    String texto;

    LocalDateTime fecha;

    long idEmisor;



    @ManyToOne(cascade = CascadeType.ALL)
    Conversacion conversacion;


    public Mensaje(String texto, LocalDateTime fecha, long idEmisor) {
        super();
        this.texto = texto;
        this.fecha = fecha;
        this.idEmisor = idEmisor;
    }
    public Mensaje(){

    }
}
