package org.example.service;

public class LoginServiceFactory {

    private LoginServiceFactory(){
        throw new UnsupportedOperationException();
    }

    public static LoginService create(){
        return new LoginServiceImpl();
    }
}
