package org.example.dao;

import org.example.model.PremioEnergia;

import java.sql.Connection;
import java.util.List;

public interface PremioEnergiaDao {

    PremioEnergia findById(Long id);
    List<PremioEnergia> findAll();
    PremioEnergia createPremio(PremioEnergia premioEnergia, Connection connection);
    void updatePremio(PremioEnergia premioEnergia, Connection connection);
    void deleteById(Long id, Connection connection);
}
