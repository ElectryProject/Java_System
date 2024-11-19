package org.example.dto;

public class FeedbackDto {
    private Long id;
    private String comentarioAvaliativo;
    private int pontuacaoAvaliativa;

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
}
