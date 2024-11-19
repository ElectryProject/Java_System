package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.PremioEnergiaDao;
import org.example.dao.PremioEnergiaDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.PremioEnergia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class PremioEnergiaServiceImpl implements PremioEnergiaService{

    private PremioEnergiaDao premioEnergiaDao = new PremioEnergiaDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public PremioEnergia getPremioById(Long id) {
        return premioEnergiaDao.findById(id);
    }

    @Override
    public List<PremioEnergia> getAllPremios() {
        return premioEnergiaDao.findAll();
    }

    @Override
    public PremioEnergia createPremio(PremioEnergia premioEnergia) throws SQLException, ExceptionNotCreated {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            premioEnergiaDao.createPremio(premioEnergia, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Prêmio Energia");
            connection.rollback();
            throw e;
        }
        return premioEnergia;
    }

    @Override
    public void updatePremio(PremioEnergia premioEnergia) {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            premioEnergiaDao.updatePremio(premioEnergia, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }

    }

    @Override
    public void deletePremio(Long id) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        this.premioEnergiaDao.deleteById(id, connection);
        connection.commit();
    }
}
