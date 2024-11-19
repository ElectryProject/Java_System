package org.example.exceptions;

public class UsuarioNotAutorizedException extends RuntimeException {
    public UsuarioNotAutorizedException() {
        super("Usuário menor de idade não pode realizar o cadastro.");
    }
}
