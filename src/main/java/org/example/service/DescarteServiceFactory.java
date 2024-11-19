package org.example.service;

public class DescarteServiceFactory {

    private DescarteServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static DescarteService create(){
        return new DescarteServiceImpl();
    }
}
