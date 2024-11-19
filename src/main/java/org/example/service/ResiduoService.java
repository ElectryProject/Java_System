package org.example.service;

import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Residuo;

import java.sql.SQLException;
import java.util.List;

public interface ResiduoService {

    Residuo getResiduoById(Long id) throws SQLException;
    List<Residuo> getAllResiduos() throws SQLException;
    Residuo createResiduo(Residuo residuo) throws SQLException, ExceptionNotCreated;
    void updateResiduo(Residuo residuo) throws SQLException;
    void deleteResiduo(Long id) throws SQLException, ExceptionNotFound;
}
