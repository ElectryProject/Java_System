package org.example.service;

public class PontoColetaServiceFactory {

    private PontoColetaServiceFactory(){
        throw new UnsupportedOperationException();

    }

    public static PontoColetaService create(){
        return new PontoColetaServiceImpl();
    }
}
