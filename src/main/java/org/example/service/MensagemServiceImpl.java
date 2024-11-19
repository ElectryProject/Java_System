package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.MensagemDao;
import org.example.dao.MensagemDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Mensagem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class MensagemServiceImpl implements MensagemService {

    private MensagemDao mensagemDao = new MensagemDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Mensagem getMensagemById(Long id) throws SQLException {
        return mensagemDao.findById(id);
    }

    @Override
    public List<Mensagem> getAllMensagem() throws SQLException {
        return mensagemDao.findAll();
    }

    @Override
    public Mensagem createMensagem(Mensagem mensagem) throws SQLException, ExceptionNotCreated {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            this.mensagemDao.createMensagem(mensagem, connection);
            connection.commit();
        }catch (SQLException | ExceptionNotCreated e){
            logger.severe("Erro ao criar Mensagem");
            connection.rollback();
            throw e;
        }
        return mensagem;
    }

    @Override
    public void deleteMensagem(Long id) throws SQLException, ExceptionNotFound {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        mensagemDao.deleteById(id, connection);
        connection.commit();
    }

    @Override
    public void updateMensagem(Mensagem mensagem)  {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            mensagemDao.updateMensagem(mensagem, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }
}
