package org.example.model;

import org.example.exceptions.SenhaInvalidException;

public class Login {

    private Long id;
    private String username;
    private String senha;


    public Login() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void validarSenha(){
        if (senha.length() != 12){
            throw new SenhaInvalidException();
        }
    }
}
