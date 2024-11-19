package org.example.dto;



public class UsuarioDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private long numeroCpf;
    private int idade;
    private long numeroTelefone;
    private String numeroCep;
    private String endereco;


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


}
