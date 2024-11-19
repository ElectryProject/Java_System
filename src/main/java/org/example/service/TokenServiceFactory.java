package org.example.service;

public class TokenServiceFactory {

    private TokenServiceFactory() {
        throw new UnsupportedOperationException();
    }

    public static TokenService create() {
        return new TokenServiceImpl();
    }
}
