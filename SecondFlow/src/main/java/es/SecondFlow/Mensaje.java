package es.SecondFlow;

import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.time.*;

@Entity
public class Mensaje {
    @Id
    Long id;

    String texto;
    @ManyToOne
    Usuario emisor;

    @ManyToOne
    Usuario receptor;

    LocalDate fecha;


}
