package org.example.service;

public class PremioEnergiaServiceFactory {

    private PremioEnergiaServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public  static PremioEnergiaService create(){
        return new PremioEnergiaServiceImpl();
    }
}
