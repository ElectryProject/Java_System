package org.example.exceptions;

public class SenhaInvalidException extends RuntimeException {
    public SenhaInvalidException() {
        super("A senha deve conter 12 caracteres");
    }
}
