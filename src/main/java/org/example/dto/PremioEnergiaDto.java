package org.example.dto;

public class PremioEnergiaDto {

    private Long id;
    private String nomePremio;
    private int valorPremio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePremio() {
        return nomePremio;
    }

    public void setNomePremio(String nomePremio) {
        this.nomePremio = nomePremio;
    }

    public int getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(int valorPremio) {
        this.valorPremio = valorPremio;
    }
}
