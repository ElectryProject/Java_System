package org.example.dao;

import org.example.exceptions.ExceptionNotFound;
import org.example.model.Usuario;

import java.sql.Connection;
import java.util.List;

public interface UsuarioDao {

    Usuario findById(Long id);
    Usuario findbyName(String nome);
    List<Usuario> findAll();
    Usuario createUsuario(Usuario usuario, Connection connection);
    void updateUsuario(Usuario usuario, Connection connection) ;
    void deleteById(Long id, Connection connection) ;
}
