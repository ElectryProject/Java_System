package org.example.dao;

import org.example.model.CentroReciclagem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CentroReciclagemDao {

    CentroReciclagem findById(Long id);
    List<CentroReciclagem> findAll();
    CentroReciclagem createCentroReciclagem(CentroReciclagem centroReciclagem, Connection connection);
    void updateCentroReciclagem(CentroReciclagem centroReciclagem, Connection connection) ;
    void deleteById(Long id, Connection connection);
}
