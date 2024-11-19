package org.example.exceptions;

public class ExceptionNotCreated extends RuntimeException {
    public ExceptionNotCreated() {
        super("O registro não foi criado");
    }
}
