package org.example.service;

public class CentroReciclagemServiceFactory {

    private CentroReciclagemServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static CentroReciclagemService create(){
        return new CentroReciclagemServiceImpl();
    }
}
