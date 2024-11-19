package org.example.exceptions;

public class UsuarioInvalidException extends RuntimeException {
    public UsuarioInvalidException() {
        super("Dados inválidos, verifique a quantidade de caracteres dos campos numéricos.");
    }
}
