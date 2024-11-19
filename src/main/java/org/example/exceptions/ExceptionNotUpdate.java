package org.example.exceptions;

public class ExceptionNotUpdate extends RuntimeException {
    public ExceptionNotUpdate() {
        super("Erro ao atualizar registro");
    }
}
