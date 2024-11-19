package org.example.service;

import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioService {

    Usuario getUsuarioById(Long id) throws SQLException;
    Usuario getUsuarioByName(String name) throws SQLException;
    List<Usuario> getAllUsuarios() throws SQLException;
    Usuario createUsuario(Usuario usuario) throws ExceptionNotCreated, SQLException;
    void updateUsuario(Usuario usuario) throws SQLException;
    void deleteUsuario(Long id) throws SQLException, ExceptionNotFound;
}
