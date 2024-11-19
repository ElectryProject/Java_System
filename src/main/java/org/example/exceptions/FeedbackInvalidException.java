package org.example.exceptions;

public class FeedbackInvalidException extends RuntimeException {
    public FeedbackInvalidException() {
        super("Verifique a pontuação dada: pontuação vale entre 0 e 10");
    }
}
