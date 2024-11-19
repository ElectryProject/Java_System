package org.example.model;

import org.example.exceptions.FeedbackInvalidException;

public class Feedback {

    private Long id;
    private String comentarioAvaliativo;
    private int pontuacaoAvaliativa;


    public Feedback() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentarioAvaliativo() {
        return comentarioAvaliativo;
    }

    public void setComentarioAvaliativo(String comentarioAvaliativo) {
        this.comentarioAvaliativo = comentarioAvaliativo;
    }

    public int getPontuacaoAvaliativa() {
        return pontuacaoAvaliativa;
    }

    public void setPontuacaoAvaliativa(int pontuacaoAvaliativa) {
        this.pontuacaoAvaliativa = pontuacaoAvaliativa;
    }


    public void validarPontuacao(){
        if (pontuacaoAvaliativa > 10 || pontuacaoAvaliativa < 0){
            throw new FeedbackInvalidException();
        }
    }


}
