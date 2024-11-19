package org.example.dto;

public class DescarteDto {
    private long id;
    private String produto;
    private String dataDescarte;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDataDescarte() {
        return dataDescarte;
    }

    public void setDataDescarte(String dataDescarte) {
        this.dataDescarte = dataDescarte;
    }
}
