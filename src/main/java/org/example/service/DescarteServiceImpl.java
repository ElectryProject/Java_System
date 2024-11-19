package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.DescarteDao;
import org.example.dao.DescarteDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Descarte;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class DescarteServiceImpl implements DescarteService {


    private DescarteDao descarteDao = new DescarteDaoImpl();
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Descarte getDescarteById(Long id) {
        return descarteDao.findById(id);
    }

    @Override
    public List<Descarte> getAllDescarte() {
        return descarteDao.findAll();
    }

    @Override
    public Descarte createDescarte(Descarte descarte) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            this.descarteDao.createDescarte(descarte, connection);
            connection.commit();
        }catch (SQLException | ExceptionNotCreated e){
            logger.severe("Erro ao criar Descarte");
            connection.rollback();
            throw e;
        }
        return descarte;
    }

    @Override
    public void updateDescarte(Descarte descarte) {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            descarteDao.updateDescarte(descarte, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }

    @Override
    public void deleteDescarte(Long id) throws SQLException, ExceptionNotFound {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        descarteDao.deleteById(id, connection);
        connection.commit();
    }
}
