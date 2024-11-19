package org.example.service;

import org.example.exceptions.ExceptionNotFound;
import org.example.model.CentroReciclagem;

import java.sql.SQLException;
import java.util.List;

public interface CentroReciclagemService {

    CentroReciclagem getCentroReciclagemById(Long id) throws SQLException;
    List<CentroReciclagem> getAllCentroReciclagem() throws SQLException;
    CentroReciclagem createCentroReciclagem(CentroReciclagem centroReciclagem) throws SQLException;
    void deleteCentroReciclagem(Long id) throws SQLException, ExceptionNotFound;
    void updateCentroReciclagem(CentroReciclagem centroReciclagem) throws SQLException;
}
