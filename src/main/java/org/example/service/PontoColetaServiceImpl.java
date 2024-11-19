package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.PontoColetaDao;
import org.example.dao.PontoColetaDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.PontoColeta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class PontoColetaServiceImpl implements PontoColetaService {

    private PontoColetaDao pontoColetaDao = new PontoColetaDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public PontoColeta getPontoColetaById(Long id) throws SQLException {
        return pontoColetaDao.findById(id);
    }

    @Override
    public List<PontoColeta> getAllPontoColeta() throws SQLException {
        return pontoColetaDao.findAll();
    }

    @Override
    public PontoColeta createPontoColeta(PontoColeta pontoColeta) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            pontoColetaDao.createPontoColeta(pontoColeta, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Ponto de Coleta");
            connection.rollback();
            throw e;
        }
        return pontoColeta;
    }

    @Override
    public void updatePontoColeta(PontoColeta pontoColeta) {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            pontoColetaDao.updatePontoColeta(pontoColeta, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }

    @Override
    public void deletePontoColeta(Long id) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        pontoColetaDao.deleteById(id, connection);
        connection.commit();
    }
}
