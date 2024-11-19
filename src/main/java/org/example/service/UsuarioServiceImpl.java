package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.UsuarioDao;
import org.example.dao.UsuarioDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDao usuarioDao = new UsuarioDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Usuario getUsuarioById(Long id)  {
        return usuarioDao.findById(id);
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        return usuarioDao.findbyName(name);
    }


    @Override
    public List<Usuario> getAllUsuarios()  {
        return usuarioDao.findAll();
    }

    @Override
    public Usuario createUsuario(Usuario usuario) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            usuario.validarDadosUsuario();
            usuarioDao.createUsuario(usuario, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Usuário");
            connection.rollback();
            throw e;
        }
        return usuario;
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            usuarioDao.updateUsuario(usuario, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }


    }

    @Override
    public void deleteUsuario(Long id) throws SQLException, ExceptionNotFound {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        this.usuarioDao.deleteById(id, connection);
        connection.commit();
    }
}
