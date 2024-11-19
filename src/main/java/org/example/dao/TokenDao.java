package org.example.dao;

import org.example.model.Token;

import java.sql.Connection;
import java.util.List;

public interface TokenDao {

    Token findById(Long id);
    List<Token> findAll();
    Token createToken(Token token, Connection connection);
    void updateToken(Token token, Connection connection);
    void deleteById(Long id, Connection connection);
}
