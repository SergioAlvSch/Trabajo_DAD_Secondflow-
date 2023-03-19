package com.example.secondflow_serviciointerno;

public class email {
    private String remitente;
    private String destinatario;
    private String texto;
    private String titulo;

    public email(String destinatario, String texto, String titulo) {
        this.destinatario = destinatario;
        this.texto = texto;
        this.titulo = titulo;
        this.remitente = "SecondFlowApp@gmail.com";
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
