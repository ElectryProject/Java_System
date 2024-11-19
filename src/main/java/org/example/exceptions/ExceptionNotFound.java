package org.example.exceptions;

public class ExceptionNotFound extends RuntimeException {
    public ExceptionNotFound(Long id) {
        super("ID não encontrado.");
    }
}
