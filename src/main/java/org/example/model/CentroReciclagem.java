package org.example.model;

public class CentroReciclagem {

    private Long id;
    private String nomeCentro;
    private double valorResgatado;


    public CentroReciclagem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCentro() {
        return nomeCentro;
    }

    public void setNomeCentro(String nomeCentro) {
        this.nomeCentro = nomeCentro;
    }

    public double getValorResgatado() {
        return valorResgatado;
    }

    public void setValorResgatado(double valorResgatado) {
        this.valorResgatado = valorResgatado;
    }
}
