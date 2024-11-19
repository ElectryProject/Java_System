package org.example.dao;

import org.example.model.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface LoginDao {
    Login findById(Long id);
    List<Login> findAll();
    Login createLogin(Login login, Connection connection);
    void updateLogin(Login login, Connection connection);
    void deleteById(Long id, Connection connection);
}
