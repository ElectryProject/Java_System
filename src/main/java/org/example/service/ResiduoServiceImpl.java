package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.ResiduoDao;
import org.example.dao.ResiduoDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Residuo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class ResiduoServiceImpl implements ResiduoService {


    private ResiduoDao residuoDao = new ResiduoDaoImpl();
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Residuo getResiduoById(Long id) {
        return residuoDao.findById(id);
    }

    @Override
    public List<Residuo> getAllResiduos() {
        return residuoDao.findAll();
    }

    @Override
    public Residuo createResiduo(Residuo residuo) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            residuoDao.createResiduo(residuo, connection);
            connection.commit();
        }catch(SQLException | ExceptionNotCreated e){
            logger.severe("Erro ao criar Resíduo");
            connection.rollback();
            throw e;
        }
        return residuo;
    }

    @Override
    public void updateResiduo(Residuo residuo) {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            residuoDao.updateResiduo(residuo, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }

    @Override
    public void deleteResiduo(Long id) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        residuoDao.deleteById(id, connection);
        connection.commit();
    }
}
