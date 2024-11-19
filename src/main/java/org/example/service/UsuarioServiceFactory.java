package org.example.service;

public class UsuarioServiceFactory {

    private UsuarioServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static UsuarioService create(){
        return new UsuarioServiceImpl();
    }
}
