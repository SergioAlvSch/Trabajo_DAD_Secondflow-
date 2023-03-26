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

    Boolean usuarioLog;

    String nombreEmisor;


    @ManyToOne(cascade = CascadeType.ALL)
    Conversacion conversacion;


    public Mensaje(String texto, LocalDateTime fecha, long idEmisor, Conversacion conversacion, String nombreEmisor) {
        super();
        this.nombreEmisor = nombreEmisor;
        this.texto = texto;
        this.fecha = fecha;
        this.idEmisor = idEmisor;
        this.conversacion = conversacion;
    }

    public Mensaje() {
    }

    public void setUsuarioLog(Boolean usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public Boolean getUsuarioLog() {
        return usuarioLog;
    }

    public Long getIdEmisor() {
        return idEmisor;
    }
}
