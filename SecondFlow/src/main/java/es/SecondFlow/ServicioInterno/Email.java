package es.SecondFlow.ServicioInterno;

public class Email {
    private String destinatario;
    private String origen;
    private String texto;
    private String asunto;

    public Email(String destinatario, String asunto, String mensaje) {
        this.destinatario = destinatario;
        this.origen ="MADCLOTHESemail@gmail.com";
        this.texto = mensaje;
        this.asunto = asunto;

    }

    public Email(String destinatario,  String asunto) {
        this.destinatario = destinatario;
        this.origen = "MADCLOTHESemail@gmail.com";
        this.asunto = asunto;
    }


    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}
