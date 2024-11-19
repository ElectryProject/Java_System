package org.example.model;

public class Descarte {

    private Long id;
    private String produto;
    private String dataDescarte;


    public Descarte() {
    }

    public Long getId() {
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
