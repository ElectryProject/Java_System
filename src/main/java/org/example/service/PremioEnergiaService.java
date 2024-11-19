package org.example.service;

import org.example.exceptions.ExceptionNotCreated;
import org.example.model.PremioEnergia;

import java.sql.SQLException;
import java.util.List;

public interface PremioEnergiaService {

    PremioEnergia getPremioById(Long id) throws SQLException;
    List<PremioEnergia> getAllPremios() throws SQLException;
    PremioEnergia createPremio(PremioEnergia premioEnergia) throws SQLException, ExceptionNotCreated;
    void updatePremio(PremioEnergia premioEnergia);
    void deletePremio(Long id) throws SQLException;
}
