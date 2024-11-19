package org.example.service;

public class MensagemServiceFactory {

    private MensagemServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static MensagemService create(){
        return new MensagemServiceImpl();
    }
}
