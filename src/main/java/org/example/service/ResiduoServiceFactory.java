package org.example.service;

public class ResiduoServiceFactory {

    private ResiduoServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static ResiduoService create(){
        return new ResiduoServiceImpl();
    }
}
