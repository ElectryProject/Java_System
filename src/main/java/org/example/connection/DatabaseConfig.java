package org.example.connection;

final class DatabaseConfig {


    private DatabaseConfig(){
        throw new UnsupportedOperationException();
    }

    static String getUrl(){
        return "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    }

    static String getUser(){
        return "rm558830";
    }

    static String getPassword(){
        return "070306";
    }
}
