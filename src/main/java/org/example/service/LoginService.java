package org.example.service;

import org.example.exceptions.ExceptionNotFound;
import org.example.model.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface LoginService {

    Login getLoginById(Long id) throws SQLException;
    List<Login> getAllLogins() throws SQLException;
    Login createLogin(Login login) throws SQLException;
    void updateLogin(Login login) throws SQLException;
    void deleteLogin(Long id) throws SQLException, ExceptionNotFound;
}
