package org.example.model;


import org.example.exceptions.SenhaInvalidException;
import org.example.exceptions.UsuarioInvalidException;
import org.example.exceptions.UsuarioNotAutorizedException;

import java.time.LocalDate;
import java.time.Period;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private long numeroCpf;
    private int idade;
    private long numeroTelefone;
    private String numeroCep;
    private String endereco;



    public Usuario() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getNumeroCpf() {
        return numeroCpf;
    }

    public void setNumeroCpf(long numeroCpf) {
        this.numeroCpf = numeroCpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public long getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(long numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getNumeroCep() {
        return numeroCep;
    }

    public void setNumeroCep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public void validarDadosUsuario(){
        if (idade < 18){
            throw new UsuarioNotAutorizedException();
        }
        String cpf = String.valueOf(numeroCpf);
        if (cpf.length() != 11){
            throw new UsuarioInvalidException();
        }
        String cep = String.valueOf(numeroCep);
        if (cep.length() != 8){
            throw new UsuarioInvalidException();
        }
        if (senha.length() != 12){
            throw new SenhaInvalidException();
        }
    }

}
