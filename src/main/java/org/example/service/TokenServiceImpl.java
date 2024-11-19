package org.example.service;


import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.TokenDao;
import org.example.dao.TokenDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Token;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class TokenServiceImpl implements TokenService {

    private TokenDao tokenDao = new TokenDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Token getTokenById(Long id) {
        return tokenDao.findById(id);
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenDao.findAll();
    }

    @Override
    public Token createToken(Token token) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            tokenDao.createToken(token, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Token");
            connection.rollback();
            throw e;
        }
        return token;
    }

    @Override
    public void updateToken(Token token)  {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            tokenDao.updateToken(token, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }

    @Override
    public void deleteToken(Long id) throws SQLException, ExceptionNotFound {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        this.tokenDao.deleteById(id, connection);
        connection.commit();
    }
}
