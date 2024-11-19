package org.example.service;

import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Token;

import java.sql.SQLException;
import java.util.List;

public interface TokenService {

    Token getTokenById(Long id) throws SQLException;
    List<Token> getAllTokens() throws SQLException;
    Token createToken(Token token) throws SQLException;
    void updateToken(Token token) throws ExceptionNotUpdate, SQLException;
    void deleteToken(Long id) throws SQLException, ExceptionNotFound;
}
