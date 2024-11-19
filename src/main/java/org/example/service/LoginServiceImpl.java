package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.LoginDao;
import org.example.dao.LoginDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class LoginServiceImpl implements LoginService {

    private LoginDao loginDao = new LoginDaoImpl();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Login getLoginById(Long id) {
        return loginDao.findById(id);
    }

    @Override
    public List<Login> getAllLogins() {
        return loginDao.findAll();
    }

    @Override
    public Login createLogin(Login login) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            login.validarSenha();
            loginDao.createLogin(login, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Login");
            connection.rollback();
            throw e;
        }
            return login;

    }

    @Override
    public void updateLogin(Login login)  {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            loginDao.updateLogin(login, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }

    @Override
    public void deleteLogin(Long id) throws SQLException, ExceptionNotFound {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        loginDao.deleteById(id, connection);
        connection.commit();
    }
}
