package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.CentroReciclagemDao;
import org.example.dao.CentroReciclagemDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.CentroReciclagem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class CentroReciclagemServiceImpl implements CentroReciclagemService {

    private CentroReciclagemDao centroReciclagemDao = new CentroReciclagemDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public CentroReciclagem getCentroReciclagemById(Long id) throws SQLException {
        return centroReciclagemDao.findById(id);
    }

    @Override
    public List<CentroReciclagem> getAllCentroReciclagem() throws SQLException {
        return centroReciclagemDao.findAll();
    }

    @Override
    public CentroReciclagem createCentroReciclagem(CentroReciclagem centroReciclagem) throws SQLException, ExceptionNotCreated {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            this.centroReciclagemDao.createCentroReciclagem(centroReciclagem, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Centro de Reciclagem");
            connection.rollback();
            throw e;
        }
        return centroReciclagem;
    }

    @Override
    public void deleteCentroReciclagem(Long id) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        centroReciclagemDao.deleteById(id, connection);
        connection.commit();
    }

    @Override
    public void updateCentroReciclagem(CentroReciclagem centroReciclagem)  {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            centroReciclagemDao.updateCentroReciclagem(centroReciclagem, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }


    }
}
