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

    LocalDate fecha;



    @ManyToOne(cascade = CascadeType.ALL)
    Conversacion conversacionEmisor;

    @ManyToOne(cascade = CascadeType.ALL)
    Conversacion conversacionReceptor;

}
